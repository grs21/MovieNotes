package com.grs21.movieNotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.HttpConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListView listView;
    private Movie movie;
    private RequestQueue requestQueue;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
/*
        ArrayList<String> movieName=new ArrayList<>();
        movieName.add("spiderMan");
        movieName.add("SuperMan");
        movieName.add("HarryPother");
        movieName.add("The LOrd Of The Rings");
        movieName.add("Big Fish");



        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(movieName);
        recyclerView.setAdapter(recyclerAdapter);
          JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET
                , "https://run.mocky.io/v3/1e3c8246-75f2-4645-80ed-4db7ce099ecf"
                , null
                , new Response.Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {

                      try {
                          Log.d(TAG, "onResponse:"+response.getString("Title"));
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }
                }
              , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d(TAG, "onErrorResponse:"+error);
                }
              }
        );
*/
        requestQueue = HttpConnector.getInstance(getApplicationContext()).getRequestQueue();



        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET
                , "https://run.mocky.io/v3/c438783d-1f84-40d1-b574-77c5ad5b612d"
                , null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {



                    Log.d(TAG, "onResponse:JsonArray "+response.getJSONObject(0).getString("Title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: JsonArray"+error);
            }
        }
        );
           HttpConnector.getInstance(getApplicationContext()).addRequestQue(jsonArrayRequest);

    }

    public void initializeComponent(){

        listView=findViewById(R.id.listView);
        recyclerView=findViewById(R.id.recyclerView);
    }
}