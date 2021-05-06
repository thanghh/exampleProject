package com.example.articles.di.module

import android.content.Context
import com.example.articles.utils.makeOkHttpClientBuilder
import com.example.articles.utils.makeService
import com.example.data.Constants
import com.example.data.api.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    internal fun provideOkHttpClientNoAuth(
        @ApplicationContext context: Context): OkHttpClient = makeOkHttpClientBuilder(context).build()


    @Provides
    @Singleton
    internal fun provideApiService(okHttpClient: OkHttpClient, moshi: Moshi): ApiService =
        makeService(ApiService::class.java, okHttpClient, Constants.API_URL, moshi)

}