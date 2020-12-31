package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.MovieDetailActivity;
import com.grs21.movieNotes.model.Movie;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageAdapter.SliderViewAdapterVH> {

    private static final String TAG = "SliderImageAdapter";
    private  ArrayList<Movie> movieSliderItemArrayList;
    private Context context;

    public SliderImageAdapter(ArrayList<Movie> movieSliderItemArrayList, Context context){
        this.context=context;
        this.movieSliderItemArrayList = movieSliderItemArrayList;
    }
    @Override
    public SliderViewAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.image_slider_item_layout,null);
        return new SliderViewAdapterVH(view);
    }
    @Override
    public void onBindViewHolder(SliderViewAdapterVH viewHolder, int position) {
        Movie movie= movieSliderItemArrayList.get(position);
        viewHolder.textViewDescription.setText(movie.getMovieName());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+ movieSliderItemArrayList
                .get(position).getMoviePosterImageURL()).fit().into(viewHolder.imageVieBackground);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MovieDetailActivity.class);
                Movie movie= movieSliderItemArrayList.get(position);
                intent.putExtra("movie",movie);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getCount() {
        return movieSliderItemArrayList.size();
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
