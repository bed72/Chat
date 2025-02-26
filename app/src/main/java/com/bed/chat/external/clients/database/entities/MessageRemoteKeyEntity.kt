package com.bed.chat.external.clients.database.entities

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

import com.bed.chat.external.clients.database.DatabaseInformation

@Entity(tableName = DatabaseInformation.Tables.REMOTE_KEY)
data class MessageRemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseInformation.Columns.REMOTE_KEY_RECEIVER_ID)
    val receiverId: Int,

    @ColumnInfo(name = DatabaseInformation.Columns.REMOTE_KEY_NEXT_OFFSET)
    val nextOffset: Int?,
)
