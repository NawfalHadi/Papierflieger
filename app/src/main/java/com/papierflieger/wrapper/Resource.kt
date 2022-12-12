package com.papierflieger.wrapper

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

sealed class Resource<T>(
    val payload: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Empty<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(thorwable: Throwable, data: T? = null) : Resource<T>(data, throwable = thorwable)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}