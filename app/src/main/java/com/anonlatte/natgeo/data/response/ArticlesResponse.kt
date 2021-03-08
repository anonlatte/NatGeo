package com.anonlatte.natgeo.data.response

import com.anonlatte.natgeo.data.model.Article

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)