package com.grs21.movieNotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.grs21.movieNotes.R;

public class MovieDetailActivity extends AppCompatActivity {

    private Intent intent;
    private static final String TAG = "MovieDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        intent=getIntent();
        int id=intent.getIntExtra("id",0);
        Log.d(TAG, "onCreate: "+id);



    }





}