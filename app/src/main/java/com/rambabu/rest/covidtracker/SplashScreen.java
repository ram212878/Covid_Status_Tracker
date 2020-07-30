package com.rambabu.rest.covidtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static final long SPLASH_SCREEN_TIMEOUT = 4000;
    // creating variables for the animation
    Animation topAnimation,bottomAnimation;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation =AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        ImageView logo = findViewById(R.id.splashScreenLogo);
        ImageView appName = findViewById(R.id.splashScreenAppName);
        ImageView subHeading = findViewById(R.id.splashScreenSubHeading);

        logo.setAnimation(topAnimation);
        appName.setAnimation(bottomAnimation);
        subHeading.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);
    }
}