package com.example.articles.utils.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

interface FragmentNavigator : Navigator {
    fun replaceChildFragmentAndAddToBackStack(@IdRes containerId: Int, fragment: Fragment, args: Bundle?, backstackTag: String?)
}