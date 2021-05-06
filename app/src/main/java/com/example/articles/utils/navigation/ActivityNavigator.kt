package com.example.articles.utils.navigation

import android.R
import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

open class ActivityNavigator(val activity: AppCompatActivity?) : Navigator  {

    override fun addFragment(containerId: Int, fragment: Fragment, args: Bundle?) {
        activity?.supportFragmentManager?.let {
            addFragmentInternal(
                it,
                containerId,
                fragment,
                null,
                args,
                true,
                null
            )
        }
    }

    override fun getContext(): Context? {
        return activity
    }

    private fun addFragmentInternal(
        fm: FragmentManager,
        @IdRes containerId: Int,
        fragment: Fragment,
        fragmentTag: String?,
        args: Bundle?,
        addToBackStack: Boolean,
        backstackTag: String?
    ) {
        if (args != null) {
            fragment.arguments = args
        }
        val ft = fm.beginTransaction()
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)

        ft.add(containerId, fragment, fragmentTag)
        if (addToBackStack) {
            ft.addToBackStack(backstackTag).commit()
            fm.executePendingTransactions()
        } else {
            ft.commitNow()
        }
    }

}