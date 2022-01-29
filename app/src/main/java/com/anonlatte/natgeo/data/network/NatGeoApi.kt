package com.anonlatte.natgeo.data.network

import com.anonlatte.natgeo.data.network.response.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NatGeoApi {
    @GET("everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("language") language: String
    ): ArticlesResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("language") language: String,
        @Query("country") country: String
    ): ArticlesResponse
}