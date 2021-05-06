package com.example.domain.repo

import com.example.domain.model.Resource
import com.example.domain.model.response.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepo {
    suspend fun getArticles(): Flow<Resource<List<Article>>>
}