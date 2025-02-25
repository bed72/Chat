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
import com.bed.chat.domain.repositories.storage.SelfUserRepository

class ChatRepositoryImpl @Inject constructor(
    private val datasource: ChatDatasource,
    private val repository: SelfUserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatRepository {
    override suspend fun invoke(limit: Int, offset: Int): Result<List<ChatOutputModel>> {
        return withContext(ioDispatcher) {
            runCatching {
                val selfId = repository.user.firstOrNull()?.id
                val response = datasource(toRequest(limit, offset))

                response.map { it.toModel(selfId) }.getOrThrow().data
            }
        }
    }

    private fun toRequest(limit: Int, offset: Int) = PaginationRequest(limit, offset)
}
