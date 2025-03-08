package com.ogzkesk.database.logger

import com.ogzkesk.domain.logger.Logger
import net.zetetic.database.BuildConfig
import timber.log.Timber

class LoggerImpl : Logger {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun d(message: String) {
        Timber.d(message)
    }

    override fun e(message: String) {
        Timber.e(message)
    }

    override fun e(throwable: Throwable) {
        Timber.e(throwable)
    }

    override fun e(
        message: String,
        throwable: Throwable,
    ) {
        Timber.e(message, throwable)
    }

    override fun i(message: String) {
        Timber.i(message)
    }

    override fun w(message: String) {
        Timber.w(message)
    }
}
