package com.bed.chat.external.clients.mediator

import javax.inject.Inject

import androidx.room.withTransaction

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.ExperimentalPagingApi

import com.bed.chat.data.datasources.LocalMessageDatasource
import com.bed.chat.data.datasources.RemoteMessageDatasource

import com.bed.chat.external.clients.http.response.toEntity
import com.bed.chat.external.clients.http.response.MessageResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

import com.bed.chat.external.clients.database.ChatDatabase
import com.bed.chat.external.clients.database.entities.MessageEntity
import com.bed.chat.external.clients.database.entities.MessageRemoteKeyEntity

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator @Inject constructor(
    private val receiverId: Int,
    private val database: ChatDatabase,
    private val localDatasource: LocalMessageDatasource,
    private val remoteDatasource: RemoteMessageDatasource,
) : RemoteMediator<Int, MessageEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        return try {
            val offset =
                getOffset(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
            val limit = state.config.pageSize

            remoteDatasource(receiverId to buildParameter(limit to offset))
                .map { response ->
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) delete()
                        insert(Triple(limit, offset, response))
                    }

                    MediatorResult.Success(endOfPaginationReached = !response.hasMore)
                }
                .getOrElse {
                    MediatorResult.Error(Exception(it.message))
                }
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private fun buildParameter(parameter: Pair<Int, Int>) = PaginationRequest(
        limit = parameter.first,
        offset = parameter.second
    )

    private suspend fun getOffset(parameter: LoadType) = when (parameter) {
        LoadType.REFRESH -> 0
        LoadType.PREPEND -> null
        LoadType.APPEND -> localDatasource.getMessageRemoteKey(receiverId)?.nextOffset
    }

    private suspend fun delete() {
        with (localDatasource) {
            deleteMessage(receiverId)
            deleteMessageRemoteKey(receiverId)
        }
    }

    private suspend fun insert(parameter: Triple<Int, Int, PaginatedResponse<MessageResponse>>) {
        with (localDatasource) {
            insertMessage(buildEntities(parameter.third))
            insertMessageRemoteKey(buildEntity(parameter))
        }
    }

    private fun buildEntities(parameter: PaginatedResponse<MessageResponse>) =
        parameter.data.map { it.toEntity() }

    private fun buildEntity(parameter: Triple<Int, Int, PaginatedResponse<MessageResponse>>) =
        MessageRemoteKeyEntity(
            receiverId = receiverId,
            nextOffset = if (parameter.third.hasMore) parameter.first + parameter.second else null
        )
}
