package com.bed.chat.external.clients.fcm.services

import android.util.Log

import android.service.notification.StatusBarNotification
import android.service.notification.NotificationListenerService

class NotificationService : NotificationListenerService() {
    override fun onListenerConnected() {
        super.onListenerConnected()

        Log.d("NLS", "Listener connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val extras = it.notification.extras
            val title = extras.getCharSequence("android.title")?.toString()
            val text = extras.getCharSequence("android.text")?.toString()
            val packageName = it.packageName

            Log.d("NLS", "Notificação recebida de $packageName: $title - $text")
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        sbn?.let {
            Log.d("NLS", "Notificação removida: ${it.packageName}")
        }
    }
}
