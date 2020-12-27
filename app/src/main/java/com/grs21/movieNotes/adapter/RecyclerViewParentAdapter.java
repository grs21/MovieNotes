package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Category;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.MovieDownloaderForListener;
import com.grs21.movieNotes.util.RecyclerOnScrollListener;

import java.util.ArrayList;

public  class  RecyclerViewParentAdapter extends RecyclerView.Adapter<RecyclerViewParentAdapter.ViewHolderRowAdapter>{

    private static final String TAG = "RowAdapter";
    private Context context;
    public ArrayList<Category> categoryList;
    private RecyclerViewChildAdapter recyclerViewChildAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    ArrayList<Movie> popularMovieArrayList;
    ArrayList<Movie> topRateMovieArrayList;
    ArrayList<Movie> upComingArrayList;
    ArrayList<Movie> nowPlayingArrayList;
    private String JSON_POPULAR_LIST_URL="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    private String JSON_TOP_RATE_LIST_URl="https://api.themoviedb.org/3/movie/top_rated?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    private String JSON_UP_COMING_LIST_URL="https://api.themoviedb.org/3/movie/upcoming?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    private String JSON_NOW_PLAYING_LIST_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    private Category categoryPopular;
    private Category categoryTopRate;
    private Category categoryNowPlaying;
    private Category categoryUpComing;
    private RecyclerViewParentAdapter recyclerViewParentAdapter=this;

    public  RecyclerViewParentAdapter(Context context, ArrayList<Category> categoryList,RecyclerView recyclerView,LinearLayoutManager layoutManager,ArrayList<Movie> popularMovieArrayList
            , ArrayList<Movie> topRateMovieArrayList, ArrayList<Movie> upComingMovieArrayList, ArrayList<Movie> nowPlayingMovieArrayList
    ,Category categoryPopular,Category categoryTopRate,Category categoryNowPlaying,Category categoryUpComing)
    {

        this.categoryPopular=categoryPopular;
        this.categoryTopRate=categoryTopRate;
        this.categoryNowPlaying=categoryNowPlaying;
        this.categoryUpComing=categoryUpComing;
        this.layoutManager=layoutManager;
        this.recyclerView=recyclerView;
        this.context = context;
        this.categoryList = categoryList;
        this.popularMovieArrayList=popularMovieArrayList;
        this.topRateMovieArrayList=topRateMovieArrayList;
        this.upComingArrayList=upComingMovieArrayList;
        this.nowPlayingArrayList=nowPlayingMovieArrayList;
    }
    @NonNull
    @Override
    public ViewHolderRowAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_row_layout,parent,false);
        return new ViewHolderRowAdapter(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderRowAdapter holder, int position) {
        holder.textViewRowTitle.setText(categoryList.get(position).getCategoryTitle());
        recyclerViewChildAdapter=new RecyclerViewChildAdapter(categoryList.get(position).getMovieArrayList(),context);
        LinearLayoutManager layoutManagerChild=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        recyclerViewChildAdapter.notifyDataSetChanged();
        holder.recyclerViewChild.setLayoutManager(layoutManagerChild);
        holder.recyclerViewChild.setAdapter(recyclerViewChildAdapter);
        holder.recyclerViewChild.addOnScrollListener(new RecyclerOnScrollListener(layoutManagerChild) {
            @Override
            public void onLoadMore(int currentPage,int lastItemLocation) {
                MovieDownloaderForListener movieDownloaderForListener=new MovieDownloaderForListener(context,categoryList,layoutManager
                        ,recyclerView,popularMovieArrayList,topRateMovieArrayList,upComingArrayList,nowPlayingArrayList
                        , categoryPopular,categoryTopRate, categoryNowPlaying,categoryUpComing);
                switch (position){
                    case 0:
                        movieDownloaderForListener.download(String.format(JSON_POPULAR_LIST_URL,currentPage)
                                ,"Popular");
                        break;
                    case 1:
                        movieDownloaderForListener.download(String.format(JSON_TOP_RATE_LIST_URl,currentPage)
                                ,"Top Rate");
                        break;
                    case 2:
                        movieDownloaderForListener.download(String.format(JSON_UP_COMING_LIST_URL,currentPage)
                                ,"Up Coming");
                        break;
                    case 3:
                        movieDownloaderForListener.download(String.format(JSON_NOW_PLAYING_LIST_URL,currentPage)
                                ,"Now Playing");
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public class ViewHolderRowAdapter extends RecyclerView.ViewHolder {
        TextView textViewRowTitle;
        RecyclerView recyclerViewChild;
        public ViewHolderRowAdapter(@NonNull View itemView) {
            super(itemView);
            textViewRowTitle=itemView.findViewById(R.id.cat_title);
            recyclerViewChild =itemView.findViewById(R.id.recyclerChild);
        }
    }
}
