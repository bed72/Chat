package com.bed.chat.data.datasources

import com.bed.chat.external.clients.response.UserResponse

interface TokenDatasource {
    suspend fun validateToken(parameter: String): Result<UserResponse>
}
