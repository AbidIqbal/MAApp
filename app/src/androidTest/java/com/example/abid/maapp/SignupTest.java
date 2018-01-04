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
public class SignupTest {


    //Test rule to enable launching of activity
    @Rule
    public ActivityTestRule<Signup> mActivityRule =
            new ActivityTestRule(Signup.class);

    @Test
    public void EmailFieldIsDisplayed() {
        onView(withId(R.id.email)).check(matches(isDisplayed()));
    }
    @Test
    public void PasswordFieldIsDisplayed() {
        onView(withId(R.id.password)).check(matches(isDisplayed()));
    }
    @Test
    public void SignInButtonIsDisplayed() {
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }
    @Test
    public void AppLogoIsDisplayed() {
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
    }
} 
