package com.bed.chat.external.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.chat.domain.repositories.AuthenticationRepository
import com.bed.chat.data.repositories.AuthenticationRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository
}
