package com.bed.chat.data.datasources

import androidx.paging.PagingSource

import com.bed.chat.external.clients.http.response.MessageResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

import com.bed.chat.external.clients.database.entities.MessageEntity
import com.bed.chat.external.clients.database.entities.MessageRemoteKeyEntity

interface RemoteMessageDatasource {
    suspend operator fun invoke(parameter: Pair<Int, PaginationRequest>): Result<PaginatedResponse<MessageResponse>>
}

interface LocalMessageDatasource {
    suspend fun deleteMessage(parameter: Int)

    suspend fun insertMessage(parameter: List<MessageEntity>)

    fun getMessage(parameter: Int): PagingSource<Int, MessageEntity>

    suspend fun deleteMessageRemoteKey(parameter: Int)

    suspend fun insertMessageRemoteKey(parameter: MessageRemoteKeyEntity)

    suspend fun getMessageRemoteKey(parameter: Int): MessageRemoteKeyEntity?
}
