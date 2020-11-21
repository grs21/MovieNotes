package com.grs21.movieNotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerAdapter;
import com.grs21.movieNotes.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();

        ArrayList<String> movieName=new ArrayList<>();
        movieName.add("spiderMan");
        movieName.add("SuperMan");
        movieName.add("HarryPother");
        movieName.add("The LOrd Of The Rings");
        movieName.add("Big Fish");

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(movieName);
        recyclerView.setAdapter(recyclerAdapter);
        
    }

    public void initializeComponent(){

        recyclerView=findViewById(R.id.recyclerView);
    }
}