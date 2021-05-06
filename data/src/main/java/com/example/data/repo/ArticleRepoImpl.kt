package com.example.data.repo

import com.example.data.source.ArticleDataSource
import com.example.domain.model.Resource
import com.example.domain.model.response.Article
import com.example.domain.repo.ArticleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ArticleRepoImpl @Inject constructor(private val articleDataSource: ArticleDataSource) : ArticleRepo {
    override suspend fun getArticles(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.success(articleDataSource.getArticles().data))
    }.flowOn(Dispatchers.IO)
}