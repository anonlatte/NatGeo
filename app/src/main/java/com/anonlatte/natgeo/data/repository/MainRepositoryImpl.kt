package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.db.dao.ArticleDao
import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.model.article.ArticlesData
import com.anonlatte.natgeo.data.network.NatGeoApi
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val natGeoApi: NatGeoApi,
    private val articleMapper: ArticleMapper,
    private val articleDao: ArticleDao
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

    override suspend fun storeArticle(article: Article): Flow<Long> {
        return flow {
            val insertedArticleId = kotlin.runCatching {
                articleDao.insertArticle(articleMapper.mapToEntity(article))
            }
                .onFailure { Timber.w(it) }
                .getOrDefault(-1)
            emit(insertedArticleId)
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