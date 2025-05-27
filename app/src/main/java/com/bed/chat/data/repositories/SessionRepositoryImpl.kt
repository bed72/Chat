package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow

import com.bed.chat.domain.repositories.SessionRepository
import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository
import com.bed.chat.external.modules.IoDispatcher


class SessionRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: SelfUserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SessionRepository {
    private val _sessionExpiredFlow = MutableSharedFlow<Unit>(replay = 1)
    override val sessionExpiredFlow = _sessionExpiredFlow.asSharedFlow()

    override suspend fun logout() {
        withContext(ioDispatcher) {
            userRepository.delete()
            tokenRepository.delete()
        }
    }

    override fun notifySessionExpired() {
        if (_sessionExpiredFlow.subscriptionCount.value == 0) return

        _sessionExpiredFlow.tryEmit(Unit)
    }
}
