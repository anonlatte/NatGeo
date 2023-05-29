package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.db.dao.ArticleDao
import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.network.RequestState
import com.anonlatte.natgeo.utils.safeApiCall
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val articleMapper: ArticleMapper
) : LocalRepository {

    override suspend fun getArticle(id: Int): RequestState<Article> {
        return safeApiCall {
            val articleEntity = articleDao.getArticle(id.toLong())
            articleMapper.mapToDomain(articleEntity)
        }
    }
}