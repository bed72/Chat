package com.bed.chat.data.datasources

import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.chat.PaginatedChatResponse

interface ChatDatasource {
    suspend operator fun invoke(parameter: PaginationRequest): Result<PaginatedChatResponse>
}
