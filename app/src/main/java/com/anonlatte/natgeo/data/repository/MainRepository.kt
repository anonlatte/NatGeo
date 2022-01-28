package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.data.network.response.ArticlesResponse

interface MainRepository {
    suspend fun getTopHeadlines(language: String, country: String): RequestState<ArticlesResponse>
    suspend fun getArticles(
        query: String,
        language: String,
        country: String
    ): RequestState<ArticlesResponse>
}