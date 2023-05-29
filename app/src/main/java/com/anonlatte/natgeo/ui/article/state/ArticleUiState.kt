package com.anonlatte.natgeo.ui.article.state

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.network.ErrorState

sealed interface ArticleUiState {
    data class Success(val article: Article) : ArticleUiState
    data class Error(val value: ErrorState) : ArticleUiState
    object Loading : ArticleUiState
}