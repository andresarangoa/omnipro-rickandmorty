package com.app.omnipro_test_rm.ui.config

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import org.robolectric.RuntimeEnvironment
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.test.core.app.ApplicationProvider

/**
 * SnapshotRobot is a class that helps in taking screenshots during tests.
 *
 * @property basePath The base path where the screenshots will be saved.
 * @property rule The ActivityScenarioRule for the ComponentActivity.
 */
class SnapshotRobot(
    private val basePath: String, private val rule: ActivityScenarioRule<ComponentActivity>
) {
    /**
     * Initializes the test case data.
     *
     * @param testCase The test case data to be initialized.
     */
    fun initTestCaseData(testCase: TestCase) {
        // Set the device type qualifier for the runtime environment
        RuntimeEnvironment.setQualifiers(testCase.deviceType.qualifier)

        // Set the test configuration for the runtime environment
        with(testCase.testConfig) {
            RuntimeEnvironment.setQualifiers(localeQualifier)
            RuntimeEnvironment.setQualifiers(orientationQualifier)
            RuntimeEnvironment.setQualifiers(uiModeQualifier)
            RuntimeEnvironment.setFontScale(fontScale.scaleFactor)

            // Set the display scale for the application context
            ApplicationProvider.getApplicationContext<Context>()
                .setDisplayScale(displayScale.scaleFactor)
        }
    }

    /**
     * Initializes the activity and sets the content.
     *
     * @param content The Composable function that will be set as the content of the activity.
     */
    fun initActivityAndSetContent(content: @Composable () -> Unit) {
        rule.scenario.onActivity { activity ->
            // Set the content for the activity
            activity.setContent {
                content()
            }
        }
    }

    /**
     * Takes a screenshot of the given node interaction.
     *
     * @param testCase The test case for which the screenshot will be taken.
     * @param nodeInteraction The node interaction of which the screenshot will be taken.
     */
    fun takeScreenshot(testCase: TestCase, nodeInteraction: SemanticsNodeInteraction) {
        val screenshotPath = provideScreenshotPath(
            testCase.testClassName, testCase.testName, testCase.screenshotId
        )
        nodeInteraction.captureRoboImage(
            screenshotPath, roborazziOptions = RoborazziOptions(
                compareOptions = RoborazziOptions.CompareOptions(
                    changeThreshold = testCase.testConfig.maxPercentDifference
                )
            )
        )
    }

    /**
     * This function generates a list of test cases for different device types and configurations.
     *
     * @param className The name of the class where the test method resides.
     * @param testMethod The name of the test method to be executed.
     * @return A list of TestCase objects, each representing a test case for a specific device type
     * and configuration.
     */
    fun getTestCases(className: String, testMethod: String) = listOf(
        TestCase(
            // Test case for a small phone device
            deviceType = DeviceType.SMALL_PHONE, testClassName = className, testName = testMethod
        ), TestCase(
            // Test case for a Pixel 4a device with a large display scale and extra large font scale
            deviceType = DeviceType.PIXEL_4A,
            testClassName = className,
            testName = testMethod,
            testConfig = TestConfig(
                displayScale = DisplayScale.DISPLAY_X_LARGE, fontScale = FontScale.FONT_XX_LARGE
            )
        ), TestCase(
            // Test case for a Pixel 6 device
            deviceType = DeviceType.PIXEL_6, testClassName = className, testName = testMethod
        ), TestCase(
            // Test case for a Pixel 6 device with dark mode enabled
            deviceType = DeviceType.PIXEL_6,
            testClassName = className,
            testName = testMethod,
            testConfig = TestConfig(darkModeEnabled = true)
        ), TestCase(
            // Test case for a Pixel 7 device
            deviceType = DeviceType.PIXEL_7, testClassName = className, testName = testMethod
        ), TestCase(
            // Test case for a Nexus 9 device
            deviceType = DeviceType.NEXUS_9, testClassName = className, testName = testMethod
        )
    )

    /**
     * Provides the screenshot path.
     *
     * @param testClassName The name of the test class.
     * @param testName The name of the test.
     * @param screenShotId The ID of the screenshot.
     * @return The path of the screenshot.
     */
    private fun provideScreenshotPath(
        testClassName: String, testName: String, screenShotId: String
    ): String =
        "$basePath${testClassName}_${testName}_${screenShotId}${Constants.Screenshot.SCREENSHOT_EXTENSION}"
}