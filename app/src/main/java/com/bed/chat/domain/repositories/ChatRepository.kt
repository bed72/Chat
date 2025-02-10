package com.bed.chat.domain.repositories

import com.bed.chat.domain.models.output.ChatOutputModel

interface ChatRepository {
    suspend fun getChats(limit: Int, offset: Int): Result<List<ChatOutputModel>>
}
