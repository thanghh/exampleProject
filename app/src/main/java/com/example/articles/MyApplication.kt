package com.example.articles

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        private lateinit var instance: MyApplication
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}