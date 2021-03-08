package com.anonlatte.natgeo.data.network

import com.anonlatte.natgeo.data.response.ArticlesResponse
import retrofit2.http.GET

interface NatGeoApi {
    @GET("everything?domains=nationalgeographic.com")
    suspend fun getArticles(): ArticlesResponse

    @GET("top-headlines?sources=national-geographic")
    suspend fun getTopHeadlines(): ArticlesResponse
}