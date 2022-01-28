package com.anonlatte.natgeo.utils

import com.anonlatte.natgeo.data.network.ErrorState
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.data.network.response.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

/**
 * Uses to make checkable calls to API from [repository][com.anonlatte.natgeo.data.MainRepositoryImpl]
 * @author [g.proshunin](https://github.com/anonlatte) | 05.03.2021-13:41
 * @param apiCall suspend function of api request
 * */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): RequestState<T> = runCatching {
    RequestState.Success(apiCall())
}.getOrElse {
    Timber.w(it)
    checkThrowable(it)
}

/**
 * Parses custom error response from API
 * @author [g.proshunin](https://github.com/anonlatte) | 05.03.2021-13:41
 * @param exception HttpException from [checkThrowable]
 * @return [ServerError][RequestState.ServerError] with empty or parsed [ServerError][RequestState.ServerError] instance
 *
 * [GenericError][RequestState.GenericError] if response couldn't be parsed.
 * */
@Suppress("KDocUnresolvedReference")
private fun parseErrorResponse(exception: HttpException): RequestState<Nothing> {
    return runCatching {
        val response = exception.response()?.let {
            Moshi.Builder().build().adapter(
                ErrorResponse::class.java
            ).fromJson(it.body().toString())
        }
        if (response != null) {
            ErrorState.ServerError(
                exception.code(),
                exception.message(),
                response
            )
        } else {
            ErrorState.ServerError()
        }
    }.getOrElse {
        ErrorState.GenericError(it)
    }
}

/**
 * @author [g.proshunin](https://github.com/anonlatte) | 05.03.2021-13:41
 * @param throwable from [safeApiCall] if exception caught
 * @return [parseErrorResponse] for [HttpException]
 *
 * [ConnectionError][RequestState.ConnectionError] for [UnknownHostException]
 *
 * else [GenericError][RequestState.GenericError]
 * */
@Suppress("KDocUnresolvedReference")
private fun checkThrowable(throwable: Throwable): RequestState<Nothing> = when (throwable) {
    is HttpException -> parseErrorResponse(throwable)
    is UnknownHostException -> ErrorState.ConnectionError
    else -> ErrorState.GenericError(throwable)
}
