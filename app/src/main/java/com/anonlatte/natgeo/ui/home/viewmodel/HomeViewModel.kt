package com.anonlatte.natgeo.ui.home.viewmodel

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.ui.home.state.NewsUiEvent
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val uiState: StateFlow<NewsUiState>
    val uiEvent: StateFlow<NewsUiEvent>

    fun getNews(query: String)
    fun getTopHeadlines()
    fun storeArticleAndNavigate(article: Article)
    fun resetEventState()
}