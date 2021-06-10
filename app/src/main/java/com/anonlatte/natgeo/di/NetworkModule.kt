package com.anonlatte.natgeo.di

import com.anonlatte.natgeo.BuildConfig
import com.anonlatte.natgeo.data.network.NatGeoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("X-Api-Key", BuildConfig.API_KEY)
                    .build()
                it.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideNatGeoApi(retrofit: Retrofit): NatGeoApi = retrofit.create(NatGeoApi::class.java)
}