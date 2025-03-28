package com.bed.chat.external.clients.http

import android.util.Log

import javax.inject.Inject

import android.accounts.NetworkErrorException

import kotlinx.coroutines.flow.firstOrNull

import io.ktor.http.isSuccess
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode

import io.ktor.client.call.body

import io.ktor.client.HttpClientConfig
import io.ktor.client.HttpClient as KtorClient

import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig

import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.HttpRequestBuilder

import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter

import com.bed.chat.domain.exception.NetworkException

import com.bed.chat.domain.repositories.TokenRepository

interface HttpClient {
    val http: KtorClient
}

class HttpClientImpl @Inject constructor(
    private val repository: TokenRepository
) : HttpClient {

    override val http get() = KtorClient(CIO) {
        expectSuccess = true

        configureAuth()
        configureRetry()
        configureLogging()
        configureWebSocket()
        configureResponseTimeout()
        configureResponseObserver()
        configureContentNegotiation()
        configureValidationResponse()

        defaultRequest {
            headers { header(HttpHeaders.ContentType, ContentType.Application.Json) }
        }
    }
//        .apply {
//        plugin(HttpSend).intercept { request ->
//            repository.get().firstOrNull()?.let { token ->
//                request.headers {
//                    append("Authorization", "Bearer $token")
//                }
//            }
//
//            execute(request)
//        }
//    }

    private fun HttpClientConfig<CIOEngineConfig>.configureWebSocket() {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureAuth() {
        install(Auth) {
            bearer {
                loadTokens {
                    repository.get().firstOrNull()?.let { token ->
                        BearerTokens(token, "")
                    }
                }
            }
        }
    }

    @Suppress("UnusedPrivateMember")
    private fun HttpClientConfig<CIOEngineConfig>.configureRetry() {
        install(HttpRequestRetry) {
            maxRetries = RETRY
            delayMillis { it * DELAY_RETRY }
            retryIf { _, response -> !response.status.isSuccess() }
            retryOnExceptionIf { _, cause -> cause is NetworkErrorException }
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureLogging() {
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.DEFAULT
            filter { it.url.host.contains("https") }
            sanitizeHeader { it == HttpHeaders.Authorization }
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
            socketTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
            connectTimeoutMillis = TIMEOUT
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureContentNegotiation() {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    encodeDefaults = false

                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureValidationResponse() {
        HttpResponseValidator {
            handleResponseExceptionWithRequest { cause, _ ->
                val exception = cause as? ClientRequestException ?: return@handleResponseExceptionWithRequest

                throw when (exception.response.status) {
                    HttpStatusCode.NotFound -> NetworkException.NotFoundException(cause)
                    HttpStatusCode.BadRequest -> NetworkException.BadRequestException(cause)
                    HttpStatusCode.Unauthorized -> NetworkException.UnauthorizedException(cause)
                    HttpStatusCode.InternalServerError -> NetworkException.ServerErrorException(cause)
                    HttpStatusCode.UnprocessableEntity -> NetworkException.UnprocessableEntityException(cause)
                    else -> NetworkException.UnknownException(cause)
                }
            }
        }
    }

    private companion object {
        private const val RETRY = 2
        private const val TIMEOUT = 15000L
        private const val DELAY_RETRY = 2000L
    }
}


suspend inline fun <reified S : Any> KtorClient.request(
    crossinline block: HttpRequestBuilder.() -> Unit,
): Result<S> = runCatching {
    val response = request { block() }

    close()

    response.body<S>()
}
