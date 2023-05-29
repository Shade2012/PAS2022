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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;



    public static final String SHARED_PREFS = "shared_prefs";

    public static final String EMAIL_KEY = "email_key";

    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String email, password;


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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);



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
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });


        Buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username2 = txtusername.getText().toString();
                String password2 = txtpassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                Buttonlogin.setVisibility(View.GONE);
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
                                    if (status == true) {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, username2.toString());
                                        editor.putString(PASSWORD_KEY, "");
                                        // to save our data with key and value.
                                        editor.apply();

                                        startActivity(new Intent(MainActivity.this, SplashScreen.class));
                                        finish();
                                    }else{
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                progressBar.setVisibility(View.GONE);
                                Buttonlogin.setEnabled(true);
                                Buttonlogin.setVisibility(View.VISIBLE);
                            }


                            @Override
                            public void onError(ANError error) {
                                // Handle error
                            }
                        });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(EMAIL_KEY,account.getId());
                editor.putString(PASSWORD_KEY, "");
                // to save our data with key and value.
                editor.apply();

                startActivity(new Intent(MainActivity.this, SplashScreen.class));
                finish();
                // Proses sign-in berhasil, Anda dapat menyimpan data akun menggunakan Shared Preferences di sini
            } catch (ApiException e) {
                // Sign-in gagal, Anda dapat menangani kesalahan di sini
            }
        }
    }

}