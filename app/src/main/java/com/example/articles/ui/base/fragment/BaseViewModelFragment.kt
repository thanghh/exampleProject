package com.example.articles.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.articles.ui.base.activity.BaseActivity
import com.example.articles.ui.base.viewmodel.BaseViewModel
import com.example.domain.model.State
import com.example.domain.model.Status

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseFragment<B>() {

    protected abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity !is BaseActivity) {
            throw IllegalStateException("All fragment's container must extend BaseActivity")
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view, activity)
        viewModel.onCreate(this, getFragmentArguments(), navigatorHelper)
        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { handleState(it) })
    }

    protected abstract fun initialize(view: View, ctx: Context?)

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    open fun handleState(state: State?) {
        if (state?.status == Status.ERROR && !state.error?.getMessage().isNullOrEmpty()){
            setMessage(state.error?.getMessage())
        }
    }

    open fun setMessage(message: String?){
        (activity as BaseActivity).setMessage(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }
}