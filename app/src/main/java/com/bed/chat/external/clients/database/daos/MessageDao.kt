package com.bed.chat.external.clients.database.daos

import androidx.paging.PagingSource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import com.bed.chat.external.clients.database.DatabaseInformation
import com.bed.chat.external.clients.database.entities.MessageEntity

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(parameter: List<MessageEntity>)

    @Query("DELETE FROM ${DatabaseInformation.Tables.MESSAGE} WHERE ${DatabaseInformation.Columns.MESSAGE_RECEIVER_ID} = :parameter")
    suspend fun delete(parameter: Int)

    @Suppress("MaxLineLength")
    @Query(
        "SELECT * FROM ${DatabaseInformation.Tables.MESSAGE} WHERE ${DatabaseInformation.Columns.MESSAGE_RECEIVER_ID} = :parameter  ORDER BY ${DatabaseInformation.Columns.MESSAGE_TIMESTAMP} DESC"
    )
    fun get(parameter: Int): PagingSource<Int, MessageEntity>
}
