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
    override suspend fun delete(parameter: Constants) {
        storageDatasource.delete(parameter.value)
    }

    override suspend fun save(parameter: Pair<Constants, String>) {
        val key = parameter.first.value
        val value = cryptographyDatasource.encrypt(parameter.second)

        with (storageDatasource) {
            delete(key)
            save(key to value)
        }
    }

    override suspend fun get(parameter: Constants): Flow<String> =
        storageDatasource.get(parameter.value).map {
            cryptographyDatasource.decrypt(it)
        }
}
