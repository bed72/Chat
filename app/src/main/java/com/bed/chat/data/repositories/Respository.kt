package com.bed.chat.data.repositories

import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineDispatcher

suspend fun <T> safeCallResult(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> T
) = withContext(dispatcher) {
    runCatching { block() }
}
