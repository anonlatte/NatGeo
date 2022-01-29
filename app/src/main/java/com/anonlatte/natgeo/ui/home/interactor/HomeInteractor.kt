package com.anonlatte.natgeo.ui.home.interactor

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    suspend fun getTopHeadlines(): Flow<NewsUiState>
    suspend fun getArticles(query: String): Flow<NewsUiState>
    suspend fun storeArticle(article: Article): Flow<Long>
}