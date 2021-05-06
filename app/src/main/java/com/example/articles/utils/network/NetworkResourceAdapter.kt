package com.example.articles.utils.network

import com.example.domain.model.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


class NetworkResourceAdapter<S : Any>(
    private val successType: Type
) : CallAdapter<S, Call<Resource<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<Resource<S>> {
        return NetworkResourceCall(call)
    }
}