package com.bed.chat.external.datasources.remoto

import javax.inject.Inject

import io.ktor.http.HttpMethod

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.client.request.setBody

import com.bed.chat.data.datasources.NotificationDatasource

import com.bed.chat.external.clients.http.HttpUrl
import com.bed.chat.external.clients.http.request
import com.bed.chat.external.clients.http.request.TokenRequest

class RemoteNotificationDatasource @Inject constructor(
    private val client: HttpClient,
) : NotificationDatasource {
    override suspend fun registerToken(parameter: TokenRequest): Result<Unit> =
        client.request<Unit> {
            setBody(parameter)
            method = HttpMethod.Post
            url(HttpUrl.NOTIFICATIONS.value)
        }
}
