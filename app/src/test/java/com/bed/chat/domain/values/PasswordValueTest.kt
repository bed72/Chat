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
    fun `Should return failure message when value is null`() {
        val password = PasswordValue(null)

        assertTrue(password.isLeft())
        password.mapLeft { message ->
            assertEquals("A senha não pode ser vazia.", message.first())
        }
    }

    @Test
    fun `Should return failure message when value is empty`() {
        val password = PasswordValue("")

        assertTrue(password.isLeft())
        password.mapLeft { message ->
            assertEquals("A senha não pode ser vazia.", message.first())
        }
    }

    @Test
    fun `Should return failure message when value is length less than 8`() {
        val password = PasswordValue("C1cada")

        assertTrue(password.isLeft())
        password.mapLeft { message ->
            assertEquals("A senha deve ter no mínimo 8 caracteres.", message.first())
        }
    }

    @Test
    fun `Should return failure message when value is not has number`() {
        val password = PasswordValue("Cicadada")

        assertTrue(password.isLeft())
        password.mapLeft { message ->
            assertEquals("A senha deve conter pelo menos um número.", message.first())
        }
    }

    @Test
    fun `Should return failure message when value is not has capital latter`() {
        val password = PasswordValue("c1cadada")

        assertTrue(password.isLeft())
        password.mapLeft { message ->
            assertEquals("A senha deve conter pelo menos uma letra maiúscula.", message.first())
        }
    }
}
