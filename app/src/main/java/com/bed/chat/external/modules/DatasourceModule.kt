package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.chat.data.datasources.AuthenticationDatasource
import com.bed.chat.external.datasources.RemoteAuthenticationDatasource

@Module
@InstallIn(SingletonComponent::class)
interface DatasourceModule {
    @Binds
    @Singleton
    fun bindAuthenticationDatasource(datasource: RemoteAuthenticationDatasource): AuthenticationDatasource
}
