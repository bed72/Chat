package com.bed.chat.domain.repositories

import com.bed.chat.domain.models.output.ChatOutputModel

interface ChatRepository {
    suspend operator fun invoke(limit: Int, offset: Int): Result<List<ChatOutputModel>>
}
