package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.model.article.ArticlesData
import com.anonlatte.natgeo.data.network.NatGeoApi
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.utils.safeApiCall
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val natGeoApi: NatGeoApi,
    private val articleMapper: ArticleMapper
) : MainRepository {
    override suspend fun getArticles(
        query: String,
        language: String
    ): RequestState<ArticlesData> {
        return safeApiCall {
            natGeoApi.getArticles(query, language)
                .let { articleMapper.mapToDomain(it) }
        }
    }

    override suspend fun getTopHeadlines(
        language: String,
        country: String
    ): RequestState<ArticlesData> {
        return safeApiCall {
            natGeoApi.getTopHeadlines(language, country)
                .let { articleMapper.mapToDomain(it) }
        }
    }
}