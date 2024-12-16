package com.bed.chat.external.datasources

import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals

import kotlinx.coroutines.test.runTest

import com.bed.chat.external.datasources.local.LocalCryptographyDatasource

class LocalCryptographyDatasourceTest {
    private lateinit var datasource: LocalCryptographyDatasource

    @Before
    fun setup() {
        datasource = LocalCryptographyDatasource()
    }

    @Test
    fun `Should encrypt data with success`() = runTest {
        val data = datasource.encrypt("Magnolia")

        assert(data.isNotEmpty())
        assertEquals("xJfCkrsYtPJS3Molx9atkA==", data)
    }

    @Test
    fun `Should decrypt data with success`() = runTest {
        val data = datasource.decrypt("xJfCkrsYtPJS3Molx9atkA==")

        assert(data.isNotEmpty())
        assertEquals("Magnolia", data)
    }
}
