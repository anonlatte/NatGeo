package com.anonlatte.natgeo.data.model

data class Article(
    val source: SourceArticle = SourceArticle(),
    val author: String? = null,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String? = null,
    val publishedAt: String = "",
    val content: String? = null
)

