package com.bed.chat.data.repositories.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.Constants

import com.bed.chat.data.datasources.CryptographyDatasource
import com.bed.chat.data.datasources.storage.StorageDatasource

import com.bed.chat.domain.repositories.storage.StorageRepository

class StorageRepositoryImpl @Inject constructor(
    private val storageDatasource: StorageDatasource,
    private val cryptographyDatasource: CryptographyDatasource
) : StorageRepository {
    override suspend fun delete(data: String) {
        storageDatasource.delete(data)
    }

    override suspend fun save(data: Pair<Constants, String>) {
        val key = data.first.value
        val value = cryptographyDatasource.encrypt(data.second)

        with (storageDatasource) {
            delete(key)
            save(key to value)
        }
    }

    override suspend fun get(data: String): Flow<String> =
        storageDatasource.get(data).map {
            cryptographyDatasource.decrypt(it)
        }
}
