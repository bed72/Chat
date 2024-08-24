package com.bed.chat.domain.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

class PasswordValueTest {
    @Test
    fun `Should return the Password when value is valid`() {
        val password = PasswordValue("PassW0rd")

        assertTrue(password.isRight())
        password.map { assertEquals("PassW0rd", it()) }
    }

    @Test
    fun `Should return failure message when value is invalid`() {
        listOf("", "password", "PassWord", "passw0rd", "PassW0r").forEach {
            val password = PasswordValue(it)

            assertTrue(password.isLeft())
            password.mapLeft { message -> assertEquals(Values.INVALID_PASSWORD.message, message.first()) }
        }
    }
}
