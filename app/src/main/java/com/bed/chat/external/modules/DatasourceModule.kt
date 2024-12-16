package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.chat.data.datasources.DataStoreDatasource
import com.bed.chat.data.datasources.CryptographyDatasource
import com.bed.chat.data.datasources.AuthenticationDatasource
import com.bed.chat.external.datasources.local.LocalDataStoreDatasource
import com.bed.chat.external.datasources.local.LocalCryptographyDatasource
import com.bed.chat.external.datasources.remoto.RemoteAuthenticationDatasource

@Module
@InstallIn(SingletonComponent::class)
interface DatasourceModule {
    @Binds
    @Singleton
    fun bindLocalDataStoreDatasource(datasource: LocalDataStoreDatasource): DataStoreDatasource

    @Binds
    @Singleton
    fun bindCryptographyDatasource(datasource: LocalCryptographyDatasource): CryptographyDatasource

    @Binds
    @Singleton
    fun bindRemoteAuthenticationDatasource(datasource: RemoteAuthenticationDatasource): AuthenticationDatasource
}
