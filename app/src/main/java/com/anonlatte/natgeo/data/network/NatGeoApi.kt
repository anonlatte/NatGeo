package com.anonlatte.natgeo.data.network

import com.anonlatte.natgeo.data.response.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NatGeoApi {
    @GET("everything?domains=nationalgeographic.com")
    suspend fun getArticles(@Query("q") query: String): ArticlesResponse

    @GET("top-headlines?sources=national-geographic")
    suspend fun getTopHeadlines(): ArticlesResponse
}