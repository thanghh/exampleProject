package com.example.articles.ui.features.detail

import android.content.Context
import android.os.Bundle
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import com.example.articles.BR
import com.example.articles.ui.base.viewmodel.BaseViewModel
import com.example.domain.define.BundleKey
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ArticleDetailViewModel @ViewModelInject constructor( var articleObservable: ArticleObservable): BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        val articleImage = bundle?.getString(BundleKey.ARTICLE_IMAGE)
        val articleDetail = bundle?.getString(BundleKey.ARTICLE_DETAIL)

        articleObservable.notifyImage(articleImage)
        articleObservable.notifyDetail(articleDetail)
    }

    class ArticleObservable @Inject constructor(@ActivityContext val context: Context) : BaseObservable() {

        @Bindable
        var articleImage: String? = null

        @Bindable
        var articleDetail: String? = null


        fun notifyImage(articleImage: String?) {
            this.articleImage = articleImage
            notifyPropertyChanged(BR.articleImage)
        }

        fun notifyDetail(articleDetail: String?) {
            this.articleDetail = articleDetail
            notifyPropertyChanged(BR.articleDetail)
        }
    }
}