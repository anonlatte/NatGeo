package com.anonlatte.natgeo.data.model.article

data class ErrorResponse(
    val status: String,
    val code: String,
    val message: String
)