package com.example.articles.utils

import android.content.Context
import com.example.articles.utils.livedata.LiveDataCallAdapterFactory
import com.example.articles.utils.network.NetworkResourceAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

fun makeOkHttpClientBuilder(context: Context): OkHttpClient.Builder {
    val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY


    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val response = chain.proceed(request)

                return response
            }
        })
}


fun <T> makeService(
    serviceClass: Class<T>,
    okHttpClient: OkHttpClient,
    url: String,
    moshi: Moshi
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addCallAdapterFactory(NetworkResourceAdapterFactory())
        .build()
    return retrofit.create(serviceClass)
}