package com.bed.chat.external.clients.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import com.bed.chat.external.clients.database.DatabaseInformation
import com.bed.chat.external.clients.database.entities.MessageRemoteKeyEntity

@Dao
interface MessageRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parameter: MessageRemoteKeyEntity)

    @Query("DELETE FROM ${DatabaseInformation.Tables.REMOTE_KEY} WHERE ${DatabaseInformation.Columns.REMOTE_KEY_RECEIVER_ID} = :parameter")
    suspend fun delete(parameter: Int)

    @Query("SELECT * FROM ${DatabaseInformation.Tables.REMOTE_KEY} WHERE ${DatabaseInformation.Columns.REMOTE_KEY_RECEIVER_ID} = :parameter")
    fun get(parameter: Int): MessageRemoteKeyEntity
}
