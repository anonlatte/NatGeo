package com.anonlatte.natgeo.data.repository

import com.anonlatte.natgeo.data.model.article.Article
import com.anonlatte.natgeo.data.model.article.ArticlesData
import com.anonlatte.natgeo.data.model.article.SourceArticle
import com.anonlatte.natgeo.data.network.response.ArticleDto
import com.anonlatte.natgeo.data.network.response.ArticlesResponse
import com.anonlatte.natgeo.data.network.response.SourceArticleDto
import javax.inject.Inject

class ArticleMapper @Inject constructor() {
    fun mapToDomain(articlesResponse: ArticlesResponse): ArticlesData {
        return ArticlesData(articlesResponse.totalResults, articlesResponse.articles.toDomain())
    }

    private fun List<ArticleDto>.toDomain(): List<Article> {
        return map {
            with(it) {
                Article(
                    source.toDomain(),
                    author,
                    title,
                    description,
                    url,
                    urlToImage,
                    publishedAt,
                    content
                )
            }
        }
    }

    private fun SourceArticleDto.toDomain(): SourceArticle {
        return SourceArticle(id, name)
    }
}