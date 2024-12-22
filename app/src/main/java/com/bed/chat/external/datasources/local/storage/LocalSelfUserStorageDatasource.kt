package com.bed.chat.external.datasources.local.storage

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

import androidx.datastore.core.DataStore

import com.bed.chat.SelfUser
import com.bed.chat.data.datasources.storage.SelfUserStorageDatasource

class LocalSelfUserStorageDatasource @Inject constructor(
    private val datastore: DataStore<SelfUser>,
) : SelfUserStorageDatasource {
    override val user: Flow<SelfUser> get() = datastore.data

    override suspend fun delete() {
        datastore.updateData {
            it.toBuilder()
                .clearId()
                .clearUsername()
                .clearLastName()
                .clearFirstName()
                .clearPictureUrl()
                .build()
        }
    }

    override suspend fun save(
        username: String,
        lastName: String,
        firstName: String,
        pictureUrl: String
    ) {
        datastore.updateData {
            it.toBuilder()
                .setUsername(username)
                .setLastName(lastName)
                .setFirstName(firstName)
                .setPictureUrl(pictureUrl)
                .build()
        }
    }
}
