package com.example.amphibians.fake

import com.example.amphibians.ui.AmphibiansUiState
import com.example.amphibians.ui.AmphibiansViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import rules.TestDispatcherRule

class AmphibiansViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun amphibiansViewModel_getAmphibians_verifyAmphibiansUiStateSuccess() =
        runTest {
            val amphibiansViewModel = AmphibiansViewModel(
                amphibiansRepository = FakeNetworkAmphibiansRepository()
            )
            assertEquals(
                AmphibiansUiState.Success(FakeDataSource.amphibiansList),
                amphibiansViewModel.amphibiansUiState
            )
        }
}