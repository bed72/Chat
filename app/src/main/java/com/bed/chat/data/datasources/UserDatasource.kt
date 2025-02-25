package com.bed.chat.data.datasources

import com.bed.chat.external.clients.response.UserResponse
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.PaginatedResponse

interface UserDatasource {
    suspend operator fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<UserResponse>>
}
