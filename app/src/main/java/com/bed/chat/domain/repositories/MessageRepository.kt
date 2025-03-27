package com.bed.chat.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.models.input.MessageDataInputModel
import com.bed.chat.domain.models.output.MessageOutputModel

interface MessageRepository {
    fun observerDataWebSocket() : Flow<MessageOutputModel?>
    fun getMessage(parameter: Int): Flow<PagingData<MessageOutputModel>>

    suspend fun disconnectWebSocket()
    suspend fun connectWebSocket(): Result<Unit>
    suspend fun sendMessage(parameter: MessageDataInputModel): Result<Unit>
}
