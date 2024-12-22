package com.bed.chat.data.datasources.storage

import com.bed.chat.SelfUser

import kotlinx.coroutines.flow.Flow

interface SelfUserStorageDatasource {
    val user: Flow<SelfUser>

    suspend fun delete()

    suspend fun save(
        id: Int,
        username: String,
        lastName: String,
        firstName: String,
        pictureUrl: String,
    )
}
