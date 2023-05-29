package com.anonlatte.natgeo.ui.article.interactor

import com.anonlatte.natgeo.ui.article.state.ArticleUiState
import kotlinx.coroutines.flow.Flow

interface ArticleInteractor {
    suspend fun getArticle(id: Int): Flow<ArticleUiState>
}