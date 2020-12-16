package com.grs21.movieNotes.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.MovieDetailActivity;
import com.grs21.movieNotes.model.Category;
import com.grs21.movieNotes.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public  class RecyclerViewChildAdapter extends RecyclerView.Adapter<RecyclerViewChildAdapter.ViewHolderBoxAdapter> {


    private ArrayList<Movie> movieArrayList;
    private static final String TAG = "BoxAdapter";
    private static RecyclerViewChildAdapter instance;
    private Context context;

    public RecyclerViewChildAdapter(ArrayList<Movie>movieArrayList, Context context) {
        this.movieArrayList=movieArrayList;
        this.context=context;

    }

    //Todo:What do when create
    @NonNull
    @Override
    public ViewHolderBoxAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_movie_row_item,parent,false);
        return new ViewHolderBoxAdapter(view);
    }

    //Todo:What do when load
    @Override
    public synchronized void  onBindViewHolder(@NonNull ViewHolderBoxAdapter holder, int position) {

        Log.d(TAG, "onBindViewHolder: MOVIE_ARRAY_LIST:"+movieArrayList.get(position));
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieArrayList.get(position)
                        .getMovieImageURL()).into(holder.imageViewRowMovieImage);

        holder.imageViewRowMovieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtra("id",movieArrayList.get(position).getId());
                v.getContext().startActivity(intent);
                Log.d(TAG, "onClick: "+movieArrayList.get(position).getId());
            }
        });

    }


    //Todo:RecyclerView row count
    @Override
    public int getItemCount() {

        return movieArrayList.size();
    }

    //Todo: put specific layout
    class ViewHolderBoxAdapter extends  RecyclerView.ViewHolder{
        ImageView imageViewRowMovieImage;


        public ViewHolderBoxAdapter(@NonNull View itemView) {
            super(itemView);
            imageViewRowMovieImage=itemView.findViewById(R.id.item_image);


        }
    }
}
