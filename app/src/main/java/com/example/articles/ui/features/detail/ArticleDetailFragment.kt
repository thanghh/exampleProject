package com.example.articles.ui.features.detail

import android.content.Context
import android.view.View
import com.example.articles.BR
import com.example.articles.R
import com.example.articles.databinding.FragmentArticleDetailBinding
import com.example.articles.extension.onClickListener
import com.example.articles.extension.viewModels
import com.example.articles.ui.base.fragment.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailFragment : BaseViewModelFragment<FragmentArticleDetailBinding, ArticleDetailViewModel>() {

    override val viewModel: ArticleDetailViewModel by viewModels()

    override fun initialize(view: View, ctx: Context?) {
        binding.setVariable(BR.vm, viewModel)
        binding.setVariable(BR.title, "Detail")
        binding.setVariable(BR.showBackIcon, true)
        binding.setVariable(BR.onClickBack, onClickListener {
            activity?.onBackPressed()
        })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_article_detail


}