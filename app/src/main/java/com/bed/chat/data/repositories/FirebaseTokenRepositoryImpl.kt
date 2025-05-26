package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineDispatcher

import com.google.firebase.messaging.FirebaseMessaging

import com.bed.chat.external.modules.IoDispatcher

import com.bed.chat.domain.repositories.FirebaseTokenRepository

class FirebaseTokenRepositoryImpl @Inject constructor(
    private val messaging: FirebaseMessaging,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FirebaseTokenRepository {
    override suspend fun getToken(): String = withContext(ioDispatcher) {
        messaging.token.await()
    }
}
