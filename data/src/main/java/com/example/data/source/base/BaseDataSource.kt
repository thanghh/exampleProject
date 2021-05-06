package com.example.data.source.base

import com.example.domain.model.ErrorObject
import com.example.domain.model.Resource

abstract class BaseDataSource {

    protected suspend fun <T> getResource(call: suspend () -> Resource<T>): Resource<T> {
        return try {
            call.invoke()
        } catch (e: Exception) {
            error(e)
        }
    }

    private fun <T> error(throwable: Throwable): Resource<T> {
        return Resource.error(ErrorObject.getError(throwable))
    }

}