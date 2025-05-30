package com.bed.chat.data.repositories.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import com.bed.chat.data.datasources.storage.SelfUserStorageDatasource

import com.bed.chat.domain.models.output.UserOutputModel
import com.bed.chat.domain.repositories.storage.SelfUserRepository

class SelfUserRepositoryImpl @Inject constructor(
    private val datasource: SelfUserStorageDatasource,
) : SelfUserRepository {

    override val user: Flow<UserOutputModel> = datasource.user.map {
        UserOutputModel(
            id = it.id,
            self = false,
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
        id: Int,
        username: String,
        lastName: String,
        firstName: String,
        pictureUrl: String
    ) {
        with (datasource) {
            delete()
            save(id, username, lastName, firstName, pictureUrl)
        }
    }
}
