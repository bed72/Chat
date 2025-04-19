package com.bed.chat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

import androidx.core.app.NotificationManagerCompat

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        createChannel()
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getText(R.string.notification_channel_description),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        NotificationManagerCompat.from(this).run {
            createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "chat_messages"
    }
}
