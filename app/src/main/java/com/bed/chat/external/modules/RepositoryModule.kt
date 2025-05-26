package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.components.ViewModelComponent

import com.bed.chat.domain.repositories.ChatRepository
import com.bed.chat.domain.repositories.UsersRepository
import com.bed.chat.domain.repositories.TokenRepository
import com.bed.chat.domain.repositories.MessageRepository
import com.bed.chat.domain.repositories.NotificationRepository
import com.bed.chat.domain.repositories.FirebaseTokenRepository
import com.bed.chat.domain.repositories.AuthenticationRepository
import com.bed.chat.domain.repositories.storage.StorageRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

import com.bed.chat.data.repositories.ChatRepositoryImpl
import com.bed.chat.data.repositories.UsersRepositoryImpl
import com.bed.chat.data.repositories.TokenRepositoryImpl
import com.bed.chat.data.repositories.MessageRepositoryImpl
import com.bed.chat.data.repositories.NotificationRepositoryImpl
import com.bed.chat.data.repositories.FirebaseTokenRepositoryImpl
import com.bed.chat.data.repositories.AuthenticationRepositoryImpl
import com.bed.chat.data.repositories.storage.StorageRepositoryImpl
import com.bed.chat.data.repositories.storage.SelfUserRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryByViewModelModule {
    @Binds
    fun bindChatRepository(repository: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindUserRepository(repository: UsersRepositoryImpl): UsersRepository

    @Binds
    fun bindMessageRepository(repository: MessageRepositoryImpl): MessageRepository

    @Binds
    fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBySingletonModule {
    @Binds
    @Singleton
    fun bindTokenRepository(repository: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Binds
    @Singleton
    fun bindSelfUserRepository(repository: SelfUserRepositoryImpl): SelfUserRepository

    @Binds
    @Singleton
    fun bindNotificationRepository(repository: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    fun bindFirebaseTokenRepository(repository: FirebaseTokenRepositoryImpl): FirebaseTokenRepository
}
