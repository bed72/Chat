package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.chat.external.clients.HttpClient
import com.bed.chat.external.clients.HttpClientImpl

@Module
@InstallIn(SingletonComponent::class)
interface HttpClientModule {
    @Binds
    @Singleton
    fun bindHttpClient(client: HttpClientImpl): HttpClient
}
