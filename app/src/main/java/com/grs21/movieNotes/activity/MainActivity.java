package com.grs21.movieNotes.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.grs21.movieNotes.util.DownLoader;
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
    public static final String JSON_DATA_GENRES_URL="https://api.themoviedb.org/3/genre/movie/list?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US";
    private static final String JSON_MOVIE_GENRES_ID="id";
    public static final String JSON_MOVIE_GENRES_NAME="name";
    private static  final String JSON_MOVIE_NAME="Title";
    public static final String JSON_MOVIE_RANK="Rank";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private ArrayList<Movie> sliderViewImageArrayList=new ArrayList<>();
    private RecyclerViewRowAdapter recyclerViewRowAdapter;
    private SliderView sliderView;


    private List<String> listGenres=new ArrayList<>();

    LinearLayoutManager linearLayoutManagerPop;
    LinearLayoutManager linearLayoutManagerTopRate;
    LinearLayoutManager linearLayoutManagerUpComing;
    LinearLayoutManager linearLayoutManagerNowPlaying;
    private RecyclerView recyclerViewPop;
    private RecyclerView recyclerViewTopRate;
    private RecyclerView recyclerViewNowPlaying;
    private RecyclerView recyclerViewUpComing;
    private TextView textViewTitlePopular;
    private TextView textViewTitleTopRate;
    private TextView textViewTitleNowPlaying;
    private TextView textViewTitleUpComing;
    ProgressBar progressBar;



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


        DownLoader popular=new DownLoader(JSON_POPULAR_LIST_URL,MainActivity.this
                ,recyclerViewPop, linearLayoutManagerPop);
        popular.download();

        DownLoader upComing=new DownLoader(JSON_UP_COMING_LIST_URL,MainActivity.this
                ,recyclerViewUpComing,linearLayoutManagerUpComing);
        upComing.download();

       DownLoader topRate=new DownLoader(JSON_TOP_RATE_LIST_URl,MainActivity.this
                ,recyclerViewTopRate, linearLayoutManagerTopRate);
        topRate.download();

        DownLoader nowPlaying=new DownLoader(JSON_NOW_PLAYING_LIST_URL,MainActivity.this
                ,recyclerViewNowPlaying, linearLayoutManagerNowPlaying);
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
                String url="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                DownLoader downLoader=new DownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewPop,linearLayoutManagerPop);
                 downLoader.download();

            }
        });
        recyclerViewUpComing.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerUpComing) {
            @Override
            public void onLoadMore(int currentPage) {
                String url="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                DownLoader downLoader=new DownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewUpComing,linearLayoutManagerUpComing);
                downLoader.download();
            }
        });
        recyclerViewNowPlaying.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerNowPlaying) {
            @Override
            public void onLoadMore(int currentPage) {
                String url="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                DownLoader downLoader=new DownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewNowPlaying,linearLayoutManagerNowPlaying);
                downLoader.download();
            }
        });
        recyclerViewTopRate.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManagerTopRate) {
            @Override
            public void onLoadMore(int currentPage) {
                String url="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
                DownLoader downLoader=new DownLoader(String.format(url,currentPage),MainActivity.this
                        ,recyclerViewTopRate,linearLayoutManagerTopRate);
                downLoader.download();


            }
        });



    }


    public void initializeComponent(){
        recyclerViewPop=findViewById(R.id.recyclerViewRowPopular);
        recyclerViewTopRate=findViewById(R.id.recyclerViewRowTopRAte);
        recyclerViewUpComing=findViewById(R.id.recyclerViewRowUpComing);
        recyclerViewNowPlaying=findViewById(R.id.recyclerViewRowNowPlaying);
        textViewTitlePopular=findViewById(R.id.editTextRowTitlePopular);
        textViewTitleNowPlaying=findViewById(R.id.editTextRowTitleNowPlaying);
        textViewTitleTopRate=findViewById(R.id.editTextRowTitleTopRate);
        textViewTitleUpComing=findViewById(R.id.editTextRowTitleUpComing);
        sliderView=findViewById(R.id.imageSlider);
    }
}