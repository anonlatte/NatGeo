package com.anonlatte.natgeo.ui.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonlatte.natgeo.ui.home.interactor.HomeInteractor
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: HomeInteractor
) : ViewModel(), HomeViewModel {

    override val uiState = MutableStateFlow<NewsUiState>(NewsUiState.Success(emptyList()))

    init {
        getTopHeadlines()
    }

    override fun getTopHeadlines() {
        makeResponseAndCollect { interactor.getTopHeadlines() }
    }

    override fun getNews(query: String) {
        makeResponseAndCollect { interactor.getArticles(query) }
    }

    private fun makeResponseAndCollect(request: suspend () -> Flow<NewsUiState>) {
        viewModelScope.launch {
            request().collect {
                uiState.value = it
            }
        }
    }
}