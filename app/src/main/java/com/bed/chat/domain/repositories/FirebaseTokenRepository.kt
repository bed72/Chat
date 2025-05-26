package com.bed.chat.domain.repositories

interface FirebaseTokenRepository {
    suspend fun getToken(): String
}
