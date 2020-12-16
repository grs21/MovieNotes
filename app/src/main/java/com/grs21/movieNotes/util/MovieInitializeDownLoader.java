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
                    JSONArray jsonArray = response.getJSONArray("results");
                        movieArrayList=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        movie = new Movie();
                        movie.setId(jsonArray.getJSONObject(i).getInt("id"));
                        movie.setRank(jsonArray.getJSONObject(i).getString("vote_average"));
                        movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                        JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("genre_ids");
                        for (int b = 0; b < jsonArray1.length(); b++) {
                            movie.moviesSaveArrayList.add((Integer) jsonArray1.get(b));
                        }
                        movie.setMovieImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
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


                    Log.d(TAG, "onResponse: "+ categoryPopular );
                    Log.d(TAG, "onResponse: "+ categoryTopRate);
                    Log.d(TAG, "onResponse: "+ categoryUpComing);
                    Log.d(TAG, "onResponse: "+ categoryNowPlaying);




                         RecyclerViewParentAdapter recyclerViewParentAdapter=new RecyclerViewParentAdapter(context
                                 ,categoryArrayList,recyclerView,layoutManager
                                 ,popularMovieArrayList,topRateMovieArrayList,upComingArrayList,nowPlayingArrayList
                                 , categoryPopular,categoryTopRate, categoryNowPlaying,categoryUpComing,0);

                             recyclerView.setAdapter(recyclerViewParentAdapter);
                             recyclerView.setLayoutManager(layoutManager);
                             if (categoryArrayList.size()==4){
                             Log.d(TAG, "onResponse: DENEMEE"+categoryArrayList);
                             Log.d(TAG, "onResponse:0 indeks:"+categoryArrayList.get(0));
                             Log.d(TAG, "onResponse: 1 indeks:"+categoryArrayList.get(1));
                             Log.d(TAG, "onResponse: 2 indeks:"+categoryArrayList.get(2));
                             Log.d(TAG, "onResponse: 3 indeks:"+categoryArrayList.get(3));}

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
