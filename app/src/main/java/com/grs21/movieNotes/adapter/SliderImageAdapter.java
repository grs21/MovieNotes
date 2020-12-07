package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.TimedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageAdapter.SliderViewAdapterVH> {


    private  ArrayList<Movie> movieSliderItem;

    public SliderImageAdapter(ArrayList<Movie> movieSliderItem ){

        this.movieSliderItem=movieSliderItem;
    }
    @Override
    public SliderViewAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.image_slider_item_layout,null);

        return new SliderViewAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderViewAdapterVH viewHolder, int position) {

        Movie movie=movieSliderItem.get(position);

        viewHolder.textViewDescription.setText(movie.getMovieName());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movieSliderItem.get(position).getMovieImageURL()).fit().into(viewHolder.imageVieBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getCount() {
        return movieSliderItem.size();
    }

    class SliderViewAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageVieBackground;
        ImageView imageGifContainer;
        TextView  textViewDescription;
        public SliderViewAdapterVH(View itemView) {
            super(itemView);
            imageVieBackground=itemView.findViewById(R.id.auto_image_slider);
            imageGifContainer=itemView.findViewById(R.id.gif_container);
            textViewDescription=itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView=itemView;


        }
    }
}
