package com.grs21.movieNotes.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public  class RecyclerViewBoxAdapter extends RecyclerView.Adapter<RecyclerViewBoxAdapter.ViewHolderBoxAdapter> {


    private ArrayList<Movie> movieArrayList;
    private static final String TAG = "BoxAdapter";

    public RecyclerViewBoxAdapter(ArrayList<Movie> movieName) {
        this.movieArrayList = movieName;

    }

    //Todo:What do when create
    @NonNull
    @Override
    public ViewHolderBoxAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_movie_box,parent,false);

        return new ViewHolderBoxAdapter(view);
    }

    //Todo:What do when load
    @Override
    public synchronized void  onBindViewHolder(@NonNull ViewHolderBoxAdapter holder, int position) {

        if (movieArrayList!=null && !movieArrayList.isEmpty()) {
                holder.textViewRowMovieName.setText(movieArrayList.get(position).getMovieName());
                Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieArrayList.get(position)
                        .getMovieImageURL()).into(holder.imageViewRowMovieImage);

        }



    }
/*
    public void nullControl(){
        if (movieArrayList ==null) {
            movieArrayList =new ArrayList<>();
            Movie movie = new Movie();
            movie.setMovieName("null2");
            Movie movie2 = new Movie();
            movie2.setMovieName("null3");
            Movie movie3 = new Movie();
            movie3.setMovieName("null4");
            Movie movie4 = new Movie();
            movie4.setMovieName("null5");
            Movie movie5 = new Movie();
            movie5.setMovieName("null6");
            movieArrayList.add(movie);
            movieArrayList.add(movie2);
            movieArrayList.add(movie3);
            movieArrayList.add(movie4);
            movieArrayList.add(movie5);
        }
        else if (movieArrayList.isEmpty()){
            Movie movie = new Movie();
            movie.setMovieName("null1");
            Movie movie2 = new Movie();
            movie2.setMovieName("null2");
            Movie movie3 = new Movie();
            movie3.setMovieName("null3");
            Movie movie4 = new Movie();
            movie4.setMovieName("null4");
            Movie movie5 = new Movie();
            movie5.setMovieName("null5");
            movieArrayList.add(movie);
            movieArrayList.add(movie2);
            movieArrayList.add(movie3);
            movieArrayList.add(movie4);
            movieArrayList.add(movie5);
        }

    }*/

    //Todo:RecyclerView row count
    @Override
    public int getItemCount() {

        int count;
        if (movieArrayList ==null){
           count=4;
        }
        else if (!movieArrayList.isEmpty()){
            count= movieArrayList.size();
        }else count=4;
        return count;
    }

    //Todo: put specific layout
    class ViewHolderBoxAdapter extends  RecyclerView.ViewHolder{
        ImageView imageViewRowMovieImage;
        TextView textViewRowMovieName;

        public ViewHolderBoxAdapter(@NonNull View itemView) {
            super(itemView);
            imageViewRowMovieImage=itemView.findViewById(R.id.imageView_recycle_row_image);
            textViewRowMovieName=itemView.findViewById(R.id.textView_recycle_row_textView);

        }
    }
}
