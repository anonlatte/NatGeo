package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.network.RequestState

interface LocalRepository {

    suspend fun getArticle(id: Int): RequestState<Article>
}