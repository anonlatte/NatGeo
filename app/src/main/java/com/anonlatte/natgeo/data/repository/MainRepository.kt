package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.model.article.ArticlesData
import com.anonlatte.natgeo.data.network.RequestState

interface MainRepository {
    suspend fun getTopHeadlines(language: String, country: String): RequestState<ArticlesData>
    suspend fun getArticles(
        query: String,
        language: String
    ): RequestState<ArticlesData>
}