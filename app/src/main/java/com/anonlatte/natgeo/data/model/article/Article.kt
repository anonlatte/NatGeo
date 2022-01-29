package com.anonlatte.natgeo.data.model.article

data class Article(
    val source: SourceArticle = SourceArticle(),
    val author: String? = null,
    val title: String? = null,
    val description: String = "",
    val url: String = "",
    val urlToImage: String? = null,
    val publishedAt: String = "",
    val content: String? = null
)