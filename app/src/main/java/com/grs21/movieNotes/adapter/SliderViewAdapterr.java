package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Movie;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderViewAdapterr extends SliderViewAdapter<SliderViewAdapterr.SliderViewAdapterVH> {

    private Context context;
    private List<Movie> mSliderItem=new ArrayList<>();

    public SliderViewAdapterr(Context context){
        this.context=context;
    }

    public void renewItems(List<Movie> sliderItem){

        this.mSliderItem=sliderItem;
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        this.mSliderItem.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Movie sliderItem) {
        this.mSliderItem.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderViewAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.view_slider,null);

        return new SliderViewAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderViewAdapterVH viewHolder, int position) {

        Movie movie=mSliderItem.get(position);
        viewHolder.textViewDescription.setText(movie.getMovieName());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getCount() {
        return mSliderItem.size();
    }

    class SliderViewAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageVieBackground;
        ImageView imageGifContainer;
        TextView  textViewDescription;


        public SliderViewAdapterVH(View itemView) {
            super(itemView);


        }
    }
}
