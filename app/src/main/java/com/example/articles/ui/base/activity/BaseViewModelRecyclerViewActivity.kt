package com.example.articles.ui.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.articles.R
import com.example.articles.ui.base.viewmodel.BaseListViewModel
import kotlinx.android.synthetic.main.view_recyclerview.*
import javax.inject.Inject

abstract class BaseViewModelRecyclerViewActivity<B : ViewDataBinding,
        VM : BaseListViewModel<A>,
        A : RecyclerView.Adapter<*>> : BaseViewModelActivity<B, VM>() {

    @Inject
    lateinit var adapter: A

    var isRefreshing: Boolean = false

    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun getLayoutResource() = R.layout.view_recyclerview

    @SuppressLint("ClickableViewAccessibility")
    override fun initialize(ctx: Context?) {
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(hasStableIds())
        }
        viewModel.initAdapter(adapter)
        layoutManager = createLayoutManager()
        recyclerView.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = adapter
            it.setOnTouchListener { _, _ ->
                false
            }
            it.setHasFixedSize(hasFixedSize())
        }

    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    open fun hasFixedSize() = false

    open fun hasStableIds() = false

    open fun hasHandleLoading() = false

    fun getRecyclerView(): RecyclerView = this.recyclerView
}