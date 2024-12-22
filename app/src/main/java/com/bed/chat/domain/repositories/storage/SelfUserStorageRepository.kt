package com.bed.chat.domain.repositories.storage

import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.models.output.UserOutputModel

interface SelfUserStorageRepository {
    val user: Flow<UserOutputModel>

    suspend fun delete()

    suspend fun save(
        id: Int,
        username: String,
        lastName: String,
        firstName: String,
        pictureUrl: String,
    )
}
