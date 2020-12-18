package com.grs21.movieNotes.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerViewParentAdapter;
import com.grs21.movieNotes.adapter.SliderImageAdapter;
import com.grs21.movieNotes.model.Category;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.MovieInitializeDownLoader;
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
    private RecyclerViewParentAdapter recyclerViewParentAdapter;
    private SliderView sliderView;
    private List<String> listMovieTitle=new ArrayList<>();
    private LinearLayoutManager linearLayoutManagerPop;
    private LinearLayoutManager linearLayoutManagerTopRate;
    private LinearLayoutManager linearLayoutManagerUpComing;
    private LinearLayoutManager linearLayoutManagerNowPlaying;
    private RecyclerView recyclerViewParent;
    private RecyclerView recyclerViewChild;
    private ArrayList<Movie> popular =new ArrayList<>();
    private ArrayList<Movie> topRate =new ArrayList<>();
    private ArrayList<Movie> nowPlaying =new ArrayList<>();
    private ArrayList<Movie> upComing =new ArrayList<>();
    private ArrayList<Category> totalCategory=new ArrayList<>();
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();

        linearLayoutManagerPop =new LinearLayoutManager(this);
        linearLayoutManagerPop.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerTopRate =new LinearLayoutManager(this);
        linearLayoutManagerTopRate.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerNowPlaying =new LinearLayoutManager(this);
        linearLayoutManagerNowPlaying.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerUpComing=new LinearLayoutManager(this);
        linearLayoutManagerUpComing.setOrientation(LinearLayoutManager.VERTICAL);
        listMovieTitle.add("Popular");
        listMovieTitle.add("Up Coming");
        listMovieTitle.add("Top Rate");
        listMovieTitle.add("Now Playing");



        MovieInitializeDownLoader movieInitializeDownLoader =new MovieInitializeDownLoader(MainActivity.this,recyclerViewParent
                ,linearLayoutManagerPop,totalCategory,popular,topRate,upComing,nowPlaying);

        movieInitializeDownLoader.download(JSON_POPULAR_LIST_URL,"Popular");
        movieInitializeDownLoader.download(JSON_TOP_RATE_LIST_URl,"Top Rate");
        movieInitializeDownLoader.download(JSON_NOW_PLAYING_LIST_URL,"Up Coming");
        movieInitializeDownLoader.download(JSON_UP_COMING_LIST_URL,"Now Playing");


        sliderImageDownload(JSON_UP_COMING_LIST_URL);

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
                       movie.setId(jsonArray.getJSONObject(i).getInt("id"));
                       movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                       movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
                       movie.setRank(jsonArray.getJSONObject(i).getString("vote_average"));
                       movie.setReleaseDate(jsonArray.getJSONObject(i).getString("release_date"));
                       movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString("backdrop_path"));

                       sliderViewImageArrayList.add(movie);
                    }

                    SliderImageAdapter sliderImageAdapter=new SliderImageAdapter(sliderViewImageArrayList,MainActivity.this);
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
        sliderView=findViewById(R.id.imageSlider);
        recyclerViewParent=findViewById(R.id.recyclerViewParent);
        recyclerViewChild =findViewById(R.id.recyclerChild);
        imageView=findViewById(R.id.item_image);
    }
}