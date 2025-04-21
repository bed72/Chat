package com.bed.chat.external.clients.http

import kotlinx.serialization.json.Json
import kotlinx.coroutines.flow.firstOrNull

import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode

import io.ktor.client.call.body

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

import io.ktor.client.engine.cio.CIOEngineConfig

import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.HttpRequestBuilder

import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter

import com.bed.chat.domain.exceptions.NetworkException
import com.bed.chat.domain.repositories.TokenRepository

fun HttpClientConfig<CIOEngineConfig>.configureDefaultHeaders() {
    defaultRequest {
        headers { header(HttpHeaders.ContentType, ContentType.Application.Json) }
    }
}

fun HttpClientConfig<CIOEngineConfig>.configureWebSocket() {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
}

fun HttpClientConfig<CIOEngineConfig>.configureLogging() {
    install(Logging) {
        level = LogLevel.ALL
        logger = Logger.DEFAULT
//        filter { it.url.host.contains("https") }
//        sanitizeHeader { it == HttpHeaders.Authorization }
    }
}

fun HttpClientConfig<CIOEngineConfig>.configureResponseTimeout() {
    install(HttpTimeout) {
        socketTimeoutMillis = 25000L
        requestTimeoutMillis = 25000L
        connectTimeoutMillis = 25000L
    }
}

fun HttpClientConfig<CIOEngineConfig>.configureContentNegotiation() {
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

fun HttpClientConfig<CIOEngineConfig>.configureValidationResponse() {
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

fun HttpClient.authenticationInterceptor(repository: TokenRepository) {
    plugin(HttpSend).intercept { request ->
        repository.get().firstOrNull()?.let { token ->
            request.headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        execute(request)
    }
}


suspend inline fun <reified S : Any> HttpClient.request(
    crossinline block: HttpRequestBuilder.() -> Unit,
): Result<S> = runCatching {
    val response = request { block() }

    response.body<S>()
}
