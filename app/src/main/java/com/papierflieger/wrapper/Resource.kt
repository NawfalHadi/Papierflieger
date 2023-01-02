package com.papierflieger.wrapper

sealed class Resource<T>(
    val payload: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Empty<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable = throwable)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}