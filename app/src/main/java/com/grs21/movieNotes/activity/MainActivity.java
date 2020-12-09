package com.grs21.movieNotes.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerViewRowAdapter;
import com.grs21.movieNotes.adapter.SliderImageAdapter;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.MovieDownLoader;
import com.grs21.movieNotes.util.RecyclerOnScrollListener;
import com.grs21.movieNotes.util.HttpConnector;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Movie movie;
    private static final String TAG = "MainActivity";
    public static final String JSON_POPULAR_LIST_URL="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    public static final String JSON_TOP_RATE_LIST_URl="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    public static final String JSON_UP_COMING_LIST_URL="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    public static final String JSON_NOW_PLAYING_LIST_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private ArrayList<Movie> sliderViewImageArrayList=new ArrayList<>();
    private RecyclerViewRowAdapter recyclerViewRowAdapter;
    private SliderView sliderView;


    private List<String> listGenres=new ArrayList<>();

    private LinearLayoutManager linearLayoutManagerPop;
    private LinearLayoutManager linearLayoutManagerTopRate;
    private LinearLayoutManager linearLayoutManagerUpComing;
    private LinearLayoutManager linearLayoutManagerNowPlaying;

    private RecyclerView recyclerViewPop;
    private RecyclerView recyclerViewTopRate;
    private RecyclerView recyclerViewNowPlaying;
    private RecyclerView recyclerViewUpComing;

    private TextView textViewTitlePopular;
    private TextView textViewTitleTopRate;
    private TextView textViewTitleNowPlaying;
    private TextView textViewTitleUpComing;

    public ProgressBar progressBarPopular;
    private ProgressBar progressBarTopRate;
    ProgressBar progressBarNowPlaying;
    ProgressBar progressBarUpComing;
    private ArrayList<Movie> popularTotalMovieArrayList =new ArrayList<>();
    private ArrayList<Movie> topRateTotalMovieArrayList =new ArrayList<>();
    private ArrayList<Movie> nowPlayingTotalMovieArrayList =new ArrayList<>();
    private ArrayList<Movie> upComingTotalMovieArrayList =new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();

        linearLayoutManagerPop =new LinearLayoutManager(this);
        linearLayoutManagerPop.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManagerTopRate =new LinearLayoutManager(this);
        linearLayoutManagerTopRate.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManagerNowPlaying =new LinearLayoutManager(this);
        linearLayoutManagerNowPlaying.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManagerUpComing=new LinearLayoutManager(this);
        linearLayoutManagerUpComing.setOrientation(LinearLayoutManager.HORIZONTAL);

        textViewTitlePopular.setText("Popular");
        textViewTitleUpComing.setText("Up Coming");
        textViewTitleTopRate.setText("Top Rate");
        textViewTitleNowPlaying.setText("Now Playing");


        MovieDownLoader popular=new MovieDownLoader(JSON_POPULAR_LIST_URL,MainActivity.this
                ,recyclerViewPop, linearLayoutManagerPop, popularTotalMovieArrayList);
        popular.download();
        Log.d(TAG, "onCreate: hi"+popular.returnList());

        MovieDownLoader upComing=new MovieDownLoader(JSON_UP_COMING_LIST_URL,MainActivity.this
                ,recyclerViewUpComing,linearLayoutManagerUpComing,upComingTotalMovieArrayList);
        upComing.download();

        MovieDownLoader topRate=new MovieDownLoader(JSON_TOP_RATE_LIST_URl,MainActivity.this
                ,recyclerViewTopRate, linearLayoutManagerTopRate,topRateTotalMovieArrayList);
        topRate.download();

        MovieDownLoader nowPlaying=new MovieDownLoader(JSON_NOW_PLAYING_LIST_URL,MainActivity.this
                ,recyclerViewNowPlaying, linearLayoutManagerNowPlaying,nowPlayingTotalMovieArrayList);
        nowPlaying.download();

        sliderImageDownload(JSON_UP_COMING_LIST_URL);
        recyclerViewScrollListener();

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


    public void recyclerViewScrollListener(){

        recyclerViewPop.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerPop) {

            @Override
            public void onLoadMore(int currentPage) {
                progressBarPopular.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Log.d(TAG, "run: CURRENTPAGE"+currentPage);
                        String url="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                        MovieDownLoader movieDownLoader =new MovieDownLoader(String.format(url,currentPage),MainActivity.this
                                ,recyclerViewPop,linearLayoutManagerPop, popularTotalMovieArrayList);
                        movieDownLoader.download();
                        progressBarPopular.setVisibility(View.GONE);

                    }
                },1000);
            }
        });
        recyclerViewUpComing.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerUpComing) {
            @Override
            public void onLoadMore(int currentPage) {

                String url="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                MovieDownLoader movieDownLoader =new MovieDownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewUpComing,linearLayoutManagerUpComing,upComingTotalMovieArrayList);
                movieDownLoader.download();
            }
        });
        recyclerViewNowPlaying.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerNowPlaying) {
            @Override
            public void onLoadMore(int currentPage) {
                String url="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                MovieDownLoader movieDownLoader =new MovieDownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewNowPlaying,linearLayoutManagerNowPlaying,nowPlayingTotalMovieArrayList);
                movieDownLoader.download();
            }
        });
        recyclerViewTopRate.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerTopRate) {
            @Override
            public void onLoadMore(int currentPage) {
                String url="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                MovieDownLoader movieDownLoader =new MovieDownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewTopRate,linearLayoutManagerTopRate,topRateTotalMovieArrayList);
                movieDownLoader.download();


            }
        });



    }


    public void initializeComponent(){
        recyclerViewPop=findViewById(R.id.recyclerViewRowPopular);
        recyclerViewTopRate=findViewById(R.id.recyclerViewRowTopRate);
        recyclerViewUpComing=findViewById(R.id.recyclerViewRowUpComing);
        recyclerViewNowPlaying=findViewById(R.id.recyclerViewRowNowPlaying);

        textViewTitlePopular=findViewById(R.id.editTextRowTitlePopular);
        textViewTitleNowPlaying=findViewById(R.id.editTextRowTitleNowPlaying);
        textViewTitleTopRate=findViewById(R.id.editTextRowTitleTopRate);
        textViewTitleUpComing=findViewById(R.id.editTextRowTitleUpComing);

        progressBarPopular=findViewById(R.id.progressBar_Popular);
        progressBarNowPlaying=findViewById(R.id.progressBar_NowPlaying);
        progressBarTopRate=findViewById(R.id.progressBar_TopRate);
        progressBarUpComing=findViewById(R.id.progressBar_UpComing);

        sliderView=findViewById(R.id.imageSlider);
    }
}