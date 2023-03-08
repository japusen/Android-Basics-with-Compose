package com.example.amphibians.fake

import com.example.amphibians.data.DefaultAmphibiansRepository
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

class NetworkAmphibiansRepositoryTest {
    @Test
    fun networkAmphibiansRepository_getAmphibians_verifyList() =
        runTest {
            val repository = DefaultAmphibiansRepository(
                amphibiansApiService = FakeAmphibiansApiService()
            )
            assertEquals(FakeDataSource.amphibiansList, repository.getAmphibians())
        }
}