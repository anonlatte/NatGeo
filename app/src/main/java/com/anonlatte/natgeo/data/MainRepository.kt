package com.anonlatte.natgeo.data

import com.anonlatte.natgeo.data.network.NatGeoApi
import com.anonlatte.natgeo.utils.safeApiCall
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val natGeoApi: NatGeoApi,
) {
    suspend fun getArticles() = safeApiCall { natGeoApi.getArticles() }
    suspend fun getTopHeadlines() = safeApiCall { natGeoApi.getTopHeadlines() }
}