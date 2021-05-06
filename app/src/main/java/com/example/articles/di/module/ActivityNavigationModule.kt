package com.example.articles.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.example.articles.di.qualitfy.ActivityNavigatorScope
import com.example.articles.navigator.NavigatorHelper
import com.example.articles.utils.navigation.ActivityNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityNavigationModule {
    @Provides
    @ActivityNavigatorScope
    fun providesNavController(activity: Activity): NavigatorHelper {
        return NavigatorHelper(
            ActivityNavigator(activity as AppCompatActivity)
        )
    }
}