package com.bed.chat.domain.repositories

import kotlinx.coroutines.flow.SharedFlow

interface SessionRepository {
    val sessionExpiredFlow: SharedFlow<Unit>
    suspend fun logout()
    fun notifySessionExpired()
}
