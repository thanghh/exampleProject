package com.example.articles.ui.base.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.articles.di.qualitfy.FragmentNavigatorScope
import com.example.articles.navigator.NavigatorHelper
import com.example.articles.utils.AutoClearedValue
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    @Inject
    @FragmentNavigatorScope
    lateinit var navigatorHelper: NavigatorHelper

    lateinit var binding: B

    abstract fun getLayoutResource(): Int

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (::binding.isInitialized.not()) {
            binding = AutoClearedValue(this, DataBindingUtil.inflate<B>(inflater, getLayoutResource(), container, false)).get()!!
            binding.root.isClickable = true
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected fun getFragmentArguments(): Bundle? {
        return arguments
    }

    fun finishFragment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.finishAfterTransition()
        } else {
            activity?.finish()
        }
    }

    /**
     * @return true if fragment should handle back press, false if not (activity will handle back press event)
     */
    open fun onBackPressed(): Boolean {
        return false
    }

    open fun getScreenName(): String? = null
}
