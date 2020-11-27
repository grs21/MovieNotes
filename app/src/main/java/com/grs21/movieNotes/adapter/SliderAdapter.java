package com.grs21.movieNotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends  RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<Movie> movieList;
    private ViewPager2 viewPager2;
    private List<Integer> movieImageResource=new ArrayList<>();

    public SliderAdapter(List<Integer> movieList, ViewPager2 viewPager2) {
        this.movieImageResource = movieList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_slider_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.roundedImageView.setImageResource(movieImageResource.get(position));
    }

    @Override
    public int getItemCount() {
        return movieImageResource.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView roundedImageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView=itemView.findViewById(R.id.imageSlide);
        }
          void setImage(List<Integer> movie){

              //Picasso.get().load(movie.getMovieImageURL()).into(roundedImageView);
               // roundedImageView.setImageResource(movie.get);
          }
    }
}
