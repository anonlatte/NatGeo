package com.anonlatte.natgeo.data.network

import com.anonlatte.natgeo.data.response.ErrorResponse

/**
 * Wrapper for api requests
 * @author [g.proshunin](https://github.com/anonlatte) | 05.03.2021-13:41
 * */
@Suppress("KDocUnresolvedReference")
sealed class RequestState<out T> {
    data class GenericError(val throwable: Throwable) : RequestState<Nothing>()
    data class ServerError(
        val code: Int? = null,
        val message: String? = null,
        val response: ErrorResponse? = null
    ) : RequestState<Nothing>()

    data class Success<out T>(val value: T) : RequestState<T>()
    object ConnectionError : RequestState<Nothing>()
    object UnauthorizedError : RequestState<Nothing>()
}
