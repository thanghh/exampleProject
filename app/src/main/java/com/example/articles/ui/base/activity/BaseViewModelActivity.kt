package com.example.articles.ui.base.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.articles.extension.observeCleaner
import com.example.articles.ui.base.viewmodel.BaseViewModel
import com.example.domain.model.State
import com.example.domain.model.Status

abstract class BaseViewModelActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    protected lateinit var binding: B

    protected abstract val viewModel: VM

    protected abstract fun initialize(ctx: Context?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::binding.isInitialized.not()) {
            binding = DataBindingUtil.setContentView(this, getLayoutResource())
            binding.apply {
                root.isClickable = true
                executePendingBindings()
            }
        }
        initialize(this)
        binding.lifecycleOwner = this
        viewModel.onCreate(this, intent.extras, navigatorHelper)
        viewModel.stateLiveData.observeCleaner(this) { handleState(it) }
    }

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    open fun handleState(state: State?) {
        if (state?.status == Status.ERROR && !state.error?.getMessage().isNullOrEmpty()){
            setMessage(state.error?.getMessage())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyView()
    }

    override fun shouldUseDataBinding() = true
}