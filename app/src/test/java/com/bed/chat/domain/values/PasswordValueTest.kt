package com.bed.chat.domain.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import com.bed.chat.domain.exceptions.ValidationMessage
import com.bed.chat.domain.exceptions.ValidationException

internal class PasswordValueTest {
    @Test
    fun `Should return the Password when value is valid`() {
        val password = PasswordValue("PassW0rd")

        assertTrue(password.isSuccess)
        password.map { assertEquals("PassW0rd", it()) }
    }

    @Test
    fun `Should return failure message when value is null`() {
        val password = PasswordValue(null)

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationException.PasswordEmpty)
            assertEquals(ValidationMessage.PASSWORD_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is empty`() {
        val password = PasswordValue("")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationException.PasswordEmpty)
            assertEquals(ValidationMessage.PASSWORD_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is length less than 6`() {
        val password = PasswordValue("C1ca")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationException.PasswordMinLength)
            assertEquals(ValidationMessage.PASSWORD_MIN_LENGTH.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is length better than 16`() {
        val password = PasswordValue("C1caCacacacacacacacacacac")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationException.PasswordMaxLength)
            assertEquals(ValidationMessage.PASSWORD_MAX_LENGTH.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is not has number`() {
        val password = PasswordValue("Cicadada")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationException.PasswordContainNumber)
            assertEquals(ValidationMessage.PASSWORD_CONTAIN_NUMBER.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is not has capital latter`() {
        val password = PasswordValue("c1cadada")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationException.PasswordContainCapitalLetter)
            assertEquals(ValidationMessage.PASSWORD_CONTAIN_CAPITAL_LETTER.message, failure.message)
        }
    }
}
