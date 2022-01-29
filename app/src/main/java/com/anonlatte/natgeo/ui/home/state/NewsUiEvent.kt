package com.anonlatte.natgeo.ui.home.state

sealed interface NewsUiEvent {
    data class ArticleSaved(val articleId: Long) : NewsUiEvent
    object ArticleSavingError : NewsUiEvent
    object Idle : NewsUiEvent
}
