package com.anonlatte.natgeo.ui.home.state

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.network.ErrorState

sealed interface NewsUiState {
    data class Success(val news: List<Article>) : NewsUiState
    data class Error(val value: ErrorState) : NewsUiState
    object Loading : NewsUiState
}