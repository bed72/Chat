package com.bed.chat.external.datasources.local

import javax.inject.Inject

import androidx.paging.PagingSource

import com.bed.chat.data.datasources.LocalMessageDatasource

import com.bed.chat.external.clients.database.ChatDatabase
import com.bed.chat.external.clients.database.entities.MessageEntity
import com.bed.chat.external.clients.database.entities.MessageRemoteKeyEntity

class LocalRemoteMessageDatasourceImpl @Inject constructor(
    private val database: ChatDatabase
) : LocalMessageDatasource {

    private val message get() = database.messageDao()
    private val messageKey get() = database.messageRemoteKeyDao()

    override suspend fun deleteMessage(parameter: Int) {
        message.delete(parameter)
    }

    override suspend fun insertMessage(parameter: List<MessageEntity>) {
        message.insert(parameter)
    }

    override fun getMessage(parameter: Int): PagingSource<Int, MessageEntity> =
        message.get(parameter)

    override suspend fun deleteMessageRemoteKey(parameter: Int) {
        messageKey.delete(parameter)
    }

    override suspend fun getMessageRemoteKey(parameter: Int): MessageRemoteKeyEntity =
        messageKey.get(parameter)

    override suspend fun insertMessageRemoteKey(parameter: MessageRemoteKeyEntity) {
        messageKey.insert(parameter)
    }
}
