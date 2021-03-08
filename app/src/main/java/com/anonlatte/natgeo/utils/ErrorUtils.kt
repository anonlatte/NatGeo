package com.anonlatte.natgeo.utils

import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.data.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Uses to make checkable calls to API from [repository][com.anonlatte.natgeo.data.MainRepository]
 * @author [g.proshunin](https://github.com/anonlatte) | 05.03.2021-13:41
 * @param apiCall suspend function of api request
 * */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): RequestState<T> = runCatching {
    RequestState.Success(apiCall())
}.getOrElse {
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
            Gson().fromJson(it.body().toString(), ErrorResponse::class.java)
        }
        if (response != null) {
            RequestState.ServerError(
                exception.code(),
                exception.message(),
                response
            )
        } else {
            RequestState.ServerError()
        }
    }.getOrElse {
        RequestState.GenericError(it)
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
    is UnknownHostException -> RequestState.ConnectionError
    else -> RequestState.GenericError(throwable)
}
