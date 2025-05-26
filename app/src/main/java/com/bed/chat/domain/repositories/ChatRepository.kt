package com.bed.chat.domain.repositories

import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.models.output.ChatOutputModel

interface ChatRepository {
    val newMessageReceivedFlow: Flow<Unit>

    suspend operator fun invoke(limit: Int, offset: Int): Result<List<ChatOutputModel>>
}
