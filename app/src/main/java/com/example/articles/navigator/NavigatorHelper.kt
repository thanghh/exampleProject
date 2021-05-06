package com.example.articles.navigator

import android.app.Activity
import android.os.Bundle
import com.example.articles.R
import com.example.articles.ui.features.detail.ArticleDetailFragment
import com.example.articles.utils.navigation.ActivityNavigator
import com.example.articles.utils.navigation.FragmentNavigator
import com.example.articles.utils.navigation.Navigator
import com.example.domain.define.BundleKey
import com.example.domain.model.response.Article

class NavigatorHelper {
    private var navigator: Navigator

    constructor(mNavigator: ActivityNavigator) {
        this.navigator = mNavigator
    }

    constructor(mNavigator: Navigator) {
        this.navigator = mNavigator
    }

    fun getFragmentNavigator() = navigator as FragmentNavigator

    fun currentActivity() = navigator.getContext() as Activity


    fun navigateArticleDetailFragment(article: Article) {
        navigator.addFragment(R.id.childContainer,ArticleDetailFragment(), Bundle().apply {
            this.putString(BundleKey.ARTICLE_IMAGE, article.image)
            this.putString(BundleKey.ARTICLE_DETAIL, article.detail)},
        )

    }
}