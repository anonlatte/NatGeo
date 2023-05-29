package com.anonlatte.natgeo.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anonlatte.natgeo.data.db.model.ArticleEntity.Companion.ARTICLES_TABLE_NAME

@Entity(tableName = ARTICLES_TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String? = null,
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String? = null,
    val publishedAt: String = "",
    val content: String? = null
) {
    companion object {
        const val ARTICLES_TABLE_NAME = "articles"
    }
}