package com.anonlatte.natgeo.data.network

import com.anonlatte.natgeo.data.response.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NatGeoApi {
    @GET("everything?domains=nationalgeographic.com")
    suspend fun getArticles(@Query("language") language: String = "ru"): ArticlesResponse

    @GET("top-headlines?sources=national-geographic")
    suspend fun getTopHeadlines(@Query("language") language: String = "ru"): ArticlesResponse
}