package com.bed.chat.external.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.chat.domain.repositories.AuthenticationRepository
import com.bed.chat.domain.repositories.storage.StorageRepository
import com.bed.chat.domain.repositories.storage.SelfUserStorageRepository

import com.bed.chat.data.repositories.AuthenticationRepositoryImpl
import com.bed.chat.data.repositories.storage.StorageRepositoryImpl
import com.bed.chat.data.repositories.storage.SelfUserStorageRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Binds
    fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    fun bindSelfUserStorageRepository(repository: SelfUserStorageRepositoryImpl): SelfUserStorageRepository
}
