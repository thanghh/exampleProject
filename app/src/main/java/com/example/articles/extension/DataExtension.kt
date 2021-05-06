package com.example.articles.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>?.observeCleaner(viewLifecycleOwner: LifecycleOwner, callBack: (data: T?) -> Unit) {
    this?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
        callBack.invoke(it)
    })
}
