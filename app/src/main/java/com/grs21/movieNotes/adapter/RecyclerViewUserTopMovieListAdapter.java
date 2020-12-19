package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;

import java.util.ArrayList;


public class RecyclerViewUserTopMovieListAdapter extends RecyclerView.Adapter<RecyclerViewUserTopMovieListAdapter.TopMovieListVH> {

    ArrayList<Movie> movieArrayList;
    Context context;

    public RecyclerViewUserTopMovieListAdapter(Context context, ArrayList<Movie>movieArrayList){
        this.context=context;
        this.movieArrayList=movieArrayList;
    }


    @NonNull
    @Override
    public TopMovieListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_top_movie_list_row,parent,false);
       return new TopMovieListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMovieListVH holder, int position) {

        Movie movie=movieArrayList.get(position);




    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class TopMovieListVH extends  RecyclerView.ViewHolder {

        ImageView imageView;
        TextView rankTextView,movieName,movieReleaseDate;

        public TopMovieListVH(@NonNull View itemView) {
            super(itemView);

        }
    }
}
