package com.app.omnipro_test_rm.ui.detail

import app.cash.turbine.test
import com.app.omnipro_test_rm.data.network.ConnectivityObserver
import com.app.omnipro_test_rm.domain.usecases.GetCharacterDetailUseCase
import com.app.omnipro_test_rm.domain.usecases.ObserveConnectivityUseCase
import com.app.omnipro_test_rm.domain.usecases.ToggleFavoriteUseCase
import com.app.omnipro_test_rm.ui.config.MockedObjects
import com.app.omnipro_test_rm.ui.screens.detail.CharacterDetailViewModel
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private val mockGetCharacterDetailUseCase = mockk<GetCharacterDetailUseCase>()
    private val mockToggleFavoriteUseCase = mockk<ToggleFavoriteUseCase>(relaxed = true)
    private val mockObserveConnectivityUseCase = mockk<ObserveConnectivityUseCase>()
    
    private val testDispatcher = StandardTestDispatcher()
    private val testCharacterId = "1"

    // Mock data
    private val mockCharacter = MockedObjects.mockCharacters[0]

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Default mock behavior
        every { mockObserveConnectivityUseCase() } returns flowOf(ConnectivityObserver.Status.Available)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial state should be default CharacterDetailUiState`() = runTest {
        // Given
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)

        // When
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Then - Check initial state before loadCharacter completes
        val initialState = viewModel.uiState.value
        assertNull(initialState.characterRickAndMorty)
        assertEquals(false, initialState.isLoading)
        assertNull(initialState.error)
    }

    @Test
    fun `loadCharacter should load character successfully on init`() = runTest {
        // Given
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)

        // When
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockGetCharacterDetailUseCase(testCharacterId) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockCharacter, state.characterRickAndMorty)
            assertFalse(state.isLoading)
            assertNull(state.error)
        }
    }

    @Test
    fun `loadCharacter should set loading state during execution`() = runTest {
        // Given
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)

        // When
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )

        // Then - Test the loading flow
        viewModel.uiState.test {
            // Skip initial state
            awaitItem()
            
            // Advance to trigger loading
            testDispatcher.scheduler.advanceTimeBy(1)
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertNull(loadingState.characterRickAndMorty)
            
            // Advance to completion
            testDispatcher.scheduler.advanceUntilIdle()
            val finalState = awaitItem()
            assertFalse(finalState.isLoading)
            assertEquals(mockCharacter, finalState.characterRickAndMorty)
        }
    }

    @Test
    fun `loadCharacter should handle error correctly`() = runTest {
        // Given
        val errorMessage = "Character not found"
        val exception = Exception(errorMessage)
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.failure(exception)

        // When
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockGetCharacterDetailUseCase(testCharacterId) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertFalse(state.isLoading)
            assertNull(state.characterRickAndMorty)
        }
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
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)

        // When
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
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
    fun `toggleFavorite should toggle character favorite status successfully`() = runTest {
        // Given
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)
        coEvery { mockToggleFavoriteUseCase(testCharacterId) } returns Unit
        
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockToggleFavoriteUseCase(testCharacterId) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.characterRickAndMorty?.isFavorite == true)
            assertNull(state.error)
        }
    }

    @Test
    fun `toggleFavorite should handle error when use case fails`() = runTest {
        // Given
        val errorMessage = "Failed to toggle favorite"
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)
        coEvery { mockToggleFavoriteUseCase(testCharacterId) } throws Exception(errorMessage)
        
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockToggleFavoriteUseCase(testCharacterId) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            // Character should remain unchanged
            assertEquals(mockCharacter, state.characterRickAndMorty)
        }
    }

    @Test
    fun `toggleFavorite should do nothing when character is null`() = runTest {
        // Given
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.failure(Exception("Not found"))
        
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { mockToggleFavoriteUseCase(any()) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.characterRickAndMorty)
        }
    }

    @Test
    fun `clearError should remove error from state`() = runTest {
        // Given
        val errorMessage = "Character not found"
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.failure(Exception(errorMessage))
        
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify error is set
        assertEquals(errorMessage, viewModel.uiState.value.error)

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
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(mockCharacter)
        coEvery { mockToggleFavoriteUseCase(testCharacterId) } returns Unit
        
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleFavorite() // false -> true
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.toggleFavorite() // true -> false  
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { mockToggleFavoriteUseCase(testCharacterId) }
        
        viewModel.uiState.test {
            val state = awaitItem()
            // Should be back to false after two toggles
            assertFalse(state.characterRickAndMorty?.isFavorite == true)
        }
    }

    @Test
    fun `viewModel should handle character with different initial favorite status`() = runTest {
        // Given
        val favoriteCharacter = mockCharacter.copy(isFavorite = true)
        coEvery { mockGetCharacterDetailUseCase(testCharacterId) } returns Result.success(favoriteCharacter)
        coEvery { mockToggleFavoriteUseCase(testCharacterId) } returns Unit
        
        viewModel = CharacterDetailViewModel(
            testCharacterId,
            mockGetCharacterDetailUseCase,
            mockToggleFavoriteUseCase,
            mockObserveConnectivityUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            // Should toggle from true to false
            assertFalse(state.characterRickAndMorty?.isFavorite == true)
        }
    }
}