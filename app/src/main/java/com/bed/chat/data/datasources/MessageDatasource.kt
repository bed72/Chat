package com.bed.chat.data.datasources

import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.MessageResponse
import com.bed.chat.external.clients.response.PaginatedResponse

interface MessageDatasource {
    suspend operator fun invoke(parameter: Pair<Int, PaginationRequest>): Result<PaginatedResponse<MessageResponse>>
}
