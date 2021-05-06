package com.example.articles.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.articles.di.qualitfy.FragmentNavigatorScope
import com.example.articles.navigator.NavigatorHelper
import com.example.articles.utils.navigation.ActivityNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FragmentNavigationModule {
    @Provides
    @FragmentNavigatorScope
    fun providesNavController(activity: Activity): NavigatorHelper {
        return NavigatorHelper(
            ActivityNavigator(activity as AppCompatActivity)
        )
    }
}