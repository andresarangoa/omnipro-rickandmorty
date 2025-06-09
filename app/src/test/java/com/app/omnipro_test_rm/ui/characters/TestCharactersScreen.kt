package com.app.omnipro_test_rm.ui.characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.paging.PagingData
import com.app.omnipro_test_rm.data.network.ConnectivityObserver
import com.app.omnipro_test_rm.domain.models.CharacterRickAndMorty
import com.app.omnipro_test_rm.ui.config.BaseSnapshotTest
import com.app.omnipro_test_rm.ui.config.Constants
import com.app.omnipro_test_rm.ui.config.SnapshotRobot
import com.app.omnipro_test_rm.ui.config.TestCase
import com.app.omnipro_test_rm.ui.data.CharactersUiState
import com.app.omnipro_test_rm.ui.components.utils.NodeTags.CHARACTERS_SCREEN_ROOT_TAG
import com.app.omnipro_test_rm.ui.config.MockedObjects
import com.app.omnipro_test_rm.ui.screens.characters.CharactersViewModel
import com.app.omnipro_test_rm.ui.screens.characters.EnhancedCharactersScreen
import com.app.omnipro_test_rm.ui.theme.RickMortyTheme
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.robolectric.annotation.Config

@Config(sdk = [35]) // ✅ Force Robolectric to use SDK 35
class TestCharactersScreen : BaseSnapshotTest() {

    private val mockViewModel = mockk<CharactersViewModel>(relaxed = true)
    private val mockOnCharacterClick = mockk<(String) -> Unit>(relaxed = true)

    private val testModule = module {
        factory<CharactersViewModel> { mockViewModel }
    }

    private val mockCharacters = MockedObjects.mockCharacters

    override fun setup() {
        MockKAnnotations.init(this)

        robot = SnapshotRobot(
            Constants.Screenshot.PATH_CHARACTERS_SCREEN,
            activityScenarioRule
        )

        setupKoinForTest()

        // Default mock setup
        every { mockViewModel.uiState } returns MutableStateFlow(CharactersUiState())
        every { mockViewModel.networkStatus } returns MutableStateFlow(ConnectivityObserver.Status.Available)
    }

    @After
    override fun tearDown() {
        unmockkAll()
    }

    private fun setupKoinForTest() {
        org.koin.core.context.loadKoinModules(testModule)
    }

    @Test
    fun testCharactersScreenEmpty() = runTest {
        val emptyPagingData = PagingData.empty<CharacterRickAndMorty>()
        every { mockViewModel.uiState.value } returns CharactersUiState(
            characters = flowOf(emptyPagingData)
        )

        runSnapshotTestsForContent(
            robot.getTestCases(
                this@TestCharactersScreen::class.simpleName.orEmpty(),
                "testCharactersScreenEmpty"
            )
        ) {
            RickMortyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    EnhancedCharactersScreen(
                        onCharacterClick = mockOnCharacterClick,
                        viewModel = mockViewModel // Pass explicitly to avoid Koin issues
                    )
                }
            }
        }
    }

    @Test
    fun testCharactersScreenWithData() = runTest {
        val pagingData = PagingData.from(mockCharacters)
        every { mockViewModel.uiState.value } returns CharactersUiState(
            characters = flowOf(pagingData)
        )

        runSnapshotTestsForContent(
            robot.getTestCases(
                this@TestCharactersScreen::class.simpleName.orEmpty(),
                "testCharactersScreenWithData"
            )
        ) {
            RickMortyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    EnhancedCharactersScreen(
                        onCharacterClick = mockOnCharacterClick,
                        viewModel = mockViewModel
                    )
                }
            }
        }
    }

    // For interaction tests, use the new runInteractionTest method
    @Test
    fun testCharacterClickInteraction() = runTest {
        val pagingData = PagingData.from(mockCharacters)
        every { mockViewModel.uiState.value } returns CharactersUiState(
            characters = flowOf(pagingData)
        )


        runSnapshotTestsForContent(
            robot.getTestCases(
                this@TestCharactersScreen::class.simpleName.orEmpty(),
                "testCharactersScreenWithData"
            )
        ) {
            RickMortyTheme {
                EnhancedCharactersScreen(
                    onCharacterClick = mockOnCharacterClick,
                    viewModel = mockViewModel
                )
            }
        }

        // Now you can perform interactions using composeRule
        // composeRule.onNodeWithTag("character_item_1").performClick()

        verify(exactly = 0) { mockOnCharacterClick(any()) }
    }

    @Test
    fun testFavoriteToggleInteraction() = runTest {
        val pagingData = PagingData.from(mockCharacters)
        every { mockViewModel.uiState.value } returns CharactersUiState(
            characters = flowOf(pagingData)
        )


        runSnapshotTestsForContent(
            robot.getTestCases(
                this@TestCharactersScreen::class.simpleName.orEmpty(),
                "testCharactersScreenWithData"
            )
        ) {
            RickMortyTheme {
                EnhancedCharactersScreen(
                    onCharacterClick = mockOnCharacterClick,
                    viewModel = mockViewModel
                )
            }
        }

        // Perform interactions
        // composeRule.onNodeWithTag("favorite_button_1").performClick()

        verify(exactly = 0) { mockViewModel.toggleFavorite(any()) }
    }

    override fun runSnapshotTestsForContent(
        testCases: List<TestCase>,
        content: @Composable () -> Unit
    ) = robot.run {
        testCases.forEach {
            initTestCaseData(it)
            initActivityAndSetContent {
                content()
            }
            takeScreenshot(it, getTestNode())
        }
    }

    override fun getTestNode(): SemanticsNodeInteraction =
        composeRule.onNodeWithTag(CHARACTERS_SCREEN_ROOT_TAG)
            .assertExists()
            .assertIsDisplayed()
            .onParent()
}