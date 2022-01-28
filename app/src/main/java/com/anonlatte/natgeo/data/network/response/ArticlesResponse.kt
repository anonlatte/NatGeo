package com.anonlatte.natgeo.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticlesResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int,
    @Json(name = "articles")
    val articles: List<ArticleDto>
)