package com.grs21.movieNotes.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

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
import com.grs21.movieNotes.util.HttpConnector;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRow;
    private RecyclerView recyclerViewMain;
    private Movie movie;
    private static final String TAG = "MainActivity";
    public static final String JSON_DATA_URL="https://run.mocky.io/v3/c438783d-1f84-40d1-b574-77c5ad5b612d";
    public static final String JSON_DATA_GENRES_URL="https://api.themoviedb.org/3/genre/movie/list?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US";
    private static final String JSON_MOVIE_GENRES_ID="id";
    public static final String JSON_MOVIE_GENRES_NAME="name";
    private static  final String JSON_MOVIE_NAME="Title";
    public static final String JSON_MOVIE_RANK="Rank";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private RecyclerViewBoxAdapter recyclerViewBoxAdapter;
    private SliderView sliderView;
    private SliderImageAdapter sliderImageAdapter;
    private List<Genres> listGenres=new ArrayList<>();
    private Genres genres;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        donLoader(JSON_DATA_URL,JSON_DATA_GENRES_URL);
        layoutManager();

    }


    public void genresDownloader(String genresDataURL){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                , genresDataURL
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray=response.getJSONArray("genres");
                    for (int i=0;i<jsonArray.length();i++){

                       genres=new Genres();
                       genres.setId(jsonArray.getJSONObject(i).getString(JSON_MOVIE_GENRES_ID));
                       genres.setName(jsonArray.getJSONObject(i).getString(JSON_MOVIE_GENRES_NAME));
                       listGenres.add(genres);
                    }
                    Log.d(TAG, "onResponse: GENRES"+listGenres);
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

    public void layoutManager(){

        LinearLayoutManager layoutManagerMain=new LinearLayoutManager(MainActivity.this);
        layoutManagerMain.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(layoutManagerMain);

    }

    public void donLoader(String bigDAtaURL,String genresDataURL){
        genresDownloader(genresDataURL);

        bigDataDownLoader downLoader=new bigDataDownLoader();
        downLoader.execute(bigDAtaURL);

    }

    @SuppressLint("StaticFieldLeak")
    private class bigDataDownLoader extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);

                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET
                        , url
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
                                Log.d(TAG, "onResponse: "+movieArrayList);
                            }
                            recyclerViewBoxAdapter =new RecyclerViewBoxAdapter(movieArrayList);
                            RecyclerViewRowAdapter recyclerViewRowAdapter=new RecyclerViewRowAdapter(MainActivity.this,listGenres,recyclerViewBoxAdapter,movieArrayList);
                            recyclerViewMain.setAdapter(recyclerViewRowAdapter);

/*
                            sliderImageAdapter=new SliderImageAdapter(movieArrayList);
                            sliderView.setSliderAdapter(sliderImageAdapter);

*/
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
        sliderView=findViewById(R.id.imageSlider);
        recyclerViewRow =findViewById(R.id.recyclerViewRow);
        recyclerViewMain=findViewById(R.id.recyclerViewMain);

    }
}