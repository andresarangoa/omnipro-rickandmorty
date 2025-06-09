package com.app.omnipro_test_rm.ui.config

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.RickAndMortyApp
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * BaseSnapshotTest is an abstract class that serves as a base for snapshot tests.
 * It is configured to run with Robolectric and uses the native graphics mode.
 * The application used in the configuration is DigitalBankingApplication.
 *
 * @property activityScenarioRule Rule that provides an activity scenario for the ComponentActivity class.
 * @property composeRule Rule that provides an empty Compose rule. This rule can be used to test Compose UI.
 * @property robot late init property that will hold the SnapshotRobot instance.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = RickAndMortyApp::class)
abstract class BaseSnapshotTest : AutoCloseKoinTest() {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(ComponentActivity::class.java)

    @get:Rule
    var composeRule = createComposeRule()

    protected lateinit var robot: SnapshotRobot

    /**
     * Setup method to be implemented by subclasses.
     */
    @Before
    abstract fun setup()

    /**
     * Teardown method to be implemented by subclasses.
     */
    @After
    abstract fun tearDown()


    /**
     * This is an abstract function that runs snapshot tests for a list of test cases and a given Composable content.
     *
     * @param testCases A list of TestCase objects that represent the test cases to be run.
     * @param content A Composable function that represents the UI content to be tested.
     */
    abstract fun runSnapshotTestsForContent(testCases: List<TestCase>, content: @Composable () -> Unit)

    /**
     * Method to get the test node. To be implemented by subclasses.
     *
     * @return SemanticsNodeInteraction instance.
     */
    abstract fun getTestNode(): SemanticsNodeInteraction
}