package com.anonlatte.natgeo.data

import com.anonlatte.natgeo.data.network.NatGeoApi
import javax.inject.Inject

class MainRepository @Inject constructor(private val natGeoApi: NatGeoApi) {
    suspend fun getArticles() = natGeoApi.getArticles()
    suspend fun getTopHeadlines() = natGeoApi.getTopHeadlines()
}