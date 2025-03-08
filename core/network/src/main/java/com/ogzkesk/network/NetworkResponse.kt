package com.ogzkesk.network

sealed interface NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>

    data class Error(val code: Int?, val message: String) : NetworkResponse<Nothing>

    val isSuccess: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Error

    val getOrNull: T?
        get() = (this as? Success)?.data
}
