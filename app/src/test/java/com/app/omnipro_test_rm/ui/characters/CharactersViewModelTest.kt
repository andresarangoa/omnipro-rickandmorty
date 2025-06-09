package com.app.omnipro_test_rm.ui.characters

import androidx.paging.PagingData
import app.cash.turbine.test
import com.app.omnipro_test_rm.data.network.ConnectivityObserver
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.domain.usecases.GetCharactersUseCase
import com.app.omnipro_test_rm.domain.usecases.ObserveConnectivityUseCase
import com.app.omnipro_test_rm.domain.usecases.ToggleFavoriteUseCase
import com.app.omnipro_test_rm.ui.config.MockedObjects
import com.app.omnipro_test_rm.ui.screens.characters.CharactersViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private lateinit var viewModel: CharactersViewModel
    private val mockGetCharactersUseCase = mockk<GetCharactersUseCase>()
    private val mockToggleFavoriteUseCase = mockk<ToggleFavoriteUseCase>(relaxed = true)
    private val mockObserveConnectivityUseCase = mockk<ObserveConnectivityUseCase>()
    
    private val testDispatcher = StandardTestDispatcher()

    // Mock data
    private val mockCharacter = MockedObjects.mockCharacters[0]

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Default mock behavior
        every { mockGetCharactersUseCase() } returns flowOf(PagingData.from(listOf(mockCharacter)))
        every { mockObserveConnectivityUseCase() } returns flowOf(ConnectivityObserver.Status.Available)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial state should be default CharactersUiState`() = runTest {
        // When
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(false, state.isShowingFavorites)
            assertEquals(false, state.isLoading)
            assertEquals(emptyList<CharacterRickAndMorty>(), state.favoriteCharacterRickAndMorties)
            assertNull(state.error)
            // Note: We don't test characters flow directly as it's set in init
        }
    }

    @Test
    fun `loadCharacters should be called during initialization`() = runTest {
        // When
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Then
        coVerify { mockGetCharactersUseCase() }
    }

    @Test
    fun `networkStatus should expose connectivity status`() = runTest {
        // Given
        val connectivityFlow = flowOf(
            ConnectivityObserver.Status.Available,
            ConnectivityObserver.Status.Unavailable,
            ConnectivityObserver.Status.Losing
        )
        every { mockObserveConnectivityUseCase() } returns connectivityFlow

        // When
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Then
        viewModel.networkStatus.test {
            assertEquals(ConnectivityObserver.Status.Available, awaitItem())
            assertEquals(ConnectivityObserver.Status.Unavailable, awaitItem())
            assertEquals(ConnectivityObserver.Status.Losing, awaitItem())
        }
    }

    @Test
    fun `toggleFavorite should call use case successfully`() = runTest {
        // Given
        val characterId = "1"
        coEvery { mockToggleFavoriteUseCase(characterId) } returns Unit
        
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // When
        viewModel.toggleFavorite(characterId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockToggleFavoriteUseCase(characterId) }
        
        // Verify no error is set
        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.error)
        }
    }

    @Test
    fun `toggleFavorite should set error when use case fails`() = runTest {
        // Given
        val characterId = "1"
        val errorMessage = "Failed to toggle favorite"
        coEvery { mockToggleFavoriteUseCase(characterId) } throws Exception(errorMessage)
        
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // When
        viewModel.toggleFavorite(characterId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockToggleFavoriteUseCase(characterId) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
        }
    }

    @Test
    fun `clearError should remove error from state`() = runTest {
        // Given
        val characterId = "1"
        val errorMessage = "Failed to toggle favorite"
        coEvery { mockToggleFavoriteUseCase(characterId) } throws Exception(errorMessage)
        
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Set an error first
        viewModel.toggleFavorite(characterId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.clearError()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.error)
        }
    }

    @Test
    fun `multiple toggleFavorite calls should work correctly`() = runTest {
        // Given
        val characterId1 = "1"
        val characterId2 = "2"
        coEvery { mockToggleFavoriteUseCase(any()) } returns Unit
        
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // When
        viewModel.toggleFavorite(characterId1)
        viewModel.toggleFavorite(characterId2)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockToggleFavoriteUseCase(characterId1) }
        coVerify { mockToggleFavoriteUseCase(characterId2) }
    }

    @Test
    fun `connectivity status changes should be properly propagated`() = runTest {
        // Given
        val connectivityFlow = flowOf(
            ConnectivityObserver.Status.Available,
            ConnectivityObserver.Status.Losing,
            ConnectivityObserver.Status.Unavailable
        )
        every { mockObserveConnectivityUseCase() } returns connectivityFlow

        // When
        viewModel = CharactersViewModel(
            mockGetCharactersUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Then
        viewModel.networkStatus.test {
            assertEquals(ConnectivityObserver.Status.Available, awaitItem())
            assertEquals(ConnectivityObserver.Status.Losing, awaitItem())
            assertEquals(ConnectivityObserver.Status.Unavailable, awaitItem())
        }
    }
}