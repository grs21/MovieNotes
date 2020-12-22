package com.grs21.movieNotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerViewUserTopMovieListAdapter;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.HttpConnector;
import com.smarteist.autoimageslider.IndicatorView.animation.data.Value;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMovieTopListActivity extends AppCompatActivity {

    private static final String TAG = "UserMovieTopListActivit";
    private RecyclerView recyclerView;
    private static final String BASE_URL="https://api.themoviedb.org/3/movie/%s?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_movie_top_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeComponent();
        Intent intent=getIntent();
        ArrayList<String>userTopMovieList= intent.getStringArrayListExtra("id");

        MovieDownLoader movieDownLoader=new MovieDownLoader();
        movieDownLoader.execute(userTopMovieList);


    }
    public class MovieDownLoader extends AsyncTask<ArrayList<String>, Value,String>{

        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {
            ArrayList<Movie> movieArrayList=new ArrayList<>();
            String movieId = null;

            for (int i = 0; i < arrayLists.length; i++) {

                movieId=arrayLists[0].get(i);
                Log.d(TAG, "doInBackground: "+movieId);
            }
            @SuppressLint("DefaultLocale")
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                    Request.Method.GET
                    , String.format(BASE_URL, movieId)
                    , null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                Movie movie=new Movie();
                    try {

                      movie.setId(response.getInt("id"));
                      movie.setMovieName(response.getString("title"));
                      movie.setReleaseDate(response.getString("release_date"));
                      movie.setRank(response.getString("vote_average"));
                      movie.setMoviePosterImageURL(response.getString("poster_path"));
                      movieArrayList.add(movie);
                        Log.d(TAG, "onResponse:TOP_MOVIE_ARRAY_LIST:"+movieArrayList.toString());
                        RecyclerViewUserTopMovieListAdapter topListAdapter=new RecyclerViewUserTopMovieListAdapter(
                                UserMovieTopListActivity.this,movieArrayList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(UserMovieTopListActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(topListAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                }
                , new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {

                 }
                 }
            );
            HttpConnector.getInstance(UserMovieTopListActivity.this).addRequestQue(jsonObjectRequest);

            return null;
        }
    }


    private void initializeComponent() {

        recyclerView=findViewById(R.id.recyclerViewMovieList);
    }
}