package com.anonlatte.natgeo.data.network

import com.anonlatte.natgeo.data.network.response.ErrorResponse

/**
 * Wrapper for api requests
 * @author [g.proshunin](https://github.com/anonlatte) | 05.03.2021-13:41
 * */
@Suppress("KDocUnresolvedReference")
sealed interface RequestState<out T> {
    data class Success<out T>(val value: T) : RequestState<T>
    object Error : ErrorState
}

sealed interface ErrorState : RequestState<Nothing> {
    data class GenericError(val throwable: Throwable) : ErrorState
    data class ServerError(
        val code: Int? = null,
        val message: String? = null,
        val response: ErrorResponse? = null
    ) : ErrorState

    object ConnectionError : ErrorState
    object UnauthorizedError : ErrorState
}