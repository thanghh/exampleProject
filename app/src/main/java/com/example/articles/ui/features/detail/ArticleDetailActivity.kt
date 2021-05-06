package com.example.articles.ui.features.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.articles.ui.base.activity.BaseSingleFragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : BaseSingleFragmentActivity<ArticleDetailFragment>() {
    override fun createFragment() = ArticleDetailFragment()
}