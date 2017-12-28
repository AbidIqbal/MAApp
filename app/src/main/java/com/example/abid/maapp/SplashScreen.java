package com.example.abid.maapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    public TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv=(TextView) findViewById(R.id.welcomeMessage);
        iv=(ImageView) findViewById(R.id.logo);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent loginActivity=new Intent("com.example.abid.maapp.LoginActivity");
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(1000);
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
