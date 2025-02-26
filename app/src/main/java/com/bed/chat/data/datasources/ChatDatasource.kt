package com.bed.chat.data.datasources

import com.bed.chat.external.clients.http.response.ChatResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

interface ChatDatasource {
    suspend operator fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<ChatResponse>>
}
