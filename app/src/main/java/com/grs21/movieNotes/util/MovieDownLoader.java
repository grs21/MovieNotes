package com.grs21.movieNotes.util;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grs21.movieNotes.adapter.RecyclerViewBoxAdapter;
import com.grs21.movieNotes.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDownLoader {

    private static final String TAG = "DownLoader";
    private Movie movie;
    private ArrayList<Movie> movieArrayList;
    private ArrayList<Movie> totalMovieArrayList;

    private String url;
    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewBoxAdapter recyclerViewBoxAdapter;

    public MovieDownLoader(String url, Context context, RecyclerView recyclerView, LinearLayoutManager layoutManager,ArrayList<Movie>movieArrayList) {
        this.url = url;
        this.context = context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        this.totalMovieArrayList=movieArrayList;
    }

    public synchronized void download() {

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
                        movie.setRank(jsonArray.getJSONObject(i).getString("vote_average"));
                        movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                        JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("genre_ids");
                        for (int b = 0; b < jsonArray1.length(); b++) {
                            movie.moviesSaveArrayList.add((Integer) jsonArray1.get(b));
                        }
                        movie.setMovieImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
                        movieArrayList.add(movie);
                    }
                    Log.d(TAG, "onResponse: "+movieArrayList);
                        recyclerViewBoxAdapter=new RecyclerViewBoxAdapter(movieArrayList,totalMovieArrayList);
                        recyclerView.setAdapter(recyclerViewBoxAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    } catch (JSONException e) {
                       e.printStackTrace();
                    }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
                recyclerView.setAdapter(recyclerViewBoxAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        HttpConnector.getInstance(context).addRequestQue(jsonArrayRequest);
    }

    public ArrayList<Movie> returnList(){

        return movieArrayList;
    }

}
