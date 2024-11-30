package com.bed.chat.domain.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import com.bed.chat.domain.models.exception.ValidationMessagesModel
import com.bed.chat.domain.models.exception.ValidationExceptionModel

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
            assertTrue(failure is ValidationExceptionModel.EmailEmpty)
            assertEquals(ValidationMessagesModel.EMAIL_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is empty`() {
        val email = EmailValue("")

        assertTrue(email.isFailure)
        email.onFailure { failure ->
            assertTrue(failure is ValidationExceptionModel.EmailEmpty)
            assertEquals(ValidationMessagesModel.EMAIL_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is invalid`() {
        listOf("email@email", "email@email.", "emailemail.com", "email@emailcom").forEach {
            val email = EmailValue(it)

            assertTrue(email.isFailure)
            email.onFailure { failure ->
                assertTrue(failure is ValidationExceptionModel.EmailInvalid)
                assertEquals(ValidationMessagesModel.EMAIL_INVALID.message, failure.message)
            }
        }
    }
}
