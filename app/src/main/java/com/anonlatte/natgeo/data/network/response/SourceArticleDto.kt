package com.anonlatte.natgeo.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceArticleDto(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "name")
    val name: String = ""
)