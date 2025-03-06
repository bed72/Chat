package com.bed.chat.data.repositories

import java.time.Instant

import javax.inject.Inject

import androidx.paging.ExperimentalPagingApi

import androidx.paging.map
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingConfig

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.firstOrNull

import com.bed.chat.data.datasources.LocalMessageDatasource
import com.bed.chat.data.datasources.RemoteMessageDatasource

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.domain.repositories.MessageRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

import com.bed.chat.external.clients.mediator.MessageRemoteMediator

import com.bed.chat.external.clients.database.ChatDatabase
import com.bed.chat.external.clients.database.entities.toModel
import com.bed.chat.external.clients.database.entities.MessageEntity

class MessageRepositoryImpl @Inject constructor(
    private val database: ChatDatabase,
    private val repository: SelfUserRepository,
    private val localDatasource: LocalMessageDatasource,
    private val remoteDatasource: RemoteMessageDatasource,
) : MessageRepository {
    override suspend fun sendMessage(receiverId: Int, message: String) {
        val messages = listOf(buildEntity(receiverId, message))

        localDatasource.insertMessage(messages)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMessage(receiverId: Int): Flow<PagingData<MessageOutputModel>> = Pager(
        config = buildConfig(),
        remoteMediator = buildMediator(receiverId),
        pagingSourceFactory = { localDatasource.getMessage(receiverId) }
    )
        .flow
        .map { it.toModel() }

    private suspend fun selfUser() = repository.user.firstOrNull()

    private fun PagingData<MessageEntity>.toModel(): PagingData<MessageOutputModel> {
        val selfId = runBlocking { selfUser()?.id }

        return map { entity ->
            entity.toModel(selfId)
        }
    }

    private suspend fun buildEntity(receiverId: Int, message: String) = MessageEntity(
        id = null,
        isUnread = false,
        receiverId = receiverId,
        senderId = selfUser()?.id ?: 0,
        message = message,
        timestamp = Instant.now().toEpochMilli()
    )

    private fun buildConfig() = PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
    )

    private fun buildMediator(parameter: Int) = MessageRemoteMediator(
        receiverId = parameter,
        database = database,
        localDatasource = localDatasource,
        remoteDatasource = remoteDatasource,
    )
}
