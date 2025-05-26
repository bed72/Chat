package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.data.datasources.ChatDatasource

import com.bed.chat.domain.models.output.ChatOutputModel

import com.bed.chat.domain.repositories.ChatRepository
import com.bed.chat.domain.repositories.NotificationRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

import com.bed.chat.external.modules.IoDispatcher
import com.bed.chat.external.clients.http.response.toModel
import com.bed.chat.external.clients.http.request.PaginationRequest

class ChatRepositoryImpl @Inject constructor(
    private val datasource: ChatDatasource,
    private val repository: SelfUserRepository,
    private val notificationRepository: NotificationRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatRepository {
    override val newMessageReceivedFlow: Flow<Unit>
        get() = notificationRepository.notificationFlow.map {  }

    override suspend fun invoke(limit: Int, offset: Int): Result<List<ChatOutputModel>> =
        safeCallResult(ioDispatcher) {
            val selfId = repository.user.firstOrNull()?.id
            val response = datasource(toRequest(limit, offset))

            response.map { it.toModel(selfId) }.getOrThrow().data
        }


    private fun toRequest(limit: Int, offset: Int) = PaginationRequest(limit, offset)
}
