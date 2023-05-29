package com.anonlatte.natgeo.ui.article.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonlatte.natgeo.ui.article.interactor.ArticleInteractor
import com.anonlatte.natgeo.ui.article.state.ArticleUiEvent
import com.anonlatte.natgeo.ui.article.state.ArticleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModelImpl @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val articleInteractor: ArticleInteractor
) : ViewModel(), ArticleViewModel {

    override val uiState = MutableStateFlow<ArticleUiState>(ArticleUiState.Loading)
    override val uiEvent = MutableStateFlow<ArticleUiEvent>(ArticleUiEvent.Idle)

    override fun sendIntent(intent: ArticleIntent) {
        when (intent) {
            is ArticleIntent.GetArticle -> getArticle(intent.id)
        }
    }

    private fun getArticle(id: Int) {
        makeResponseAndCollect {
            articleInteractor.getArticle(id)
        }
    }

    private fun makeResponseAndCollect(request: suspend () -> Flow<ArticleUiState>) {
        viewModelScope.launch(Dispatchers.IO) {
            request().collect {
                uiState.value = it
            }
        }
    }
}

