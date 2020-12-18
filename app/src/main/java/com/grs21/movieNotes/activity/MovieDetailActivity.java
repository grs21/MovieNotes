package com.grs21.movieNotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.HttpConnector;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    private CardView cardView;
    private ImageView movieImage,cardViewBackGround;
    private TextView movieName,releaseDate, textViewRank,genresTextView,overviewTextView;
    private Movie movie;
    private final String baseURL="https://api.themoviedb.org/3/movie/%d?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US";


    private Intent intent;
    private static final String TAG = "MovieDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent=getIntent();
        movie=(Movie) intent.getSerializableExtra("movie");


        initializeComponent();
        setComponentValue();
        detailDownloader(baseURL,movie.getId());
    }

    private void detailDownloader(String url,int movieId) {

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                , String.format(url,movieId)
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject=response;

                    overviewTextView.setText(jsonObject.getString("overview"));

                    JSONArray jsonArray=response.getJSONArray("genres");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        movie.getMovieGenres().add(jsonArray.getJSONObject(i).getString("name"));
                    }
                    genresTextView.setText(movie.getMovieGenres().toString());


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
        HttpConnector.getInstance(MovieDetailActivity.this).addRequestQue(jsonObjectRequest);

    }


    private void setComponentValue() {



        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getMovieBackdropPathImageUrl()).into(cardViewBackGround);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getMoviePosterImageURL())
                .into(movieImage);
        movieName.setText(movie.getMovieName());
        releaseDate.setText(movie.getReleaseDate());
        textViewRank.setText(movie.getRank());
        ArrayList<String>genres=new ArrayList<>();
        genres.add("Action");
        genres.add("Popular");
        genresTextView.setText(genres.toString().trim());

    }


    private void initializeComponent() {
        cardViewBackGround=findViewById(R.id.cardViewImageBackGround);
        movieImage=findViewById(R.id.detailImageView);
        movieName=findViewById(R.id.textViewMovieNameDetail);
        releaseDate=findViewById(R.id.textViewMovieDetailReleaseDate);
        textViewRank =findViewById(R.id.editTextRank);
        genresTextView=findViewById(R.id.textViewGenres);
        overviewTextView=findViewById(R.id.textViewOverView);
    }


}