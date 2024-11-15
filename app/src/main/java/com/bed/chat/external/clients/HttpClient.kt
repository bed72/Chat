package com.bed.chat.external.clients

import android.util.Log

import arrow.core.left
import arrow.core.right
import arrow.core.Either

import javax.inject.Inject

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import kotlinx.serialization.json.Json

import io.ktor.serialization.kotlinx.json.json

import io.ktor.client.call.body

import io.ktor.client.HttpClientConfig
import io.ktor.client.HttpClient as KtorClient

import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig

import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.HttpRequestBuilder

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

interface HttpClient {
    val http: KtorClient
}

class HttpClientImpl @Inject constructor() : HttpClient {

    private val timeout = 15000L

    private val configureJson get() = Json {
        explicitNulls = false
        encodeDefaults = false

        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    override val http get() = KtorClient(CIO) {
        expectSuccess = true

        configureLogging()
        configureRequestDefault()
        configureResponseTimeout()
        configureResponseObserver()
        configureContentNegotiation()
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureLogging() {
        install(Logging) {
            level = LogLevel.INFO
            level = LogLevel.HEADERS
            filter { it.url.host.contains("http") }
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureRequestDefault() {
        install(DefaultRequest) {
            url(HttpUrl.API.value)
            headers {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureResponseObserver() {
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("[KTOR HTTP STATUS]:", "\n ${response.status.value}\n\n")
                Log.d("[KTOR HTTP RESPONSE]:", "\n ${response.body<String>()}\n\n")
            }
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureResponseTimeout() {
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureContentNegotiation() {
        install(ContentNegotiation) {
            json(configureJson)
        }
    }
}


suspend inline fun <reified F : Any, reified S : Any> KtorClient.request(
    crossinline block: HttpRequestBuilder.() -> Unit,
): Either<F, S> {
    val response = request { block() }

    close()

    return when (response.status) {
        HttpStatusCode.OK, HttpStatusCode.Created -> response.body<S>().right()
        else -> response.body<F>().left()
    }
}
