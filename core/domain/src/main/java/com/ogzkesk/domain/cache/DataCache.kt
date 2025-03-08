package com.ogzkesk.domain.cache

import com.ogzkesk.domain.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Suppress("UnusedPrivateProperty") // TODO remove
abstract class DataCache<T>(
    private val default: T,
    private val logger: Logger,
) {
    private val data: MutableStateFlow<T> = MutableStateFlow(default)

    fun stream(): StateFlow<T> = data.asStateFlow()

    fun update(block: (T) -> T) {
        // TODO debug events
        data.update(block)
    }

    fun clear() {
        data.update { default }
    }
}
