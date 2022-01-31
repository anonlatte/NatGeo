package com.anonlatte.natgeo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.anonlatte.natgeo.data.db.model.ArticleEntity

@Dao
interface ArticleDao {
    @Insert
    fun insertArticle(article: ArticleEntity): Long
}