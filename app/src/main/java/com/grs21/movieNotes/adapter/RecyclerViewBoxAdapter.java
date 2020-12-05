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

public class RecyclerViewBoxAdapter extends RecyclerView.Adapter<RecyclerViewBoxAdapter.ViewHolderBoxAdapter> {


    private ArrayList<Movie> movieName;
    private static final String TAG = "BoxAdapter";

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

        if (movieName==null) {
            movieName=new ArrayList<>();
            Movie movie = new Movie();
            movie.setMovieName("null2");
            Movie movie2 = new Movie();
            movie.setMovieName("null3");
            Movie movie3 = new Movie();
            movie.setMovieName("null4");
            Movie movie4 = new Movie();
            movie.setMovieName("null5");
            Movie movie5 = new Movie();
            movie.setMovieName("null6");
            movieName.add(movie);
            movieName.add(movie2);
            movieName.add(movie3);
            movieName.add(movie4);
            movieName.add(movie5);
        }
        else if (movieName.isEmpty()){
            Movie movie = new Movie();
            movie.setMovieName("null1");
            Movie movie2 = new Movie();
            movie.setMovieName("null2");
            Movie movie3 = new Movie();
            movie.setMovieName("null3");
            Movie movie4 = new Movie();
            movie.setMovieName("null4");
            Movie movie5 = new Movie();
            movie.setMovieName("null5");
            movieName.add(movie);
            movieName.add(movie2);
            movieName.add(movie3);
            movieName.add(movie4);
            movieName.add(movie5);
        }

            Log.d(TAG, "onBindViewHolder: movieName:"+movieName);
            holder.textViewRowMovieName.setText(movieName.get(position).getMovieName());

            //Todo:image Set!!
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movieName.get(position)
                    .getMovieImageURL()).into(holder.imageViewRowMovieImage);

    }

    //Todo:RecyclerView row count
    @Override
    public int getItemCount() {

        int count;
        if (movieName==null){
           count=4;
        }
        else if (!movieName.isEmpty()){
            count=movieName.size();
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
