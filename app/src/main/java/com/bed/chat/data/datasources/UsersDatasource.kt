package com.bed.chat.data.datasources

import com.bed.chat.external.clients.http.response.UserResponse
import com.bed.chat.external.clients.http.request.PaginationRequest
import com.bed.chat.external.clients.http.response.PaginatedResponse

interface UsersDatasource {
    suspend fun getUser(parameter: Int): Result<UserResponse>
    suspend fun getUsers(parameter: PaginationRequest): Result<PaginatedResponse<UserResponse>>
}
