package com.example.pas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String EMAIL_KEY = "email_key";

    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String email, password;

    private static final int RC_SIGN_IN = 9001;
    private ProgressBar progressBar;
    private Button signInButton,Buttonlogin;
    private EditText txtusername,txtpassword;
    private TextView register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Buttonlogin = findViewById(R.id.Button2);
        txtusername = findViewById(R.id.username);
        txtpassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress);
        signInButton = findViewById(R.id.Button1);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, register.class));
                finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil fungsi untuk memulai proses Sign-In
                startSignIn();
            }
        });

        Buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username2 = txtusername.getText().toString();
                String password2 = txtpassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                Buttonlogin.setEnabled(false);
                AndroidNetworking.post("https://mediadwi.com/api/latihan/login")
                        .addBodyParameter("username", username2)
                        .addBodyParameter("password", password2)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    //ini saya bikin if nya bertulisan status karna default nya dibaca jika status = true maka jalankan perintah
                                    if (status){
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, username2.toString());
                                        editor.putString(PASSWORD_KEY, "");
                                        editor.apply();

                                        startActivity(new Intent(MainActivity.this, Tmdb.class));
                                        finish();
                                    }else{
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                progressBar.setVisibility(View.GONE);
                                Buttonlogin.setEnabled(true);
                            }


                            @Override
                            public void onError(ANError error) {
                                // Handle error
                            }
                        });
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