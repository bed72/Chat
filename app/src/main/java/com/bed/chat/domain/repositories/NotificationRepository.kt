package com.bed.chat.domain.repositories

import kotlinx.coroutines.flow.SharedFlow

import com.bed.chat.external.clients.fcm.models.NotificationModel

interface NotificationRepository {
    val notificationFlow: SharedFlow<NotificationModel>
    fun notifyNewMessage(parameter: NotificationModel)
}
