package com.grs21.movieNotes.util.downloader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.MainActivity;
import com.grs21.movieNotes.adapter.RecyclerViewParentAdapter;
import com.grs21.movieNotes.model.Category;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.HttpConnector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MovieDownloaderForListener {

    private static final String JSON_OBJECT_KEYWORD_ID="id";
    private static final String JSON_OBJECT_KEYWORD_MOVIE_TITLE="original_title";
    private static final String JSON_OBJECT_KEYWORD_POSTER_PATH="poster_path";
    private static final String JSON_OBJECT_KEYWORD_VOTE_AVERAGE="vote_average";
    private static final String JSON_OBJECT_KEYWORD_RELEASE_DATE="release_date";
    private static final String JSON_OBJECT_KEYWORD_RESULT ="results";
    private static final String JSON_OBJECT_KEYWORD_BACKDROP_PATH="backdrop_path";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private ArrayList<Category> categoryArrayList;
    private final Context context;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    ArrayList<Movie> popularMovieArrayList;
    ArrayList<Movie> topRateMovieArrayList;
    ArrayList<Movie> upComingArrayList;
    private Category categoryPopular;
    private Category categoryTopRate;
    private Category categoryUpComing;
    public MovieDownloaderForListener(Context context, ArrayList<Category> categoryArrayList
            , LinearLayoutManager layoutManager, RecyclerView recyclerView,  ArrayList<Movie> popularMovieArrayList
            , ArrayList<Movie> topRateMovieArrayList, ArrayList<Movie> upComingMovieArrayList
            ,Category categoryPopular,Category categoryTopRate,Category categoryUpComing)
    {
        this.categoryPopular=categoryPopular;
        this.categoryTopRate=categoryTopRate;
        this.categoryUpComing=categoryUpComing;
        this.context = context;
        this.categoryArrayList=categoryArrayList;
        this.layoutManager = layoutManager;
        this.recyclerView = recyclerView;
        this.popularMovieArrayList=popularMovieArrayList;
        this.topRateMovieArrayList=topRateMovieArrayList;
        this.upComingArrayList=upComingMovieArrayList;

    }
    public void download(String url, String titleCategory) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                , url
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(JSON_OBJECT_KEYWORD_RESULT);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Movie movie = new Movie();
                        movie.setId(jsonArray.getJSONObject(i).getInt(JSON_OBJECT_KEYWORD_ID));
                        movie.setMovieName(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_MOVIE_TITLE));
                        movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_POSTER_PATH));
                        movie.setRank(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_VOTE_AVERAGE));
                        movie.setReleaseDate(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_RELEASE_DATE));
                        movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_BACKDROP_PATH));
                        movieArrayList.add(movie);
                    }
                    categoryArrayList.clear();
                    switch (titleCategory){
                        case "Popular":
                            popularMovieArrayList.addAll(movieArrayList);
                            categoryPopular.setCategoryTitle(context.getString(R.string.popular));
                            categoryPopular.setMovieArrayList(popularMovieArrayList);
                            break;
                        case "Top Rate":
                            topRateMovieArrayList.addAll(movieArrayList);
                            categoryTopRate.setCategoryTitle(context.getString(R.string.Top_Rate));
                            categoryTopRate.setMovieArrayList(topRateMovieArrayList);
                            break;
                        case "Up Coming":
                            upComingArrayList.addAll(movieArrayList);
                            categoryUpComing.setCategoryTitle(context.getString(R.string.Up_coming));
                            categoryUpComing.setMovieArrayList(upComingArrayList);
                            break;
                    }
                    categoryArrayList.add(categoryPopular);
                    categoryArrayList.add(categoryTopRate);
                    categoryArrayList.add(categoryUpComing);
                    RecyclerViewParentAdapter recyclerViewParentAdapter=new RecyclerViewParentAdapter(context
                                        ,categoryArrayList,recyclerView,layoutManager,popularMovieArrayList,topRateMovieArrayList
                                        ,upComingArrayList,categoryPopular,categoryTopRate,categoryUpComing);
                                        recyclerViewParentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Connection Error");
                builder.setPositiveButton("okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("LOGOUT", true);
                        context.startActivity(intent);
                    }
                });
                builder.show();
            }
        });
        HttpConnector.getInstance(context).addRequestQue(jsonObjectRequest);
    }
}
