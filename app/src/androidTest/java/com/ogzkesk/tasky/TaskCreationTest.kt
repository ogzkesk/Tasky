package com.ogzkesk.tasky

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ogzkesk.tasky.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TaskCreationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testTaskCreationFlow() {
        composeTestRule.onNodeWithTag("add_task").performClick()

        composeTestRule.onNodeWithTag("title_input")
            .performTextInput("Buy groceries")

        composeTestRule.onNodeWithTag("description_input")
            .performTextInput("Milk, eggs, bread")

        composeTestRule.onNodeWithTag("calendar_input").performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithTag("calendar_dialog").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithTag("create_button").performClick()

        composeTestRule.onNodeWithText("Buy groceries").assertIsDisplayed()
    }
}