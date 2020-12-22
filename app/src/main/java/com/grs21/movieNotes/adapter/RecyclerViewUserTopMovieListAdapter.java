package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.squareup.picasso.Picasso;

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

        holder.rankTextView.setText(movie.getRank());
        holder.movieNameTextView.setText(movie.getMovieName());
        holder.movieReleaseDateTextView.setText(movie.getReleaseDate());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movie.getMoviePosterImageURL())
                .into(holder.imageView);

        movieDeleteButton(holder);

    }

    private void movieDeleteButton(TopMovieListVH holder) {

        holder.deleteMovieImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:delete to local file
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class TopMovieListVH extends  RecyclerView.ViewHolder {

        ImageView imageView;
        TextView rankTextView, movieNameTextView, movieReleaseDateTextView;
        ImageButton deleteMovieImageButton;

        public TopMovieListVH(@NonNull View itemView) {
            super(itemView);
            deleteMovieImageButton =itemView.findViewById(R.id.detailAddMovieButton);

            imageView=itemView.findViewById(R.id.topListImage);

            rankTextView=itemView.findViewById(R.id.topListEditTextRank);
            movieNameTextView =itemView.findViewById(R.id.topListMovieName);
            movieReleaseDateTextView =itemView.findViewById(R.id.topListReleaseDate);

        }
    }
}
