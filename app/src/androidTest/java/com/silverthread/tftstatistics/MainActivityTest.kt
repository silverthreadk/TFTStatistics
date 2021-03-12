package com.silverthread.tftstatistics

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.silverthread.tftstatistics.ui.main.MainActivity
import com.silverthread.tftstatistics.util.waitFor
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun MainActivityTest_canBeTypedInto() {
        onView(withId(R.id.summonerInput)).perform(typeText(ID_TO_BE_TYPED), closeSoftKeyboard())
                .check(matches(withText(ID_TO_BE_TYPED)))
    }

    @Test
    fun MainActivityTest_searchSummoner() {
        onView(withId(R.id.summonerInput)).perform(typeText(ID_TO_BE_TYPED), closeSoftKeyboard())
        onView(withId(R.id.search)).perform().perform(click())
        onView(isRoot()).perform(waitFor(5000))
        onView(withId(R.id.summonerName)).check(matches(withText(ID_TO_BE_TYPED)))
    }

    @Test
    fun MainActivityTest_searchWrongSummoner() {
        onView(withId(R.id.summonerInput)).perform(typeText(WRONG_ID_TO_BE_TYPED), closeSoftKeyboard())
        onView(withId(R.id.search)).perform().perform(click())
        onView(isRoot()).perform(waitFor(5000))
        onView(withId(R.id.summonerInput)).check(matches(isDisplayed()))
    }

    companion object {
        val ID_TO_BE_TYPED = "PAKA"
        val WRONG_ID_TO_BE_TYPED = "aaaaaaaaaaaaaaaaa"
    }

}