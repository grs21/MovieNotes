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

public class MovieInitializeDownLoader {

    private static final String JSON_OBJECT_KEYWORD_ID="id";
    private static final String JSON_OBJECT_KEYWORD_MOVIE_TITLE="title";
    private static final String JSON_OBJECT_KEYWORD_POSTER_PATH="poster_path";
    private static final String JSON_OBJECT_KEYWORD_VOTE_AVERAGE="vote_average";
    private static final String JSON_OBJECT_KEYWORD_RELEASE_DATE="release_date";
    private static final String JSON_OBJECT_KEYWORD_RESULT ="result";
    private static final String JSON_OBJECT_KEYWORD_BACKDROP_PATH="backdrop_path";

    private static final String TAG = "DownLoader";
    private Movie movie;
    private ArrayList<Movie> movieArrayList;
    private ArrayList<Movie>movies=new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewParentAdapter recyclerViewParentAdapter;
    private ArrayList<Category> categoryArrayList;
    private Category categoryPopular=new Category();
    private Category categoryTopRate=new Category();
    private Category categoryNowPlaying=new Category();
    private Category categoryUpComing=new Category();
    ArrayList<Movie> popularMovieArrayList;
    ArrayList<Movie> topRateMovieArrayList;
    ArrayList<Movie> upComingArrayList;
    ArrayList<Movie> nowPlayingArrayList;
    public MovieInitializeDownLoader(Context context, RecyclerView recyclerView, LinearLayoutManager layoutManager
            , ArrayList<Category> categoryArrayList, ArrayList<Movie> popularMovieArrayList
            , ArrayList<Movie> topRateMovieArrayList, ArrayList<Movie> upComingMovieArrayList, ArrayList<Movie> nowPlayingMovieArrayList)
    {
        this.popularMovieArrayList=popularMovieArrayList;
        this.topRateMovieArrayList=topRateMovieArrayList;
        this.upComingArrayList=upComingMovieArrayList;
        this.nowPlayingArrayList=nowPlayingMovieArrayList;
        this.categoryArrayList=categoryArrayList;
        this.context = context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
    }


    public synchronized void download(String url,String titleCategory) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET
                , url
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray(JSON_OBJECT_KEYWORD_RESULT);
                        movieArrayList=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        movie = new Movie();
                        movie.setId(jsonArray.getJSONObject(i).getInt(JSON_OBJECT_KEYWORD_ID));
                        movie.setRank(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_VOTE_AVERAGE));
                        movie.setMovieName(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_MOVIE_TITLE));
                        movie.setReleaseDate(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_RELEASE_DATE));
                        movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_BACKDROP_PATH));
                        movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_POSTER_PATH));
                        movieArrayList.add(movie);
                    }

                    switch (titleCategory){

                        case "Popular":
                            popularMovieArrayList.addAll(movieArrayList);
                            categoryPopular.setCategoryTitle(titleCategory);
                            categoryPopular.setMovieArrayList(popularMovieArrayList);
                            categoryArrayList.add(categoryPopular);
                            break;
                        case "Top Rate":
                            topRateMovieArrayList.addAll(movieArrayList);
                            categoryTopRate.setCategoryTitle(titleCategory);
                            categoryTopRate.setMovieArrayList(topRateMovieArrayList);
                            categoryArrayList.add(categoryTopRate);
                            break;
                        case "Up Coming":
                            upComingArrayList.addAll(movieArrayList);
                            categoryUpComing.setCategoryTitle(titleCategory);
                            categoryUpComing.setMovieArrayList(upComingArrayList);
                            categoryArrayList.add(categoryUpComing);
                            break;
                        case "Now Playing":
                            nowPlayingArrayList.addAll(movieArrayList);
                            categoryNowPlaying.setCategoryTitle(titleCategory);
                            categoryNowPlaying.setMovieArrayList(nowPlayingArrayList);
                            categoryArrayList.add(categoryNowPlaying);
                            break;
                    }
                    RecyclerViewParentAdapter recyclerViewParentAdapter=new RecyclerViewParentAdapter(context
                                 ,categoryArrayList,recyclerView,layoutManager
                                 ,popularMovieArrayList,topRateMovieArrayList,upComingArrayList,nowPlayingArrayList
                                 , categoryPopular,categoryTopRate, categoryNowPlaying,categoryUpComing,0);

                             recyclerView.setAdapter(recyclerViewParentAdapter);
                             recyclerView.setLayoutManager(layoutManager);
                }
                    catch (JSONException e) {
                       e.printStackTrace();
                    }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
                recyclerView.setAdapter(recyclerViewParentAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        HttpConnector.getInstance(context).addRequestQue(jsonArrayRequest);
    }


}
