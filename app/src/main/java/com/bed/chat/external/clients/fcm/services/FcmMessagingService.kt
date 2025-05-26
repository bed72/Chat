package com.bed.chat.external.clients.fcm.services

import javax.inject.Inject

import android.content.Intent
import android.app.PendingIntent

import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull

import dagger.hilt.android.AndroidEntryPoint

import androidx.core.net.toUri
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService

import com.bed.chat.R
import com.bed.chat.ChatApp

import com.bed.chat.presentation.MainActivity
import com.bed.chat.presentation.shared.routes.CHAT_URI

import com.bed.chat.external.mappers.NotificationMapper
import com.bed.chat.external.clients.fcm.models.NotificationModel

import com.bed.chat.domain.repositories.NotificationRepository
import com.bed.chat.domain.repositories.storage.SelfUserRepository

@AndroidEntryPoint
class FcmMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var mapper: NotificationMapper

    @Inject
    lateinit var repository: SelfUserRepository

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        scope.launch {
            repository.user.firstOrNull()?.let { user ->
               message.data["messagePayload"]?.let {
                   val data = mapper(it)
                   notificationRepository.notifyNewMessage(data)
                   sendNotification(data)
                }
            }
        }
    }

    private fun sendNotification(parameter: NotificationModel) {
        if (ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.POST_NOTIFICATIONS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) return

        val builder = buildNotification(parameter)
        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(parameter.id, builder.build())
    }

    private fun buildNotification(parameter: NotificationModel): NotificationCompat.Builder {
        val intent = buildIntent(parameter)
        val pending = buildPendingIntent(intent)

        return NotificationCompat.Builder(applicationContext, ChatApp.CHANNEL_ID)
            .setContentTitle(parameter.name)
            .setContentText(parameter.message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(pending)
    }

    private fun buildIntent(parameter: NotificationModel) = Intent(applicationContext, MainActivity::class.java).apply {
        action = Intent.ACTION_VIEW
        data = "$CHAT_URI/${parameter.id}".toUri()
    }

    private fun buildPendingIntent(parameter: Intent) = PendingIntent.getActivity(
        applicationContext,
        0,
        parameter,
        PendingIntent.FLAG_IMMUTABLE
    )
}
