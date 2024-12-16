package com.bed.chat.data.datasources

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.request.SignUpRequest
import com.bed.chat.external.clients.response.UserResponse
import com.bed.chat.external.clients.response.ImageResponse
import com.bed.chat.external.clients.response.TokenResponse

interface AuthenticationDatasource {
    suspend fun signUp(parameter: SignUpRequest): Result<Unit>
    suspend fun signIn(parameter: SignInRequest): Result<TokenResponse>
    suspend fun validateToken(parameter: String): Result<UserResponse>
    suspend fun uploadProfilePicture(filePath: String): Result<ImageResponse>
}
