package com.anonlatte.natgeo.di

import android.content.Context
import androidx.room.Room
import com.anonlatte.natgeo.data.db.NewsDatabase
import com.anonlatte.natgeo.data.db.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NewsDatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            NewsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideArticleDao(database: NewsDatabase): ArticleDao = database.articleDao()
}