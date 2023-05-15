package com.example.pas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private Button signInButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.Button1);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil fungsi untuk memulai proses Sign-In
                startSignIn();
            }
        });
    }

    private void startSignIn() {
        // Intent untuk memulai Sign-In menggunakan Google Sign-In API
        Intent signInIntent = new Intent(this, SignInActivity.class);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Proses hasil dari Sign-In
            // ...
            Intent myIntent = new Intent(MainActivity.this, Tmdb.class);
            MainActivity.this.startActivity(myIntent);

            // Misalnya, Anda dapat memperbarui UI setelah berhasil Sign-In
            // signInButton.setVisibility(View.GONE);
        }

    }}