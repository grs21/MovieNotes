package com.grs21.movieNotes.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerViewAdapter;
import com.grs21.movieNotes.adapter.SliderAdapter;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.HttpConnector;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Movie movie;
    private static final String TAG = "MainActivity";
    public static final String JSON_DATA_URL="https://run.mocky.io/v3/c438783d-1f84-40d1-b574-77c5ad5b612d";
    private static  final String JSON_MOVIE_NAME="Title";
    public static final String JSON_MOVIE_RANK="Rank";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private RecyclerViewAdapter recyclerAdapter;
    private ViewPager2 viewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        donLoader(JSON_DATA_URL);
        layoutManager();


        List<Integer> imageResource=new ArrayList<>();
        imageResource.add(R.drawable.burger_icon);
        imageResource.add(R.drawable.cake_icon);
        imageResource.add(R.drawable.cheesecake_icon);
        imageResource.add(R.drawable.drink_icon);
        imageResource.add(R.drawable.food_icon);

        viewPager2.setAdapter(new SliderAdapter(imageResource,viewPager2));









    }

    public void layoutManager(){

        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void donLoader(String url){

        DataDownLoader downLoader=new DataDownLoader();
        downLoader.execute(url);

    }

    @SuppressLint("StaticFieldLeak")
    private class DataDownLoader extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET
                        , s
                        , null
                        , new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0;i<=response.length()-1;i++){
                                movie=new Movie();
                                movie.setRank(response.getJSONObject(i).getString(JSON_MOVIE_RANK));
                                movie.setMovieName(response.getJSONObject(i).getString(JSON_MOVIE_NAME));
                                movieArrayList.add(movie);
                                recyclerAdapter=new RecyclerViewAdapter(movieArrayList);
                                recyclerView.setAdapter(recyclerAdapter);

                                Log.d(TAG, "onResponse: "+movieArrayList);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: "+error);
                    }
                });

               HttpConnector.getInstance(getApplicationContext()).addRequestQue(jsonArrayRequest);


        }
    }

    public void initializeComponent(){
        recyclerView=findViewById(R.id.recyclerView);
        viewPager2=findViewById(R.id.viewPager2);
    }
}