package com.example.domain.model

import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorObject {
    private var message: String? = null
    private var status: Int? = null
    private var reason: String? = null

    constructor()
    constructor(message: String?, status: Int) {
        this.message = message
        this.status = status
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getStatus(): Int = this.status ?: ERROR_UNKNOWN

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage(): String? = message

    companion object {
        const val ERROR_UNKNOWN = -1
        const val ERROR_NO_INTERNET = 1001
        const val ERROR_TIME_OUT = 1002

        fun getError(throwable: Throwable?): ErrorObject {
            val e = ErrorObject()
            when (throwable) {
                is UnknownHostException, is ConnectException -> {
                    e.status =
                        ERROR_NO_INTERNET
                    e.message = "INTERNET ERROR"
                    e.reason = throwable.message
                }
                is SocketTimeoutException -> {
                    e.status =
                        ERROR_TIME_OUT
                    e.message = "TIME OUT"
                    e.reason = throwable.message
                }
                else -> {
                    e.status = ERROR_UNKNOWN
                    e.reason = throwable?.message ?: ""
                }
            }
            return e
        }
    }
}