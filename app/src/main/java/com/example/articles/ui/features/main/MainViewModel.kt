package com.example.articles.ui.features.main

import android.os.Bundle
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.articles.navigator.NavigatorHelper
import com.example.articles.ui.base.viewmodel.BaseListViewModel
import com.example.articles.ui.base.viewmodel.BaseViewModel
import com.example.domain.model.response.Article
import com.example.domain.usecase.GetArticleDataUseCase

class MainViewModel @ViewModelInject constructor(
    private val getArticleDataUseCase: GetArticleDataUseCase) : BaseListViewModel<MainAdapter>() {

    override fun fetchData() {}

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            getArticleDataUseCase.getArticleData().execute(true, onDataSuccess = {
                adapter.submitList(it)
            })
        }
    }

    override fun initAdapter(adapter: MainAdapter) {
        super.initAdapter(adapter)
        adapter.addCallBack(object : MainAdapter.ArticleCallBack{
            override fun onClickItem(article: Article) {
                navigatorHelper?.navigateArticleDetailFragment(article)
            }
        })
    }

}