package com.bed.chat.data.datasources

import com.bed.chat.external.clients.http.request.SignInRequest
import com.bed.chat.external.clients.http.request.SignUpRequest
import com.bed.chat.external.clients.http.response.UserResponse
import com.bed.chat.external.clients.http.response.ImageResponse
import com.bed.chat.external.clients.http.response.TokenResponse

interface AuthenticationDatasource {
    suspend fun authenticate(): Result<UserResponse>
    suspend fun signUp(parameter: SignUpRequest): Result<Unit>
    suspend fun signIn(parameter: SignInRequest): Result<TokenResponse>
    suspend fun uploadProfilePicture(filePath: String): Result<ImageResponse>
}
