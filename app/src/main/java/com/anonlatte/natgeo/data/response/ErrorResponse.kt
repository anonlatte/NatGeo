package com.anonlatte.natgeo.data.response

data class ErrorResponse(
    val status: String,
    val code: String,
    val message: String
)