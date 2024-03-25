package com.krisna.diva.githubuser.ui.settings

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.krisna.diva.githubuser.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsActivityTest {
    @Before
    fun setup() {
        ActivityScenario.launch(SettingsActivity::class.java)
    }

    @Test
    fun assertSetDarkLightMode() {
        Espresso.onView(withId(R.id.layout_theme)).perform(ViewActions.click())
        Espresso.onView(withText("Dark")).perform(ViewActions.click())
        Espresso.onView(withText("OK")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.layout_theme)).perform(ViewActions.click())
        Espresso.onView(withText("Light")).perform(ViewActions.click())
        Espresso.onView(withText("OK")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.layout_theme)).perform(ViewActions.click())
        Espresso.onView(withText("System default")).perform(ViewActions.click())
        Espresso.onView(withText("OK")).perform(ViewActions.click())
    }
}