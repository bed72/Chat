package com.bed.chat.external.clients.fcm.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationModel(
    @SerialName("userId")
    val id: Int,

    @SerialName("action")
    val action: String,

    @SerialName("message")
    val message: String,

    @SerialName("userName")
    val name: String,

    @SerialName("profileImageUrl")
    val image: String
)
