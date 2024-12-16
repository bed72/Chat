package com.bed.chat.data.datasources

interface CryptographyDatasource {
    fun encrypt(value: String): String
    fun decrypt(value: String): String
}
