package com.example.domain.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class ApiLiveData<T>: MediatorLiveData<T>() {

    private var source: (() -> LiveData<T>)?=null

    fun setSource(source: () -> LiveData<T>) {
        this.source = source
        this.addSource(source(), ::observer)
    }


    private fun observer(data: T) {
        value = data
    }

    fun refresh() {
        source?.let {
            removeSource(it())
            addSource(it(), ::observer)
        }
    }
}