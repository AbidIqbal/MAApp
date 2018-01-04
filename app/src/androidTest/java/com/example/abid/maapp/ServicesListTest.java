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

public class ServicesListTest {



    //Test rule to enable launching of activity
    @Rule
    public ActivityTestRule<ServicesList> mActivityRule =
            new ActivityTestRule(ServicesList.class);

    @Test
    public void ListIsDisplayed() {
        onView(withId(R.id.servicesList)).check(matches(isDisplayed()));
    }
} 
