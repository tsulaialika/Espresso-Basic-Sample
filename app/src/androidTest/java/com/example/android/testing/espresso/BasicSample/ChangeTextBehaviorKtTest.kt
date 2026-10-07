/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.espresso.BasicSample

import androidx.test.ext.junit.rules.activityScenarioRule
import android.app.Activity
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.testing.espresso.BasicSample.Helper.getText
import com.example.android.testing.espresso.BasicSample.Helper.tap
import com.example.android.testing.espresso.BasicSample.Helper.typeText
import com.example.android.testing.espresso.BasicSample.Helper.waitForViewVisible
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * The kotlin equivalent to the ChangeTextBehaviorTest, that
 * showcases simple view matchers and actions like [ViewMatchers.withId],
 * [ViewActions.click] and [ViewActions.typeText], and ActivityScenarioRule
 *
 *
 * Note that there is no need to tell Espresso that a view is in a different [Activity].
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeTextBehaviorKtTest {

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    private val favoriteFood = "Pizza"
    private val firstMovie = "Harry Potter and the Sorcerer's Stone"
    private val secondMovie = "The Lord of the Rings: The Fellowship of the Ring"

    @Test
    fun verifyTextChangeOnSameScreen() {
        userInputView.typeText(favoriteFood)
        onView(userInputView).perform(closeSoftKeyboard())

        confirmChangeBtn.tap()

        onView(headerLabel)
            .check(matches(isDisplayed()))
            .check(matches(withText(favoriteFood)))
    }

    @Test
    fun verifyTextChangeAcrossActivities() {
        userInputView.typeText(firstMovie)
        onView(userInputView).perform(closeSoftKeyboard())

        confirmChangeBtn.tap()

        onView(headerLabel)
            .check(matches(isDisplayed()))
            .check(matches(withText(firstMovie)))

        onView(userInputView).perform(clearText())

        userInputView.typeText(secondMovie)
        onView(userInputView).perform(closeSoftKeyboard())

        navigateActivityBtn.tap()

        onView(resultLabel)
            .check(matches(isDisplayed()))
            .check(matches(withText(secondMovie)))
    }

    companion object {
        private val userInputView: Matcher<View> by lazy {
            withId(R.id.editTextUserInput)
        }

        private val confirmChangeBtn: Matcher<View> by lazy {
            withId(R.id.changeTextBt)
        }

        private val headerLabel: Matcher<View> by lazy {
            withId(R.id.textToBeChanged)
        }

        private val navigateActivityBtn: Matcher<View> by lazy {
            withId(R.id.activityChangeTextBtn)
        }

        private val resultLabel: Matcher<View> by lazy {
            withId(R.id.show_text_view)
        }
    }
}