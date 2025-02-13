package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.chat.data.datasources.ChatDatasource
import com.bed.chat.data.datasources.CryptographyDatasource
import com.bed.chat.data.datasources.AuthenticationDatasource
import com.bed.chat.data.datasources.storage.StorageDatasource
import com.bed.chat.data.datasources.storage.SelfUserStorageDatasource

import com.bed.chat.external.datasources.remoto.RemoteChatDatasource
import com.bed.chat.external.datasources.remoto.RemoteAuthenticationDatasource

import com.bed.chat.external.datasources.local.LocalCryptographyDatasource
import com.bed.chat.external.datasources.local.storage.LocalStorageDatasource
import com.bed.chat.external.datasources.local.storage.LocalSelfUserDatasource

@Module
@InstallIn(SingletonComponent::class)
interface DatasourceModule {
    @Binds
    @Singleton
    fun bindRemoteChatsDatasource(datasource: RemoteChatDatasource): ChatDatasource

    @Binds
    @Singleton
    fun bindRemoteAuthenticationDatasource(datasource: RemoteAuthenticationDatasource): AuthenticationDatasource


    @Binds
    @Singleton
    fun bindCryptographyDatasource(datasource: LocalCryptographyDatasource): CryptographyDatasource

    @Binds
    @Singleton
    fun bindLocalDataStoreDatasource(datasource: LocalStorageDatasource): StorageDatasource

    @Binds
    @Singleton
    fun bindLocalSelfUserStorageDatasource(datasource: LocalSelfUserDatasource): SelfUserStorageDatasource
}
