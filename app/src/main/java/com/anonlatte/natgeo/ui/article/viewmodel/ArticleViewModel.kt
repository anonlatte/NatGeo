package com.anonlatte.natgeo.ui.article.viewmodel

import com.anonlatte.natgeo.ui.article.state.ArticleUiEvent
import com.anonlatte.natgeo.ui.article.state.ArticleUiState
import kotlinx.coroutines.flow.StateFlow

interface ArticleViewModel {
    val uiState: StateFlow<ArticleUiState>
    val uiEvent: StateFlow<ArticleUiEvent>

    fun sendIntent(intent: ArticleIntent)
}