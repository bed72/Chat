package com.bed.chat.external.clients

import android.util.Log

import javax.inject.Inject

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

import kotlinx.serialization.json.Json

import io.ktor.serialization.kotlinx.json.json

import io.ktor.client.call.body

import io.ktor.client.HttpClientConfig
import io.ktor.client.HttpClient as KtorClient

import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig

import io.ktor.client.request.request
import io.ktor.client.request.HttpRequestBuilder

import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
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
        configureResponseTimeout()
        configureResponseObserver()
        configureContentNegotiation()

        defaultRequest { url(HttpUrl.API.value) }
    }

    private fun HttpClientConfig<CIOEngineConfig>.configureLogging() {
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.DEFAULT
            filter { it.url.host.contains("http") }
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

@Suppress("ThrowingExceptionsWithoutMessageOrCause")
suspend inline fun <reified F : Any, reified S : Any> KtorClient.request(
    crossinline block: HttpRequestBuilder.() -> Unit,
): Result<S> {
    return Result.failure(Exception())
//    try {
//        val response = request { block() }
//
//        close()
//
//        return when (response.status) {
//            HttpStatusCode.OK, HttpStatusCode.Created -> response.body<S>().right()
//            else -> response.body<F>().left()
//        }
//    } catch (exception: Exception) {
//        exception.printStackTrace()
//        return (defaultFailure().body as F).left()
//    }
}
