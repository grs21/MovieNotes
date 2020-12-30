    package com.grs21.movieNotes.activity;


    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;


    import com.android.volley.Request;

    import com.android.volley.Response;
    import com.android.volley.VolleyError;

    import com.android.volley.toolbox.JsonObjectRequest;
    import com.grs21.movieNotes.R;
    import com.grs21.movieNotes.adapter.SliderImageAdapter;
    import com.grs21.movieNotes.model.Category;
    import com.grs21.movieNotes.model.Movie;
    import com.grs21.movieNotes.util.downloader.MovieInitializeDownLoader;
    import com.grs21.movieNotes.util.HttpConnector;
    import com.grs21.movieNotes.util.TxtFileReader;
    import com.smarteist.autoimageslider.SliderAnimations;
    import com.smarteist.autoimageslider.SliderView;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity  {

        private Movie movie;
        private static final String TAG = "MainActivity";
        public static final String JSON_POPULAR_LIST_URL="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_TOP_RATE_LIST_URl="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_UP_COMING_LIST_URL="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_NOW_PLAYING_LIST_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
        public static final String JSON_SEARCH_MOVIE_URL="https://api.themoviedb.org/3/search/movie?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&query=%s&page=1&include_adult=false";
        private ArrayList<Movie> sliderViewImageArrayList=new ArrayList<>();
        private static final String ON_CLICKED_HOME_BUTTON_INTENT_KEY ="HOME_BUTTON";
        private static final String ON_CLICKED_ON_THE_MOVIE_INTENT_KEY ="movie";
        private static final String JSON_OBJECT_KEYWORD_RESULT ="results";
        private static final String JSON_OBJECT_KEYWORD_ID="id";
        private static final String JSON_OBJECT_KEYWORD_MOVIE_TITLE="original_title";
        private static final String JSON_OBJECT_KEYWORD_POSTER_PATH="poster_path";
        private static final String JSON_OBJECT_KEYWORD_VOTE_AVERAGE="vote_average";
        private static final String JSON_OBJECT_KEYWORD_RELEASE_DATE="release_date";
        private static final String JSON_OBJECT_KEYWORD_BACKDROP_PATH="backdrop_path";


        private SliderView sliderView;
        private RecyclerView recyclerViewParent;
        private ArrayList<Movie> popular =new ArrayList<>();
        private ArrayList<Movie> topRate =new ArrayList<>();
        private ArrayList<Movie> nowPlaying =new ArrayList<>();
        private ArrayList<Movie> upComing =new ArrayList<>();
        private ArrayList<Category> totalCategory=new ArrayList<>();
        private AutoCompleteTextView autoCompleteTextView;
        private Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initializeComponent();
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.search_icon);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            LinearLayoutManager linearLayoutManagerParentRecyclerView = new LinearLayoutManager(this);
            linearLayoutManagerParentRecyclerView.setOrientation(LinearLayoutManager.VERTICAL);
            MovieInitializeDownLoader movieInitializeDownLoader =new MovieInitializeDownLoader(MainActivity.this,recyclerViewParent
                    , linearLayoutManagerParentRecyclerView,totalCategory,popular,topRate,upComing,nowPlaying);
            movieInitializeDownLoader.download(JSON_POPULAR_LIST_URL,"Popular");
            movieInitializeDownLoader.download(JSON_TOP_RATE_LIST_URl,"Top Rate");
            movieInitializeDownLoader.download(JSON_NOW_PLAYING_LIST_URL,"Up Coming");
            movieInitializeDownLoader.download(JSON_UP_COMING_LIST_URL,"Now Playing");
            sliderImageDownload(JSON_UP_COMING_LIST_URL);
            if (getIntent().getBooleanExtra("LOGOUT", false))
            {
                finish();
            }
            autoCompleteTextView();
        }
        private void autoCompleteTextView() {
            autoCompleteTextView.setDropDownHeight(400);
            autoCompleteTextView.setDropDownVerticalOffset(5);
            autoCompleteTextView.setThreshold(2);
            autoCompleteTextView.clearFocus();
            autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String newText = s.toString();
                    if (!newText.isEmpty() && newText != null){
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                                , String.format(JSON_SEARCH_MOVIE_URL,newText)
                                , null
                                , new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<Movie> searchMovies = new ArrayList<>();
                                try {
                                    JSONArray jsonArray = response.getJSONArray(JSON_OBJECT_KEYWORD_RESULT);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        movie = new Movie();
                                        movie.setId(jsonArray.getJSONObject(i).getInt(JSON_OBJECT_KEYWORD_ID));
                                        movie.setMovieName(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_MOVIE_TITLE));
                                        movie.setRank(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_VOTE_AVERAGE));
                                        movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_POSTER_PATH));
                                        movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_BACKDROP_PATH));
                                        movie.setReleaseDate(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_RELEASE_DATE));
                                        searchMovies.add(movie);
                                    }
                                    ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(MainActivity.this
                                            , R.layout.outocomplate_item,R.id.autocomplate_name, searchMovies);
                                    autoCompleteTextView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse: " + error);
                            }
                        });
                    HttpConnector.getInstance(MainActivity.this).addRequestQue(jsonObjectRequest);
                }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie= (Movie)parent.getItemAtPosition(position);
                    autoCompleteTextView.setTag(movie);
                }
            });
        }

        public void sliderImageDownload(String imageDataURL){
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET
                    , imageDataURL
                    , null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray=response.getJSONArray(JSON_OBJECT_KEYWORD_RESULT);
                        for (int i=0;i<13;i++){
                           movie=new Movie();
                           movie.setId(jsonArray.getJSONObject(i).getInt(JSON_OBJECT_KEYWORD_ID));
                           movie.setMovieName(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_MOVIE_TITLE));
                           movie.setMoviePosterImageURL(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_POSTER_PATH));
                           movie.setRank(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_VOTE_AVERAGE));
                           movie.setReleaseDate(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_RELEASE_DATE));
                           movie.setMovieBackdropPathImageUrl(jsonArray.getJSONObject(i).getString(JSON_OBJECT_KEYWORD_BACKDROP_PATH));
                           sliderViewImageArrayList.add(movie);
                        }
                        Log.d(TAG, "onResponse: "+sliderViewImageArrayList);
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
            autoCompleteTextView=findViewById(R.id.autoCompleteTextView);
            toolbar=findViewById(R.id.mainActivityToolBar);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.homeButton:
                    TxtFileReader txtFileReader = new TxtFileReader();
                    Intent intent = new Intent(MainActivity.this, UserMovieTopListActivity.class);
                    intent.putStringArrayListExtra(ON_CLICKED_HOME_BUTTON_INTENT_KEY, txtFileReader.read());
                    startActivity(intent);
                    break;
                    case R.id.sendButton:
                    Movie movie=(Movie)autoCompleteTextView.getTag();
                    Intent intent1=new Intent(MainActivity.this,MovieDetailActivity.class);
                    intent1.putExtra(ON_CLICKED_ON_THE_MOVIE_INTENT_KEY,movie);
                    startActivity(intent1);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
    }