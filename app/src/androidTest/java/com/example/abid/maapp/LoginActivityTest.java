package com.example.abid.maapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

        //Test rule to enable launching of activity
        @Rule
        public ActivityTestRule<LoginActivity> mActivityRule =
                new ActivityTestRule(LoginActivity.class);

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
                onView(withId(R.id.signin)).check(matches(isDisplayed()));
        }
        @Test
        public void SignUpButtonIsDisplayed() {
                onView(withId(R.id.signup)).check(matches(isDisplayed()));
        }
        @Test
        public void AppLogoIsDisplayed() {
                onView(withId(R.id.appLogo)).check(matches(isDisplayed()));
        }



}


