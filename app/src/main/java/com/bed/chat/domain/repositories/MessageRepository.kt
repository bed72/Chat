package com.bed.chat.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.models.output.MessageOutputModel

interface MessageRepository {
    suspend fun sendMessage(receiverId: Int, message: String)
    fun getMessage(receiverId: Int): Flow<PagingData<MessageOutputModel>>
}
