package com.example.abid.maapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class RegisterServiceTest {

    //Test rule to enable launching of activity
    @Rule
    public ActivityTestRule<RegisterService> mActivityRule =
            new ActivityTestRule(RegisterService.class);

    @Test
    public void SelectServiceTypeSpinnerIsDisplayed() {
        onView(withId(R.id.typeOfService)).check(matches(isDisplayed()));
    }
    @Test
    public void NameFieldIsDisplayed() {
        onView(withId(R.id.nameOfServiceProvider)).check(matches(isDisplayed()));
    }
    @Test
    public void ContactFieldIsDisplayed() {
        onView(withId(R.id.contactOfService)).check(matches(isDisplayed()));
    }
    @Test
    public void RegisterServiceButtonIsDisplayed() {
        onView(withId(R.id.registerServiceButton)).check(matches(isDisplayed()));
    }
    @Test
    public void FromTimeSelectionFieldIsDisplayed() {
        onView(withId(R.id.fromTime)).check(matches(isDisplayed()));
    }
    @Test
    public void ToTimeSelectionFieldIsDisplayed() {
        onView(withId(R.id.toTime)).check(matches(isDisplayed()));
    }

} 
