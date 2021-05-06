package com.example.domain.livedata

import androidx.lifecycle.MutableLiveData

class SafeMutableLiveData<T> : MutableLiveData<T> {

    constructor(): super()


    override fun setValue(value: T) {
        try {
            super.setValue(value)
        } catch (e: Exception) {
            // if we can't set value due to not in main thread, must call post value instead
            super.postValue(value)
        }
    }
}