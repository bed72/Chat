package com.bed.chat.external.datasources.local

import android.annotation.SuppressLint

import java.util.Base64
import javax.crypto.Cipher
import javax.inject.Inject
import javax.crypto.spec.SecretKeySpec

import com.bed.chat.data.datasources.CryptographyDatasource

class LocalCryptographyDatasource @Inject constructor() : CryptographyDatasource {
    @SuppressLint("GetInstance")
    private val cipher = Cipher.getInstance(AES_TRANSFORMATION)
    private val key = SecretKeySpec(AES_KEY.toByteArray(), AES_ALGORITHM)

    override fun encrypt(value: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val data = cipher.doFinal(value.toByteArray())

        return Base64.getEncoder().encodeToString(data)
    }

    override fun decrypt(value: String): String {
        if (value.isEmpty()) return value

        cipher.init(Cipher.DECRYPT_MODE, key)
        val data = cipher.doFinal(Base64.getDecoder().decode(value))

        return String(data)
    }

    private companion object {
        private const val AES_ALGORITHM = "AES"
        private const val AES_TRANSFORMATION = "AES/ECB/PKCS5Padding"
        private const val AES_KEY = "2u6bwmz0fax7e7v0d0yxt33dwp925pjn"
    }
}
