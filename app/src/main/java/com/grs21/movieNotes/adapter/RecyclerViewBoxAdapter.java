package com.grs21.movieNotes.adapter;

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

public class RecyclerViewBoxAdapter extends RecyclerView.Adapter<RecyclerViewBoxAdapter.ViewHolderBoxAdapter> {


    private ArrayList<Movie> movieName;
    private static final String TAG = "RecyclerAdapter";

    public RecyclerViewBoxAdapter(ArrayList<Movie> movieName) {
        this.movieName = movieName;

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
    public void onBindViewHolder(@NonNull ViewHolderBoxAdapter holder, int position) {

            Movie movie=movieName.get(position);

            holder.textViewRowMovieName.setText(movie.getMovieName());
         //Todo:image Set!!
        // holder.imageViewRowMovieImage.setImageBitmap();

    }

    //Todo:RecyclerView row count
    @Override
    public int getItemCount() {

        return movieName.size();
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
