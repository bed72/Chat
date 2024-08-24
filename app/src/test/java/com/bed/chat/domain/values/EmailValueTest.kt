package com.bed.chat.domain.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

internal class EmailValueTest {
    @Test
    fun `Should return the Email when value is valid`() {
        val email = EmailValue("email@email.com")

        assertTrue(email.isRight())
        email.map { assertEquals("email@email.com", it()) }
    }

    @Test
    fun `Should return failure message when value is null`() {
        val email = EmailValue(null)

        assertTrue(email.isLeft())
        email.mapLeft { message -> assertEquals("O e-mail não pode ser vazio.", message.first()) }
    }

    @Test
    fun `Should return failure message when value is empty`() {
        val email = EmailValue("")

        assertTrue(email.isLeft())
        email.mapLeft { message -> assertEquals("O e-mail não pode ser vazio.", message.first()) }
    }

    @Test
    fun `Should return failure message when value is invalid`() {
        listOf("email@email", "email@email.", "emailemail.com", "email@emailcom").forEach {
            val email = EmailValue(it)

            assertTrue(email.isLeft())
            email.mapLeft { message -> assertEquals("E-mail inválido.", message.first()) }
        }
    }
}
