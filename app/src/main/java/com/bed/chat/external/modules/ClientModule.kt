package com.bed.chat.external.modules

import javax.inject.Singleton

import android.content.Context

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import androidx.room.Room

import androidx.datastore.dataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import com.bed.chat.SelfUser

import com.bed.chat.BuildConfig
import com.bed.chat.domain.repositories.SessionRepository

import com.bed.chat.domain.repositories.TokenRepository

import com.bed.chat.external.clients.database.ChatDatabase
import com.bed.chat.external.clients.database.DatabaseInformation

import com.bed.chat.external.clients.websocket.WebSocketClient
import com.bed.chat.external.clients.websocket.WebSocketClientImpl

import com.bed.chat.external.clients.http.configureLogging
import com.bed.chat.external.clients.http.configureWebSocket
import com.bed.chat.external.clients.http.configureDefaultHeaders
import com.bed.chat.external.clients.http.configureResponseTimeout
import com.bed.chat.external.clients.http.authenticationInterceptor
import com.bed.chat.external.clients.http.configureContentNegotiation
import com.bed.chat.external.clients.http.configureValidationResponse

import com.bed.chat.external.datasources.local.serializer.SelfUserSerializer

@Module
@InstallIn(SingletonComponent::class)
interface WebSocketClientModule {
    @Binds
    @Singleton
    fun bindWebSocketClient(client: WebSocketClientImpl): WebSocketClient
}

@Module
@InstallIn(SingletonComponent::class)
object StorageClientModule {
    @Provides
    @Singleton
    fun provideDatabaseClient(@ApplicationContext context: Context): ChatDatabase = Room.databaseBuilder(
        context = context,
        ChatDatabase::class.java,
        DatabaseInformation.NAME
    ).build()

    @Provides
    @Singleton
    fun provideSelfUserDataStoreClient(@ApplicationContext context: Context): DataStore<SelfUser> =
        dataStore(SelfUser::class.java.name, SelfUserSerializer)
            .getValue(context, SelfUserSerializer::javaClass)

    @Provides
    @Singleton
    fun provideDataStoreClient(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = StorageClientModule.javaClass.name).getValue(context, String::javaClass)
}

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Provides
    @Singleton
    fun provideHttpClient(
        tokenRepository: TokenRepository,
        sessionRepository: SessionRepository
    ): HttpClient = HttpClient(CIO) {
        expectSuccess = true

        configureWebSocket()
        configureDefaultHeaders()
        configureResponseTimeout()
        configureContentNegotiation()
        configureValidationResponse(sessionRepository)
        if (BuildConfig.DEBUG) configureLogging()
    }
        .apply {
            authenticationInterceptor(tokenRepository)
        }
}
