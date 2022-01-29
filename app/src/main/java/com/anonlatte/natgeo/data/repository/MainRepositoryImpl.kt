package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.network.NatGeoApi
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.data.network.response.ArticlesResponse
import com.anonlatte.natgeo.utils.safeApiCall
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val natGeoApi: NatGeoApi) : MainRepository {
    override suspend fun getArticles(
        query: String,
        language: String
    ): RequestState<ArticlesResponse> {
        return safeApiCall { natGeoApi.getArticles(query, language) }
    }

    override suspend fun getTopHeadlines(
        language: String,
        country: String
    ): RequestState<ArticlesResponse> {
        return safeApiCall { natGeoApi.getTopHeadlines(language, country) }
    }
}