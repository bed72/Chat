package com.bed.chat.external.datasources.remoto

import android.util.Log
import javax.inject.Inject

import java.io.File
import java.io.FileNotFoundException

import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

import io.ktor.client.plugins.onUpload

import io.ktor.client.request.url
import io.ktor.client.request.setBody
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.MultiPartFormDataContent

import com.bed.chat.external.clients.request
import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.HttpClient

import com.bed.chat.external.clients.request.SignInRequest
import com.bed.chat.external.clients.request.SignUpRequest
import com.bed.chat.external.clients.response.UserResponse
import com.bed.chat.external.clients.response.TokenResponse
import com.bed.chat.external.clients.response.ImageResponse

import com.bed.chat.data.datasources.AuthenticationDatasource

import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider

class RemoteAuthenticationDatasource @Inject constructor(
    private val client: HttpClient
) : AuthenticationDatasource {

    override suspend fun authenticate(): Result<UserResponse> =
        client.http.request<UserResponse> {
            url(HttpUrl.AUTHENTICATE.value)
        }

    override suspend fun signUp(parameter: SignUpRequest): Result<Unit> =
        client.http.request<Unit> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.SIGN_UP.value)
        }

    override suspend fun signIn(parameter: SignInRequest): Result<TokenResponse> =
        client.http.request<TokenResponse> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.SIGN_IN.value)
        }

    override suspend fun uploadProfilePicture(filePath: String): Result<ImageResponse> {
        val file = File(filePath)

        return if (!file.exists()) Result.failure(FileNotFoundException("Imagem não encontrada."))
        else client.http.request<ImageResponse> {
            method = HttpMethod.Post
            url(HttpUrl.UPLOADING.value)
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "profilePicture",
                            file.readBytes(),
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/${file.extension}")
                                append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                            }
                        )
                    },
                )
            )
            onUpload { total, length ->
                Log.d("[UPLOAD]", "Sent $total bytes from $length")
            }
        }
    }
}
