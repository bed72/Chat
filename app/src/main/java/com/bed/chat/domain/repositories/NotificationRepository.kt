package com.bed.chat.domain.repositories

interface NotificationRepository {
    suspend fun getToken(): String
}
