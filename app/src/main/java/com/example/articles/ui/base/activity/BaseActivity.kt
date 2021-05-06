package com.example.articles.ui.base.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.articles.di.qualitfy.ActivityNavigatorScope
import com.example.articles.extension.showToast
import com.example.articles.navigator.NavigatorHelper
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    @ActivityNavigatorScope
    lateinit var navigatorHelper: NavigatorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!shouldUseDataBinding()) {
            // set contentView if child activity not use dataBinding
            setContentView(getLayoutResource())
        }
    }

    fun addFragment(@IdRes res: Int, fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .add(res, fragment, tag)
            .commit()
    }

    open fun setMessage(message: String?) {
        if (!message.isNullOrEmpty()) {
            showToast(message)
        }
    }

    protected fun finishWithAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
    }

    /**
     * @return true if child activity should use data binding instead of [.setContentView]
     */
    protected open fun shouldUseDataBinding(): Boolean {
        return false
    }

    protected abstract fun getLayoutResource(): Int

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    open fun getScreenName(): String? = null

}