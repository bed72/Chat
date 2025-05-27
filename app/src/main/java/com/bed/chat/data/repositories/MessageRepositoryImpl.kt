package com.bed.chat.data.repositories

import javax.inject.Inject

import androidx.paging.ExperimentalPagingApi

import androidx.paging.map
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingConfig

import kotlin.uuid.ExperimentalUuidApi

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.external.modules.IoDispatcher

import com.bed.chat.data.datasources.LocalMessageDatasource
import com.bed.chat.data.datasources.RemoteMessageDatasource

import com.bed.chat.domain.models.input.MessageDataInputModel
import com.bed.chat.domain.models.output.MessageOutputModel
import com.bed.chat.domain.models.output.MessageWithMembersOutputModel

import com.bed.chat.domain.repositories.MessageRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

import com.bed.chat.external.clients.http.response.toModel
import com.bed.chat.external.clients.http.response.toEntity
import com.bed.chat.external.clients.http.response.MessageResponse

import com.bed.chat.external.clients.mediator.MessageRemoteMediator

import com.bed.chat.external.clients.database.ChatDatabase
import com.bed.chat.external.clients.database.entities.toModel
import com.bed.chat.external.clients.database.entities.MessageEntity

import com.bed.chat.external.clients.websocket.response.WebSocketResponse

import com.bed.chat.external.clients.websocket.request.WebSocketRequest
import com.bed.chat.external.clients.websocket.request.WebSocketDataRequest
import com.bed.chat.external.clients.websocket.response.ActiveUserIdsResponse

class MessageRepositoryImpl @Inject constructor(
    private val database: ChatDatabase,
    private val userRepository: SelfUserRepository,
    private val localDatasource: LocalMessageDatasource,
    private val remoteDatasource: RemoteMessageDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MessageRepository {

    private val user = runBlocking { userRepository.user.firstOrNull() }

    override suspend fun sendMessage(parameter: MessageDataInputModel): Result<Unit> =
        safeCallResult(ioDispatcher) { remoteDatasource.sendMessage(toRequest(parameter)) }

    override fun observerDataWebSocket(): Flow<MessageWithMembersOutputModel?> =
        remoteDatasource.observerDataWebSocket()
            .onEach {
                when (it) {
                    is WebSocketResponse.MessageReceived -> save(it.message)
                    else -> it
                }
            }.map {
                when (it) {
                    is WebSocketResponse.ActiveUsersChanged -> buildMessages(it.ids, null)
                    is WebSocketResponse.MessageReceived -> buildMessages(null, it.message.toModel())

                    else -> null
                }
            }
            .flowOn(ioDispatcher)

    override suspend fun disconnectWebSocket() {
        withContext(ioDispatcher) { remoteDatasource.disconnectWebSocket() }
    }

    override suspend fun connectWebSocket(): Result<Unit> =
        safeCallResult(ioDispatcher) { remoteDatasource.connectWebSocket(user?.id ?: 0) }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMessage(id: Int): Flow<PagingData<MessageOutputModel>> = Pager(
        config = buildConfig(),
        remoteMediator = buildMediator(id),
        pagingSourceFactory = { localDatasource.getMessage(id) }
    )
        .flow
        .flowOn(ioDispatcher)
        .map { it.toModel() }

    private fun buildMessages(ids: ActiveUserIdsResponse?, messages: MessageOutputModel?) =
        MessageWithMembersOutputModel(
            messages = messages,
            ids = ids?.activeUserIds
        )

    private suspend fun save(message: MessageResponse) {
        localDatasource.insertMessage(listOf(message.toEntity()))
    }

    private fun buildConfig() = PagingConfig(pageSize = 25)

    private fun PagingData<MessageEntity>.toModel(): PagingData<MessageOutputModel> =
        map {it.toModel(user?.id) }

    @OptIn(ExperimentalUuidApi::class)
    private fun toRequest(parameter: MessageDataInputModel) = WebSocketDataRequest(
        type = "messageRequest",
        data = WebSocketRequest(
            text = parameter.text,
            timestamp = parameter.timestamp,
            receiverId = parameter.receiverId,
            messageId = parameter.messageId.toString()
        )
    )

    private fun buildMediator(parameter: Int) = MessageRemoteMediator(
        id = parameter,
        database = database,
        localDatasource = localDatasource,
        remoteDatasource = remoteDatasource,
    )
}
