package com.anonlatte.natgeo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anonlatte.natgeo.data.db.dto.ArticleDao
import com.anonlatte.natgeo.data.db.model.ArticleEntity

@Database(entities = [ArticleEntity::class], version = NewsDatabase.DATABASE_VERSION)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "news-database"
        const val DATABASE_VERSION = 1
    }
}