package com.example.pas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detailpage extends AppCompatActivity {
    Intent i;
kucingModel kucingmodel;
TextView id,height,widht;
ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);
        id = findViewById(R.id.iddetail);
        height = findViewById(R.id.height);
        widht = findViewById(R.id.width);
        img = findViewById(R.id.imageview2);
        i =getIntent();
        kucingmodel = (kucingModel)i.getParcelableExtra("mykucing");

        height.setText(kucingmodel.getHeight());
        widht.setText(kucingmodel.getWidth());
        id.setText(kucingmodel.getId());
        Glide.with(this).load("https://image.tmdb.org/t/p/original" + kucingmodel.getImage2()).into(img);
    }
}