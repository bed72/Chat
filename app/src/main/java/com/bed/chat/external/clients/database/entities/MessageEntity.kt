package com.bed.chat.external.clients.database.entities

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

import com.bed.chat.domain.models.output.MessageOutputModel

import com.bed.chat.external.clients.http.response.toTimestamp
import com.bed.chat.external.clients.database.DatabaseInformation

@Entity(tableName = DatabaseInformation.Tables.MESSAGE)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_AUTO_ID)
    val autoId: Int = 0,

    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_ID)
    val id: Int?,

    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_SENDER_ID)
    val senderId: Int,

    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_TEXT)
    val message: String,

    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_RECEIVER_ID)
    val receiverId: Int,

    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_TIMESTAMP)
    val timestamp: Long,

    @ColumnInfo(name = DatabaseInformation.Columns.MESSAGE_IS_UNREAD)
    val isUnread: Boolean,
)

fun MessageEntity.toModel(userId: Int?) = MessageOutputModel(
    id = id,
    autoId = autoId,
    senderId = senderId,
    receiverId = receiverId,
    isUnread = isUnread,
    isSelf = senderId == userId,
    message = message,
    timestamp = timestamp.toTimestamp()
)
