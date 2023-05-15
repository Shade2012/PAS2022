package com.example.pas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {

    private EditText txtusername,txtpassword,txtemail,txtfullname;
    private ProgressBar progress;
    private Button buttonRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtusername = findViewById(R.id.username2);
        txtpassword = findViewById(R.id.password2);
        txtemail = findViewById(R.id.email2);
        txtfullname = findViewById(R.id.fullname);
        progress = findViewById(R.id.progress2);
        buttonRegister = findViewById(R.id.Button3);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                String email = txtemail.getText().toString();
                String fullname = txtfullname.getText().toString();
                progress.setVisibility(View.VISIBLE);
                buttonRegister.setEnabled(false);
                AndroidNetworking.post("https://mediadwi.com/api/latihan/register-user")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .addBodyParameter("full_name", fullname)
                        .addBodyParameter("email", email)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle the response as needed
                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    if (status){
                                        Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(register.this, Tmdb.class));
                                        finish();
                                    }else{
                                        Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                progress.setVisibility(View.GONE);
                                buttonRegister.setEnabled(true);
                            }

                            @Override
                            public void onError(ANError error) {
                                // Handle error
                            }
                        });
            }
        });
    }
}