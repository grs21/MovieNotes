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

    private static  String TAG = "MovieDownloaderForListener";
    private ArrayList<Movie> movieArrayList=new ArrayList<>();
    private ArrayList<Category> categoryArrayList;
    private Context context;
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

                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 13; i++) {
                        Movie movie = new Movie();
                        movie.setId(jsonArray.getJSONObject(i).getInt("id"));
                        movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                        movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
                        movie.setRank(jsonArray.getJSONObject(i).getString("vote_average"));
                        movie.setReleaseDate(jsonArray.getJSONObject(i).getString("release_date"));
                        movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString("backdrop_path"));
                        movieArrayList.add(movie);
                    }
                    Log.d(TAG, "onResponse:"+titleCategory+":"+movieArrayList);
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



                    Log.d(TAG, "onResponse: "+categoryArrayList);


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
