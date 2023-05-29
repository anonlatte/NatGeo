package com.anonlatte.natgeo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anonlatte.natgeo.data.db.model.ArticleEntity
import com.anonlatte.natgeo.data.db.model.ArticleEntity.Companion.ARTICLES_TABLE_NAME

@Dao
interface ArticleDao {
    @Insert
    fun insertArticle(article: ArticleEntity): Long

    @Query("SELECT * from $ARTICLES_TABLE_NAME WHERE id=:id")
    fun getArticle(id: Long): ArticleEntity
}