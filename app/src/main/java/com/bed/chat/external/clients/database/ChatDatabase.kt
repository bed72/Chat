package com.bed.chat.external.clients.database

import androidx.room.Database
import androidx.room.RoomDatabase

import com.bed.chat.external.clients.database.daos.MessageDao
import com.bed.chat.external.clients.database.daos.MessageRemoteKeyDao

import com.bed.chat.external.clients.database.entities.MessageEntity
import com.bed.chat.external.clients.database.entities.MessageRemoteKeyEntity

@Database(
    version = DatabaseInformation.VERSION,
    entities = [
        MessageEntity::class,
        MessageRemoteKeyEntity::class
    ]
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    abstract fun messageRemoteKeyDao(): MessageRemoteKeyDao
}

object DatabaseInformation {
    const val VERSION = 1
    const val NAME = "chat_database"
    object Tables {
        const val MESSAGE = "message"
        const val REMOTE_KEY = "message_remote_keys"
    }
    object Columns {
        /// Table message_remote_keys
        const val REMOTE_KEY_NEXT_OFFSET = "next_offset"
        const val REMOTE_KEY_RECEIVER_ID = "receiver_id"

        /// Table message
        const val MESSAGE_ID = "id"
        const val MESSAGE_TEXT = "text"
        const val MESSAGE_AUTO_ID = "auto_id"
        const val MESSAGE_IS_UNREAD = "is_unread"
        const val MESSAGE_SENDER_ID = "sender_id"
        const val MESSAGE_TIMESTAMP = "timestamp"
        const val MESSAGE_RECEIVER_ID = "receiver_id"
    }
}
