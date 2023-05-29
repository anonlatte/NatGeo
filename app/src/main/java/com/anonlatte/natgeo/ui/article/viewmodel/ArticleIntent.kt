package com.anonlatte.natgeo.ui.article.viewmodel

sealed interface ArticleIntent {
    data class GetArticle(val id: Int) : ArticleIntent
}
