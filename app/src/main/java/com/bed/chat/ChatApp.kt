package com.bed.chat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

import dagger.hilt.android.HiltAndroidApp

import androidx.core.app.NotificationManagerCompat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver

@HiltAndroidApp
class ChatApp : Application(), DefaultLifecycleObserver {

    override fun onCreate() {
        super<Application>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        createChannel()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        ON_APP_FOREGROUND = true
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        ON_APP_FOREGROUND = false
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
        const val CHANNEL_ID = "chat_messages"
        var ON_APP_FOREGROUND = false
            private set
    }
}
