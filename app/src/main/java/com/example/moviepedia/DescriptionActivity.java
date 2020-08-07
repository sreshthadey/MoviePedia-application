package com.example.moviepedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        String mov_name = getIntent().getExtras().getString("mov_name");
        String mov_desc = getIntent().getExtras().getString("mov_desc");

        TextView tv_name = findViewById(R.id.movie_name);
        TextView tv_desc = findViewById(R.id.desc);

        tv_name.setText(mov_name);
        tv_desc.setText(mov_desc);
    }
}