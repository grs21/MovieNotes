package com.grs21.movieNotes.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerViewBoxAdapter;
import com.grs21.movieNotes.adapter.RecyclerViewRowAdapter;
import com.grs21.movieNotes.adapter.SliderImageAdapter;
import com.grs21.movieNotes.model.Genres;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.EndlessRecyclerOnScrollListener;
import com.grs21.movieNotes.util.HttpConnector;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.PipedOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMain;
    private Movie movie;
    private static final String TAG = "MainActivity";
    public static final String JSON_POPULAR_LIST_URL="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=1";
    public static final String JSON_TOP_RATE_LIST_URl="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=1";
    public static final String JSON_UP_COMING_LIST_URL="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=1";
    public static final String JSON_NOW_PLAYING_LIST_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=1";
    public static final String JSON_DATA_GENRES_URL="https://api.themoviedb.org/3/genre/movie/list?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US";
    private static final String JSON_MOVIE_GENRES_ID="id";
    public static final String JSON_MOVIE_GENRES_NAME="name";
    private static  final String JSON_MOVIE_NAME="Title";
    public static final String JSON_MOVIE_RANK="Rank";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private ArrayList<Movie> sliderViewImageArrayList=new ArrayList<>();
    private RecyclerViewRowAdapter recyclerViewRowAdapter;
    private SliderView sliderView;
    private SliderImageAdapter sliderImageAdapter;

    private List<String> listGenres=new ArrayList<>();

    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutManager=new LinearLayoutManager(this);
        initializeComponent();
        listGenres.add("Popular");
        listGenres.add("Top Rated");
        listGenres.add("Now Playing");
        listGenres.add("Up Coming");


        MovieDownloader popular=new MovieDownloader();
        popular.execute(JSON_POPULAR_LIST_URL);
        MovieDownloader upComing=new MovieDownloader();
        upComing.execute(JSON_UP_COMING_LIST_URL);
        MovieDownloader topRate=new MovieDownloader();
        topRate.execute(JSON_TOP_RATE_LIST_URl);
        MovieDownloader nowPlaying=new MovieDownloader();
        nowPlaying.execute(JSON_NOW_PLAYING_LIST_URL);

       sliderImageDownload(JSON_UP_COMING_LIST_URL);
    }

    public class MovieDownloader extends AsyncTask<String,Void,ArrayList<Movie>>{

         @Override
         protected ArrayList<Movie> doInBackground(String... strings) {
             JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.GET
                    , strings[0]
                    , null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray=response.getJSONArray("results");
                        for (int i=0;i<jsonArray.length();i++){
                            movie=new Movie();
                            movie.setRank(jsonArray.getJSONObject(i).getString("vote_average"));
                            movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                            JSONArray jsonArray1=jsonArray.getJSONObject(i).getJSONArray("genre_ids");
                            for (int b=0;b<jsonArray1.length();b++){

                                movie.genresId.add((Integer) jsonArray1.get(b));
                            }
                            movie.setMovieImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
                            movieArrayList.add(movie);
                        }
                        if (movieArrayList.size()>60){
                        RecyclerViewRowAdapter recyclerViewRowAdapter=new RecyclerViewRowAdapter(MainActivity.this,listGenres,movieArrayList);
                        recyclerViewMain.setAdapter(recyclerViewRowAdapter);
                        recyclerViewMain.setLayoutManager(layoutManager);
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
            HttpConnector.getInstance(MainActivity.this).addRequestQue(jsonArrayRequest);
            return movieArrayList;

        }
    }

    public void sliderImageDownload(String imageDataURL){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                , imageDataURL
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray=response.getJSONArray("results");
                    for (int i=0;i<13;i++){
                       movie=new Movie();
                       movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                       movie.setMovieImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
                       sliderViewImageArrayList.add(movie);
                    }

                    SliderImageAdapter sliderImageAdapter=new SliderImageAdapter(sliderViewImageArrayList);
                    sliderView.setSliderAdapter(sliderImageAdapter);
                    sliderView.startAutoCycle();
                    sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        HttpConnector.getInstance(MainActivity.this).addRequestQue(jsonObjectRequest);
    }
    public void initializeComponent(){
        recyclerViewMain=findViewById(R.id.recyclerViewMain);
        sliderView=findViewById(R.id.imageSlider);
    }
}