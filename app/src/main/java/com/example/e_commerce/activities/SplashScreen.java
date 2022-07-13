package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.e_commerce.R;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN );

        final Intent intent = new Intent(getBaseContext(), OnBoardingActivity.class);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          startActivity(intent);
                                          finish();
                                      }
                                  }, 3000
        );
//
    }
}