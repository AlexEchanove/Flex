package com.example.flex;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestProfileTab {
    @Test
    public void testCreateUserButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.tabLayout)).check(ViewAssertions.matches(isDisplayed()));
            Espresso.onView(withText("Profile")).perform(click());
            Espresso.onView(withId(R.id.viewLayout)).check(ViewAssertions.matches(isDisplayed()));
        }
    }
}
