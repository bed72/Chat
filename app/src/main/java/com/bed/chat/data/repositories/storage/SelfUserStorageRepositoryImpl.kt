package com.bed.chat.data.repositories.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import com.bed.chat.data.datasources.storage.SelfUserStorageDatasource

import com.bed.chat.domain.models.output.UserOutputModel
import com.bed.chat.domain.repositories.storage.SelfUserStorageRepository

class SelfUserStorageRepositoryImpl @Inject constructor(
    private val datasource: SelfUserStorageDatasource,
) : SelfUserStorageRepository {

    override val user: Flow<UserOutputModel> get() = datasource.user.map {
        UserOutputModel(
            id = it.id,
            username = it.username,
            lastName = it.lastName,
            firstName = it.firstName,
            profilePicture = it.pictureUrl,
        )
    }

    override suspend fun delete() {
        datasource.delete()
    }

    override suspend fun save(
        username: String,
        lastName: String,
        firstName: String,
        pictureUrl: String
    ) {
        datasource.save(username, lastName, firstName, pictureUrl)
    }
}
