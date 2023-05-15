package com.example.pas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        // Konfigurasi opsi Sign-In dengan Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Membuat GoogleApiClient dengan opsi yang dikonfigurasi
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Memulai proses Sign-In
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
// Penanganan kesalahan saat koneksi gagal
        Log.e("SignInActivity", "Connection failed: " + connectionResult.getErrorMessage());
        Toast.makeText(this, "Sign-In failed. Please try again.", Toast.LENGTH_SHORT).show();
        finish(); // Menutup SignInActivity
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Sign-In berhasil, mendapatkan informasi akun pengguna
            GoogleSignInAccount account = result.getSignInAccount();

            // Dapat melakukan aksi seperti menyimpan data pengguna, mengubah UI, dll.

            // Menutup SignInActivity setelah Sign-In berhasil

            finish();
        } else {
            // Sign-In gagal
            Log.e("SignInActivity", "Sign-In Succes.");
            Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
            finish(); // Menutup SignInActivity
        }
    }

}