package com.example.domain.exception

class ApiStateException(
    var code: Int?,
    var errorMessage: String?,
) : Throwable()