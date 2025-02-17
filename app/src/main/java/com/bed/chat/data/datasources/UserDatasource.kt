package com.bed.chat.data.datasources

import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.user.PaginatedUserResponse

interface UserDatasource {
    suspend operator fun invoke(parameter: PaginationRequest): Result<PaginatedUserResponse>
}
