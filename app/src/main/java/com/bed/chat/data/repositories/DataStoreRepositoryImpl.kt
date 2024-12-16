package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.Constants

import com.bed.chat.data.datasources.DataStoreDatasource
import com.bed.chat.data.datasources.CryptographyDatasource

import com.bed.chat.domain.repositories.DataStoreRepository

class DataStoreRepositoryImpl @Inject constructor(
    private val storageDatasource: DataStoreDatasource,
    private val cryptographyDatasource: CryptographyDatasource
) : DataStoreRepository {
    override suspend fun delete(data: String) {
        storageDatasource.delete(data)
    }

    override suspend fun save(data: Pair<Constants, String>) {
        val key = data.first.value
        val value = cryptographyDatasource.encrypt(data.second)

        storageDatasource.save(key to value)
    }

    override suspend fun get(data: String): Flow<String> =
        storageDatasource.get(data).map {
            cryptographyDatasource.decrypt(it)
        }
}
