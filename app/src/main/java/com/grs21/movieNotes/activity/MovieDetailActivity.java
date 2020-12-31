package com.grs21.movieNotes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
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
import java.io.IOException;
import java.util.ArrayList;


public class MovieDetailActivity extends AppCompatActivity {


    private static final String ON_CLICKED_ON_THE_MOVIE_INTENT_KEY ="movie";
    private ArrayList<Actor> actorArrayList=new ArrayList<>();
    private ImageView movieImage,cardViewBackGround;
    private TextView textViewMovieName, textViewReleaseDate, textViewRank, textViewGenres, textViewOverview;
    private Movie movie;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private static final String MOVIE_ID_FILE_NAME="movie_id.txt";
    private final String baseURL="https://api.themoviedb.org/3/movie/%d?api_key=e502c799007bd295e5f591cb3ae8fb46&language=%s&append_to_response=credits";
    private Intent intent;
    private static final String TAG = "MovieDetailActivity";
    private static final String ON_CLICKED_HOME_BUTTON_INTENT_KEY ="HOME_BUTTON";
    private static final String JSON_OBJECT_KEYWORD_CREDITS ="credits";
    private static final String JSON_OBJECT_KEYWORD_CAST ="cast";
    private static final String JSON_OBJECT_KEYWORD_GENRES="genres";
    private static final String JSON_OBJECT_KEYWORD_NAME="name";
    private static final String JSON_OBJECT_KEYWORD_MOVIE_PROFILE_PATH="profile_path";
    private static final String JSON_OBJECT_KEYWORD_OVERVIEW="overview";
    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initializeComponent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        movie=(Movie) intent.getSerializableExtra(ON_CLICKED_ON_THE_MOVIE_INTENT_KEY);
        setComponentValue();
        actorDownloader(baseURL,movie.getId());
        movieAddButtonListener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_bar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TxtFileReader txtFileReader=new TxtFileReader();
        Intent intent=new Intent(MovieDetailActivity.this,UserMovieTopListActivity.class);
        intent.putStringArrayListExtra(ON_CLICKED_HOME_BUTTON_INTENT_KEY,txtFileReader.read());
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
                else {
                    Toast toast = Toast.makeText(MovieDetailActivity.this, R.string.MOVIE_ALREADY_RECORDED, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
    }

    private void actorDownloader(String url, int movieId) {

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                , String.format(url,movieId,getString(R.string.language))
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= response.getJSONObject(JSON_OBJECT_KEYWORD_CREDITS);
                    JSONArray jsonArray1=jsonObject.getJSONArray(JSON_OBJECT_KEYWORD_CAST);
                    Log.d(TAG, "onResponse: "+movieId);
                    Log.d(TAG, "onResponse: "+jsonArray1.toString());
                    for (int i = 0; i <jsonArray1.length() ; i++) {
                        Actor actor=new Actor(jsonArray1.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_NAME)
                                ,jsonArray1.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_MOVIE_PROFILE_PATH));
                        actorArrayList.add(actor);
                    }
                    RecyclerViewDetailActorAdapter recyclerViewDetailActorAdapter=new RecyclerViewDetailActorAdapter(actorArrayList);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(MovieDetailActivity.this
                            ,LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setAdapter(recyclerViewDetailActorAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    textViewOverview.setText(response.getString(JSON_OBJECT_KEYWORD_OVERVIEW));
                    JSONArray jsonArray=response.getJSONArray(JSON_OBJECT_KEYWORD_GENRES);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        movie.getMovieGenres().add(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_NAME));
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
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getMovieBackdropPathImageUrl())
                .into(cardViewBackGround);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getMoviePosterImageURL())
                .into(movieImage);
        textViewMovieName.setText(movie.getMovieName());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRank.setText(movie.getRank());
    }
    private void initializeComponent() {
        cardViewBackGround=findViewById(R.id.cardViewImageBackGround);

        movieImage=findViewById(R.id.topListImage);

        textViewMovieName =findViewById(R.id.topListNameTextView);
        textViewReleaseDate =findViewById(R.id.topListReleaseDate);
        textViewRank =findViewById(R.id.topListEditTextRank);
        textViewGenres =findViewById(R.id.textViewGenres);
        textViewOverview =findViewById(R.id.textViewOverView);

        recyclerView=findViewById(R.id.recyclerViewActor);

        imageButton=findViewById(R.id. detailAddMovieButton);

        toolbar=findViewById(R.id.detailActivityToolBar);
    }


}