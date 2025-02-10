package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.data.datasources.ChatDatasource

import com.bed.chat.external.modules.IoDispatcher
import com.bed.chat.external.clients.response.toModel
import com.bed.chat.external.clients.request.PaginationRequest

import com.bed.chat.domain.models.output.ChatOutputModel

import com.bed.chat.domain.repositories.ChatRepository
import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.storage.SelfUserStorageRepository

class ChatRepositoryImpl @Inject constructor(
    private val chatDatasource: ChatDatasource,
    private val tokenRepository: TokenRepository,
    private val selfUserRepository: SelfUserStorageRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatRepository {
    override suspend fun getChats(limit: Int, offset: Int): Result<List<ChatOutputModel>> {
        return withContext(ioDispatcher) {
            runCatching {
                val token = tokenRepository.get().firstOrNull()  ?: ""
                val selfId = selfUserRepository.user.firstOrNull()?.id
                val response = chatDatasource.getChats(token, toRequest(limit, offset))

                response.map { it.toModel(selfId) }.getOrThrow().conversations
            }
        }
    }

    private fun toRequest(limit: Int, offset: Int) = PaginationRequest(limit, offset)
}
