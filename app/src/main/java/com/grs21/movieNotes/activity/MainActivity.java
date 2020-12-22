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
    import android.view.MenuItem;


    import com.android.volley.Request;

    import com.android.volley.Response;
    import com.android.volley.VolleyError;

    import com.android.volley.toolbox.JsonObjectRequest;
    import com.grs21.movieNotes.R;
    import com.grs21.movieNotes.adapter.SliderImageAdapter;
    import com.grs21.movieNotes.model.Category;
    import com.grs21.movieNotes.model.Movie;
    import com.grs21.movieNotes.util.MovieInitializeDownLoader;
    import com.grs21.movieNotes.util.HttpConnector;
    import com.smarteist.autoimageslider.SliderAnimations;
    import com.smarteist.autoimageslider.SliderView;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;


    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
    import java.util.Scanner;


    public class MainActivity extends AppCompatActivity {

        private Movie movie;
        private static final String TAG = "MainActivity";
        public static final String JSON_POPULAR_LIST_URL="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_TOP_RATE_LIST_URl="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_UP_COMING_LIST_URL="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_NOW_PLAYING_LIST_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        private ArrayList<Movie> sliderViewImageArrayList=new ArrayList<>();
        private SliderView sliderView;

        private LinearLayoutManager linearLayoutManagerParentRecyclerView;
        public static final String FILE_ROOT="/data/user/0/com.grs21.movienotes/files/movie_id.txt";

        private RecyclerView recyclerViewParent;
        private RecyclerView recyclerViewChild;
        private ArrayList<Movie> popular =new ArrayList<>();
        private ArrayList<Movie> topRate =new ArrayList<>();
        private ArrayList<Movie> nowPlaying =new ArrayList<>();
        private ArrayList<Movie> upComing =new ArrayList<>();
        private ArrayList<Category> totalCategory=new ArrayList<>();




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initializeComponent();


            linearLayoutManagerParentRecyclerView =new LinearLayoutManager(this);
            linearLayoutManagerParentRecyclerView.setOrientation(LinearLayoutManager.VERTICAL);

            MovieInitializeDownLoader movieInitializeDownLoader =new MovieInitializeDownLoader(MainActivity.this,recyclerViewParent
                    , linearLayoutManagerParentRecyclerView,totalCategory,popular,topRate,upComing,nowPlaying);
            movieInitializeDownLoader.download(JSON_POPULAR_LIST_URL,"Popular");
            movieInitializeDownLoader.download(JSON_TOP_RATE_LIST_URl,"Top Rate");
            movieInitializeDownLoader.download(JSON_NOW_PLAYING_LIST_URL,"Up Coming");
            movieInitializeDownLoader.download(JSON_UP_COMING_LIST_URL,"Now Playing");

            sliderImageDownload(JSON_UP_COMING_LIST_URL);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);

            return true;
        }
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            ArrayList<String> idArrayList=new ArrayList<>();
            readToFile(idArrayList);
            Intent intent=new Intent(MainActivity.this,UserMovieTopListActivity.class);
            intent.putStringArrayListExtra("id",idArrayList);

            startActivity( intent);
            Log.d(TAG, "onOptionsItemSelected: "+idArrayList.toString());
            return super.onOptionsItemSelected(item);
        }

        private ArrayList<String> readToFile(ArrayList<String> arrayList) {

            File file=new File(FILE_ROOT);
            Scanner scanner = null;
            try {
              scanner=new Scanner(file);
                while (scanner.hasNext()){
                    scanner.useDelimiter(" ");
                    arrayList.add(scanner.next());
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (scanner!=null){
                scanner.close();
                    }
            }
              return arrayList;
        }


        public void sliderImageDownload(String imageDataURL){

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                    , imageDataURL
                    , null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray=response.getJSONArray("results");
                        for (int i=0;i<13;i++){
                           movie=new Movie();
                           movie.setId(jsonArray.getJSONObject(i).getInt("id"));
                           movie.setMovieName(jsonArray.getJSONObject(i).getString("title"));
                           movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString("poster_path"));
                           movie.setRank(jsonArray.getJSONObject(i).getString("vote_average"));
                           movie.setReleaseDate(jsonArray.getJSONObject(i).getString("release_date"));
                           movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString("backdrop_path"));

                           sliderViewImageArrayList.add(movie);
                        }

                        SliderImageAdapter sliderImageAdapter=new SliderImageAdapter(sliderViewImageArrayList,MainActivity.this);
                        sliderView.setSliderAdapter(sliderImageAdapter);
                        sliderView.startAutoCycle();
                        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
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
            HttpConnector.getInstance(MainActivity.this).addRequestQue(jsonObjectRequest);
        }

        public void initializeComponent(){
            sliderView=findViewById(R.id.imageSlider);
            recyclerViewParent=findViewById(R.id.recyclerViewParent);
            recyclerViewChild =findViewById(R.id.recyclerChild);
        }
    }