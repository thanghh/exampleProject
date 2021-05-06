package com.example.articles.ui.features.main

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.articles.BR
import com.example.articles.R
import com.example.articles.databinding.ItemArticleBinding
import com.example.articles.extension.onClickListener
import com.example.articles.ui.base.adapter.BaseListAdapter
import com.example.articles.ui.base.adapter.BaseViewHolder
import com.example.domain.model.response.Article
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MainAdapter @Inject constructor(
    @ActivityContext context: Context
) : BaseListAdapter<Article, ItemArticleBinding>(context, object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title && oldItem.pubDate == newItem.pubDate && oldItem.creator == newItem.creator && oldItem.description == newItem.description
                && oldItem.image == newItem.image && oldItem.detail == newItem.detail && oldItem.categories == newItem.categories
    }
}) {
    override fun itemLayoutResource(): Int = R.layout.item_article

    override fun createViewHolder(itemView: View) = ArticleViewHolder(
        itemView
    )

    override fun onBindViewHolder(binding: ItemArticleBinding, dto: Article, position: Int) {
        binding.setVariable(BR.dto, dto)
        binding.executePendingBindings()
        binding.setVariable(BR.onClickItem, onClickListener {
            callBack?.onClickItem(dto)
        })
    }

    class ArticleViewHolder(itemView: View) : BaseViewHolder<ItemArticleBinding>(itemView)

    private var callBack: ArticleCallBack? = null

    fun addCallBack(callBack: ArticleCallBack) {
        this.callBack = callBack
    }

    interface ArticleCallBack {
        fun onClickItem(article: Article)
    }
}