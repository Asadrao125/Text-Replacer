package com.asadrao.textreplacer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.asadrao.textreplacer.R;
import com.asadrao.textreplacer.utils.SharedPref;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPref.init(this);
        SharedPref.write("isAdInProcess", "false");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        }, 1000);
    }
}