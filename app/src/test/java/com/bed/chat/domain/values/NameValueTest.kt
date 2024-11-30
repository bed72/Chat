package com.bed.chat.domain.values

import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import com.bed.chat.domain.models.exception.ValidationMessagesModel
import com.bed.chat.domain.models.exception.ValidationExceptionModel

internal class NameValueTest {
    @Test
    fun `Should return the Name when value is valid`() {
        val name = NameValue("Magnolia")

        assertTrue(name.isSuccess)
        name.map { assertEquals("Magnolia", it()) }
    }

    @Test
    fun `Should return failure message when value is null`() {
        val email = NameValue(null)

        assertTrue(email.isFailure)
        email.onFailure { failure ->
            assertTrue(failure is ValidationExceptionModel.NameEmpty)
            assertEquals(ValidationMessagesModel.NAME_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is empty`() {
        val password = NameValue("")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationExceptionModel.NameEmpty)
            assertEquals(ValidationMessagesModel.NAME_EMPTY.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is length less than 2`() {
        val password = NameValue("M")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationExceptionModel.NameMinLength)
            assertEquals(ValidationMessagesModel.NAME_MIN_LENGTH.message, failure.message)
        }
    }

    @Test
    fun `Should return failure message when value is length better than 12`() {
        val password = NameValue("MagnoliaMagnoliaMagnolia")

        assertTrue(password.isFailure)
        password.onFailure { failure ->
            assertTrue(failure is ValidationExceptionModel.NameMaxLength)
            assertEquals(ValidationMessagesModel.NAME_MAX_LENGTH.message, failure.message)
        }
    }
}
