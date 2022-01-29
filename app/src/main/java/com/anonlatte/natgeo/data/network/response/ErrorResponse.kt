package com.anonlatte.natgeo.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "code")
    val code: String,
    @Json(name = "message")
    val message: String
)