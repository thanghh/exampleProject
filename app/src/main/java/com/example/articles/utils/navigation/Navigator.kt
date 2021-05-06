package com.example.articles.utils.navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

interface Navigator {

    fun addFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        args: Bundle?
    )

    fun getContext(): Context?

}