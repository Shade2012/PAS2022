package com.example.pas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tmdb extends AppCompatActivity implements kucingAdapter.CodeAdapterListener  {
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String EMAIL_KEY = "email_key";

    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;

RecyclerView kucingrv;
ArrayList<kucingModel> listdatakucing;
public kucingAdapter adapterlistkucing;
    ProgressBar progressBar;

    public void getkucingonline(){
        String url = "https://api.thecatapi.com/v1/images/search?limit=10";

        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the response here
                        try {
                            // Parse the JSON response
                            for (int i = 0; i < response.length(); i++) {
                                kucingModel mykucing = new kucingModel();
                                JSONObject jsonkucing = response.getJSONObject(i);
                                mykucing.setId(jsonkucing.getString("id"));
                                mykucing.setImage(jsonkucing.getString("url"));
                                mykucing.setWidth(jsonkucing.getString("width"));
                                mykucing.setHeight(jsonkucing.getString("height"));

                                listdatakucing.add(mykucing);

                            }
                            kucingrv = findViewById(R.id.recycled);
                            adapterlistkucing = new kucingAdapter(getApplicationContext(),listdatakucing,Tmdb.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            kucingrv.setHasFixedSize(true);
                            kucingrv.setLayoutManager(mLayoutManager);
                            kucingrv.setAdapter(adapterlistkucing);
                            progressBar.setVisibility(View.GONE);
                            kucingrv.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // Handle error
                        Log.d("Gagal", "onError: " + error.toString());
                    }
                });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmdb);
        listdatakucing = new ArrayList<>();
        kucingrv = findViewById(R.id.recycled);
        getkucingonline();
        progressBar = findViewById(R.id.progress);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Handle settings action

             sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            sharedpreferences.edit().remove(EMAIL_KEY).remove(PASSWORD_KEY).apply();
            startActivity(new Intent(Tmdb.this, SplashScreen.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCodeSelected(kucingModel kucing) {
        Intent intent = new Intent(getApplicationContext(), detailpage.class);
        intent.putExtra("mykucing", kucing);
        startActivity(intent);

    }

    @Override
    public void onDeleteClickListener(kucingModel kucing) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this ID: " + kucing.getId())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Perform any action on OK button click
                        deleteItem(kucing);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Perform any action on Cancel button click
                    }
                })
                .show();
    }
    private void deleteItem(kucingModel kucing) {
        // Implement the deletion logic here
        // For example, remove the item from the list and notify the adapter
        int position = listdatakucing.indexOf(kucing);
        if (position != -1) {
            listdatakucing.remove(position);
            adapterlistkucing.notifyItemRemoved(position);
        }
    }
}