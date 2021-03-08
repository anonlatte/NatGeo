package com.anonlatte.natgeo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonlatte.natgeo.data.MainRepository
import com.anonlatte.natgeo.data.model.Article
import com.anonlatte.natgeo.data.network.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(
        NewsUiState.Success(emptyList())
    )
    val uiState: MutableStateFlow<NewsUiState> = _uiState

    init {
        viewModelScope.launch {
            val requestState = mainRepository.getTopHeadlines()
            _uiState.value = when (requestState) {
                is RequestState.ConnectionError -> NewsUiState.Error(requestState)
                is RequestState.GenericError -> NewsUiState.Error(requestState)
                is RequestState.ServerError -> NewsUiState.Error(requestState)
                is RequestState.Success -> NewsUiState.Success(requestState.value.articles)
                is RequestState.UnauthorizedError -> NewsUiState.Error(requestState)
            }
        }
    }
}

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val news: List<Article>) : NewsUiState()
    data class Error(val error: RequestState<Nothing>) : NewsUiState()
}