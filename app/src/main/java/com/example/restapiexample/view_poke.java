package com.example.restapiexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class view_poke extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poke);
        TextView big_title;
        ImageView big_poke_img;
        Bundle extras;
        String img_url;

        extras = getIntent().getExtras();

        big_title = findViewById(R.id.big_poke_title);
        big_poke_img = findViewById(R.id.big_poke);

        big_title.setText(extras.getString("Pokemon"));
        img_url = extras.getString("Image_Url");

        new MainActivity2.DownloadImageFromInternet((big_poke_img)).execute(img_url);



    }
}