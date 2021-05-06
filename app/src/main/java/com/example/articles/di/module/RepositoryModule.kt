package com.example.articles.di.module

import com.example.data.repo.ArticleRepoImpl
import com.example.domain.repo.ArticleRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun articleRepo(repoImpl: ArticleRepoImpl): ArticleRepo

}