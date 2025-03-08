package com.ogzkesk.domain.logger

interface Logger {
    fun d(message: String)

    fun e(message: String)

    fun e(throwable: Throwable)

    fun e(
        message: String,
        throwable: Throwable,
    )

    fun i(message: String)

    fun w(message: String)
}
