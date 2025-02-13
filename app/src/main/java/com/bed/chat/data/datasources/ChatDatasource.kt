package com.bed.chat.data.datasources

import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.PaginatedChatsResponse

interface ChatDatasource {
    suspend fun getChats(parameter: PaginationRequest): Result<PaginatedChatsResponse>
}
