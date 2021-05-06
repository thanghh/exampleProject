package com.example.articles.utils.network

import com.example.domain.model.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResourceAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<Resource<<Foo>> or Call<Resource<out Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not ApiResponse then we can't handle this type, so we return null
        if (getRawType(responseType) != Resource::class.java) {
            return null
        }

        // the response type is ApiResponse and should be parameterized
        check(responseType is ParameterizedType) { "Response must be parameterized as Resource<Foo> or Resource<out Foo>" }
        val successBodyType = getParameterUpperBound(0, responseType)
        return NetworkResourceAdapter<Any>(successBodyType)
    }
}