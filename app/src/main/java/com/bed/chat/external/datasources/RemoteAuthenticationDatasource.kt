package com.bed.chat.external.datasources

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
import com.bed.chat.external.clients.response.TokenResponse

import com.bed.chat.data.datasources.AuthenticationDatasource
import com.bed.chat.external.clients.response.ImageResponse

class RemoteAuthenticationDatasource @Inject constructor(
    private val client: HttpClient
) : AuthenticationDatasource {
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
            method = HttpMethod.Patch
            url(HttpUrl.UPLOADING.value)
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            "avatar",
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
