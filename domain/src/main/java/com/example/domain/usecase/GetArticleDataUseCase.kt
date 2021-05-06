package com.example.domain.usecase

import com.example.domain.repo.ArticleRepo
import javax.inject.Inject

class GetArticleDataUseCase @Inject constructor(
    private val articleRepo: ArticleRepo
) {
    suspend fun getArticleData() = articleRepo.getArticles()
}