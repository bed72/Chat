package com.bed.chat.data.datasources

import com.bed.chat.external.clients.response.ChatResponse
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.PaginatedResponse

interface ChatDatasource {
    suspend operator fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<ChatResponse>>
}
