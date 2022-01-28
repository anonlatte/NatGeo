package com.anonlatte.natgeo.ui.home.state

import com.anonlatte.natgeo.data.network.ErrorState
import com.anonlatte.natgeo.data.network.response.ArticleDto

sealed interface NewsUiState {
    data class Success(val news: List<ArticleDto>) : NewsUiState
    data class Error(val value: ErrorState) : NewsUiState
    object Loading : NewsUiState
}