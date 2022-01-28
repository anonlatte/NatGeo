package com.anonlatte.natgeo.ui.home.viewmodel

import com.anonlatte.natgeo.ui.home.state.NewsUiState
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val uiState: StateFlow<NewsUiState>

    fun getNews(query: String)
}