package com.bed.chat.external.modules

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.bed.chat.data.datasources.UserDatasource

import com.bed.chat.external.paging.UserPagingSource
import com.bed.chat.external.paging.UserPagingSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {
    @Provides
    @Singleton
    fun providesUserPagingSource(datasource: UserDatasource): UserPagingSource =
        UserPagingSourceImpl(datasource)
}
