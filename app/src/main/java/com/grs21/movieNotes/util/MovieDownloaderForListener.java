package com.grs21.movieNotes.util;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.adapter.RecyclerViewParentAdapter;
import com.grs21.movieNotes.model.Category;
import com.grs21.movieNotes.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MovieDownloaderForListener {

    private static final String JSON_OBJECT_KEYWORD_ID="id";
    private static final String JSON_OBJECT_KEYWORD_MOVIE_TITLE="title";
    private static final String JSON_OBJECT_KEYWORD_POSTER_PATH="poster_path";
    private static final String JSON_OBJECT_KEYWORD_VOTE_AVERAGE="vote_average";
    private static final String JSON_OBJECT_KEYWORD_RELEASE_DATE="release_date";
    private static final String JSON_OBJECT_KEYWORD_RESULT ="result";
    private static final String JSON_OBJECT_KEYWORD_BACKDROP_PATH="backdrop_path";

    private static  String TAG = "MovieDownloaderForListener";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private ArrayList<Category> categoryArrayList;
    private final Context context;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    ArrayList<Movie> popularMovieArrayList;
    ArrayList<Movie> topRateMovieArrayList;
    ArrayList<Movie> upComingArrayList;
    ArrayList<Movie> nowPlayingArrayList;

    private Category categoryPopular;
    private Category categoryTopRate;
    private Category categoryNowPlaying;
    private Category categoryUpComing;
    private RecyclerView recyclerViewChild;
    private int itemLastLocation;
    RecyclerViewParentAdapter recyclerViewParentAdapter;



    public MovieDownloaderForListener(Context context, ArrayList<Category> categoryArrayList
            , LinearLayoutManager layoutManager, RecyclerView recyclerView,  ArrayList<Movie> popularMovieArrayList
            , ArrayList<Movie> topRateMovieArrayList, ArrayList<Movie> upComingMovieArrayList, ArrayList<Movie> nowPlayingMovieArrayList
            ,Category categoryPopular,Category categoryTopRate,Category categoryNowPlaying,Category categoryUpComing
            ,RecyclerView recyclerViewChild
    ,RecyclerViewParentAdapter recyclerViewParentAdapter)
    {
        this.recyclerViewParentAdapter=recyclerViewParentAdapter;
        this.recyclerViewChild=recyclerViewChild;
        this.categoryPopular=categoryPopular;
        this.categoryTopRate=categoryTopRate;
        this.categoryNowPlaying=categoryNowPlaying;
        this.categoryUpComing=categoryUpComing;
        this.context = context;
        this.categoryArrayList=categoryArrayList;
        this.layoutManager = layoutManager;
        this.recyclerView = recyclerView;
        this.popularMovieArrayList=popularMovieArrayList;
        this.topRateMovieArrayList=topRateMovieArrayList;
        this.upComingArrayList=upComingMovieArrayList;
        this.nowPlayingArrayList=nowPlayingMovieArrayList;
    }

    public void download(String url, String titleCategory,int position) {
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
                    switch (titleCategory){
                        case "Popular":
                            popularMovieArrayList.addAll(movieArrayList);
                            categoryPopular.setCategoryTitle(titleCategory);
                            categoryPopular.setMovieArrayList(popularMovieArrayList);
                            break;
                        case "Top Rate":
                            topRateMovieArrayList.addAll(movieArrayList);
                            categoryTopRate.setCategoryTitle(titleCategory);
                            categoryTopRate.setMovieArrayList(topRateMovieArrayList);
                            break;
                        case "Up Coming":
                            upComingArrayList.addAll(movieArrayList);
                            categoryUpComing.setCategoryTitle(titleCategory);
                            categoryUpComing.setMovieArrayList(upComingArrayList);
                            break;
                        case "Now Playing":
                            nowPlayingArrayList.addAll(movieArrayList);
                            categoryNowPlaying.setCategoryTitle(titleCategory);
                            categoryNowPlaying.setMovieArrayList(nowPlayingArrayList);
                            break;
                    }
                    RecyclerViewParentAdapter recyclerViewParentAdapter=new RecyclerViewParentAdapter(context
                                        ,categoryArrayList,recyclerView,layoutManager,popularMovieArrayList,topRateMovieArrayList
                                        ,upComingArrayList,nowPlayingArrayList
                                        ,categoryPopular,categoryTopRate, categoryNowPlaying,categoryUpComing,itemLastLocation);
                                        recyclerViewParentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });
        HttpConnector.getInstance(context).addRequestQue(jsonObjectRequest);
    }
}
