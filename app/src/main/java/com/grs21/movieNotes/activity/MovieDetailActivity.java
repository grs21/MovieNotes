package com.grs21.movieNotes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.adapter.RecyclerViewDetailActorAdapter;
import com.grs21.movieNotes.model.Actor;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.HttpConnector;
import com.grs21.movieNotes.util.TxtFileReader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MovieDetailActivity extends AppCompatActivity {

    private ArrayList<Actor> actorArrayList=new ArrayList<>();
    private ImageView movieImage,cardViewBackGround;
    private TextView textViewMovieName, textViewReleaseDate, textViewRank, textViewGenres, textViewOverview;
    private Movie movie;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private static final String MOVIE_ID_FILE_NAME="movie_id.txt";
    private final String baseURL="https://api.themoviedb.org/3/movie/%d?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&append_to_response=credits";


    private Intent intent;
    private static final String TAG = "MovieDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeComponent();
        intent=getIntent();
        movie=(Movie) intent.getSerializableExtra("movie");



        setComponentValue();
        detailDownloader(baseURL,movie.getId());
        movieAddButtonListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TxtFileReader txtFileReader=new TxtFileReader();
        Intent intent=new Intent(MovieDetailActivity.this,UserMovieTopListActivity.class);
        intent.putStringArrayListExtra("id",txtFileReader.read());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity( intent);

        return  super.onOptionsItemSelected(item);
    }

    private void movieAddButtonListener() {

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TxtFileReader txtFileReader=new TxtFileReader();
                boolean status=true;
                for (String idController:txtFileReader.read()) {
                    if (String.valueOf(movie.getId()).equals(idController)){
                        status=false;
                    }
                }
                if (status) {
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = MovieDetailActivity.this.openFileOutput(MOVIE_ID_FILE_NAME, Context.MODE_APPEND);
                        byte[] id = movie.getId().toString().getBytes();
                        fileOutputStream.write(" ".getBytes());
                        fileOutputStream.write(id);
                        Log.d(TAG, "onClick: " + movie.getId().toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else Toast.makeText(MovieDetailActivity.this, "THIS FILM HAS ALREADY BEEN RECORDED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void detailDownloader(String url,int movieId) {

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                , String.format(url,movieId)
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONObject jsonObject= response.getJSONObject("credits");
                   JSONArray jsonArray1=jsonObject.getJSONArray("cast");
                    for (int i = 0; i <10 ; i++) {
                        Actor actor=new Actor(jsonArray1.getJSONObject(i).getString("name")
                        ,jsonArray1.getJSONObject(i).getString("profile_path"));
                        actorArrayList.add(actor);
                    }
                    RecyclerViewDetailActorAdapter recyclerViewDetailActorAdapter=new RecyclerViewDetailActorAdapter(actorArrayList);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailActivity.this
                            ,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setAdapter(recyclerViewDetailActorAdapter);
                    recyclerView.setLayoutManager(layoutManager);


                    textViewOverview.setText(response.getString("overview"));

                    JSONArray jsonArray=response.getJSONArray("genres");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        movie.getMovieGenres().add(jsonArray.getJSONObject(i).getString("name"));
                    }
                    textViewGenres.setText(movie.getMovieGenres().toString());


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
        textViewMovieName.setText(movie.getMovieName());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRank.setText(movie.getRank());
        ArrayList<String>genres=new ArrayList<>();
        genres.add("Action");
        genres.add("Popular");
        textViewGenres.setText(genres.toString().trim());

    }


    private void initializeComponent() {
        cardViewBackGround=findViewById(R.id.cardViewImageBackGround);

        movieImage=findViewById(R.id.topListImage);

        textViewMovieName =findViewById(R.id.topListMovieName);
        textViewReleaseDate =findViewById(R.id.topListReleaseDate);
        textViewRank =findViewById(R.id.topListEditTextRank);
        textViewGenres =findViewById(R.id.textViewGenres);
        textViewOverview =findViewById(R.id.textViewOverView);

        recyclerView=findViewById(R.id.recyclerViewActor);

        imageButton=findViewById(R.id. detailAddMovieButton);
    }


}