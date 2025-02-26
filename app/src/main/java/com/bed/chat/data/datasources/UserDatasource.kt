package com.bed.chat.data.datasources

import com.bed.chat.external.clients.http.response.UserResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

interface UserDatasource {
    suspend operator fun invoke(parameter: PaginationRequest): Result<PaginatedResponse<UserResponse>>
}
