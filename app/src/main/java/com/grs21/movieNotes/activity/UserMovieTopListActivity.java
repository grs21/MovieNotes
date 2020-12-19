package com.grs21.movieNotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.grs21.movieNotes.R;

public class UserMovieTopListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_movie_top_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeComponent();

    }

    private void initializeComponent() {

        recyclerView=findViewById(R.id.recyclerViewMovieList);
    }
}