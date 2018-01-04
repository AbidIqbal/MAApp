package com.example.abid.maapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A class used to create an animation screen at the application start
 */
public class SplashScreen extends AppCompatActivity {

    public TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textView=(TextView) findViewById(R.id.welcomeMessage);
        imageView=(ImageView) findViewById(R.id.logo);

        /***************************************************************************************
         *    Title: How to create Splash screen with transition animation Android Studio
         *    Author: Simple Easy & Fast Programming
         *    Date Accessed: 28 Dec. 2017
         *    Code version: N/A
         *    Availability: https://www.youtube.com/watch?v=9JZ9sI7Rv0I
         *
         ***************************************************************************************/

        //creating and starting animation.
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.mytransition);
        textView.startAnimation(myanim);
        imageView.startAnimation(myanim);
        //Intent to start the first interactive Activity for the user.
        final Intent loginActivity=new Intent("com.example.abid.maapp.LoginActivity");
        //thread to goto LoginActivity
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {

                }
                finally {
                    startActivity(loginActivity);
                    finish();

                }

            }
        };
        timer.start();
    }
}
