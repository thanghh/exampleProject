package com.example.articles.ui.features.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.articles.BR
import com.example.articles.R
import com.example.articles.databinding.ActivityMainBinding
import com.example.articles.extension.viewModels
import com.example.articles.ui.base.activity.BaseViewModelActivity
import com.example.articles.ui.base.activity.BaseViewModelRecyclerViewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewModelRecyclerViewActivity<ActivityMainBinding, MainViewModel, MainAdapter>() {
    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun initialize(ctx: Context?) {
        super.initialize(ctx)
        binding.setVariable(BR.title, "Article")
    } 

}