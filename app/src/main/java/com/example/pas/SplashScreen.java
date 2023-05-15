package com.example.pas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Menggunakan handler untuk menunda navigasi ke MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk membuka MainActivity setelah splashscreen selesai
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup SplashActivity agar tidak dapat diakses kembali
            }
        }, SPLASH_DELAY);
    }
}