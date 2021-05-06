package com.example.data.source

import com.example.data.api.ApiService
import com.example.data.source.base.BaseDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ArticleDataSource @Inject constructor(
    private val service: ApiService,
) : BaseDataSource(){
    suspend fun getArticles() = getResource { service.getArticles() }

}