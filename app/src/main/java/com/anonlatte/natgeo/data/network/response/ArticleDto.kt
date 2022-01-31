package com.anonlatte.natgeo.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDto(
    @Json(name = "source")
    val source: SourceArticleDto = SourceArticleDto(),
    @Json(name = "author")
    val author: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "description")
    val description: String? = "",
    @Json(name = "url")
    val url: String = "",
    @Json(name = "urlToImage")
    val urlToImage: String? = null,
    @Json(name = "publishedAt")
    val publishedAt: String = "",
    @Json(name = "content")
    val content: String? = null
)