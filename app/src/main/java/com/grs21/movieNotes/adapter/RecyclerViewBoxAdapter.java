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
    private ArrayList<Movie> totalMovieArrayList;
    private static final String TAG = "BoxAdapter";
    private static RecyclerViewBoxAdapter instance;

    public  RecyclerViewBoxAdapter(ArrayList<Movie> movieName,ArrayList<Movie>totalMovieArrayList) {
        this.movieArrayList = movieName;
        this.totalMovieArrayList=totalMovieArrayList;
        totalMovieArrayList.addAll(movieArrayList);
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

        Log.d(TAG, "onBindViewHolder: MOVIE_ARRAY_LIST"+movieArrayList);

        if (totalMovieArrayList!=null && !totalMovieArrayList.isEmpty()) {
                holder.textViewRowMovieName.setText(totalMovieArrayList.get(position).getMovieName());
                Picasso.get().load("https://image.tmdb.org/t/p/w500/" + totalMovieArrayList.get(position)
                        .getMovieImageURL()).into(holder.imageViewRowMovieImage);
        }
        Log.d(TAG, "onBindViewHolder: TOTAL_MOVIE:"+totalMovieArrayList);
    }


    //Todo:RecyclerView row count
    @Override
    public int getItemCount() {

        int count;
        if (totalMovieArrayList ==null){
           count=4;
        }
        else if (!totalMovieArrayList.isEmpty()){
            count= totalMovieArrayList.size();
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
