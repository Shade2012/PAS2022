package com.example.pas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tmdb extends AppCompatActivity implements kucingAdapter.CodeAdapterListener {

RecyclerView kucingrv;
ArrayList<kucingModel> listdatakucing;
private kucingAdapter adapterlistkucing;
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
                                // Use the image URL as needed (e.g., display in an ImageView)
                                // ...
                            }
                            kucingrv = findViewById(R.id.recycled);
                            adapterlistkucing = new kucingAdapter(getApplicationContext(),listdatakucing,Tmdb.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            kucingrv.setHasFixedSize(true);
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
        getkucingonline();
        progressBar = findViewById(R.id.progress);
    }

    @Override
    public void onCodeSelected(kucingModel kucing) {
        Intent intent = new Intent(getApplicationContext(), detailpage.class);
        intent.putExtra("mykucing", kucing);
        startActivity(intent);
    }
}