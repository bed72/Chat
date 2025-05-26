package com.bed.chat.data.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow

import com.bed.chat.domain.repositories.NotificationRepository

import com.bed.chat.external.clients.fcm.models.NotificationModel

class NotificationRepositoryImpl @Inject constructor() : NotificationRepository {
    private val _notificationFlow = MutableSharedFlow<NotificationModel>(replay = 1)
    override val notificationFlow = _notificationFlow.asSharedFlow()

    override fun notifyNewMessage(parameter: NotificationModel) {
        if (_notificationFlow.subscriptionCount.value == 0) return

        _notificationFlow.tryEmit(parameter)
    }
}
