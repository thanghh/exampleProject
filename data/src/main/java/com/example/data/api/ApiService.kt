package com.example.data.api

import com.example.domain.model.Resource
import com.example.domain.model.response.Article
import retrofit2.http.GET

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): Resource<List<Article>>
}