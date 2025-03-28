package com.bed.chat.domain.models.input

import java.time.Instant

import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

data class MessageDataInputModel @OptIn(ExperimentalUuidApi::class) constructor(
    val text: String,
    val receiverId: Int,
    val messageId: Uuid = Uuid.random(),
    val timestamp: Long = Instant.now().toEpochMilli()
)
