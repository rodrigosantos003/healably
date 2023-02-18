package com.example.healably;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.healably.accounts.model.User;
import com.example.healably.accounts.views.LoginActivity;
import com.example.healably.data.HealablySQLiteHelper;
import com.example.healably.user_profile.views.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HealablySQLiteHelper healablySQLiteHelper = new HealablySQLiteHelper(SplashActivity.this);
                try{
                    User loggedUser = healablySQLiteHelper.getLoggedUser();
                    Intent it;
                    if(loggedUser != null){
                        it = new Intent(SplashActivity.this, MainActivity.class);
                    } else{
                        it = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(it);
                } catch (Exception e){
                    Log.d("EXCEPTION", e.getMessage());
                }

                finish();
            }
        },3000);
    }
}