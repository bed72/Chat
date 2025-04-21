package com.bed.chat.domain.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import com.bed.chat.domain.exceptions.ValidationMessage
import com.bed.chat.domain.exceptions.ValidationException

internal class EmailValueTest {
    @Test
    fun `Should return the Email when value is valid`() {
        val email = EmailValue("email@email.com")

        assertTrue(email.isSuccess)
        email.map { assertEquals("email@email.com", it()) }
    }

    @Test
    fun `Should return failure message when value is null`() {
        val email = EmailValue(null)

        assertTrue(email.isFailure)
        email.onFailure { failure ->
            assertTrue(failure is ValidationException.EmailEmpty)
            assertEquals(ValidationMessage.EMAIL_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is empty`() {
        val email = EmailValue("")

        assertTrue(email.isFailure)
        email.onFailure { failure ->
            assertTrue(failure is ValidationException.EmailEmpty)
            assertEquals(ValidationMessage.EMAIL_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is invalid`() {
        listOf("email@email", "email@email.", "emailemail.com", "email@emailcom").forEach {
            val email = EmailValue(it)

            assertTrue(email.isFailure)
            email.onFailure { failure ->
                assertTrue(failure is ValidationException.EmailInvalid)
                assertEquals(ValidationMessage.EMAIL_INVALID.message, failure.message)
            }
        }
    }
}
