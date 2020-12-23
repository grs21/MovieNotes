package com.grs21.movieNotes.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.MovieDetailActivity;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.TxtFileReader;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public  class RecyclerViewChildAdapter extends RecyclerView.Adapter<RecyclerViewChildAdapter.ViewHolderBoxAdapter> {


    private ArrayList<Movie> movieArrayList;
    private static final String TAG = "BoxAdapter";
    private static RecyclerViewChildAdapter instance;
    private Context context;
    private static final String MOVIE_NAME="movie_id.txt";
    public static final String FILE_ROOT="/data/user/0/com.grs21.movienotes/files/movie_id.txt";


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

        Movie movie=movieArrayList.get(position);
        Log.d(TAG, "onBindViewHolder: MOVIE_ARRAY_LIST:"+movieArrayList.get(position));
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieArrayList.get(position)
                        .getMoviePosterImageURL()).into(holder.imageViewRowMovieImage);

        holder.imageViewRowMovieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(), MovieDetailActivity.class);
                Movie movie=movieArrayList.get(position);
                intent.putExtra("movie",movie);
                v.getContext().startActivity(intent);
            }
        });
        movieAddButtonListener(holder,movie);

    }

    private void movieAddButtonListener(ViewHolderBoxAdapter holder,Movie movie) {

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo:write to local file
                boolean status=true;
                TxtFileReader txtFileReader=new TxtFileReader();
                for (String idController:txtFileReader.read()) {

                    if (String.valueOf(movie.getId()).equals(idController)){

                        status=false;
                    }

                }
                    if (status){
                        FileOutputStream fileOutputStream=null;

                        try {

                            fileOutputStream=v.getContext().openFileOutput("movie_id.txt",Context.MODE_APPEND);

                            fileOutputStream.write(" ".getBytes());
                            fileOutputStream.write(movie.getId().toString().getBytes());
                            Log.d(TAG, "onClick: "+movie.getId());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (fileOutputStream!=null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }else Toast.makeText(context, "THIS FILM HAS ALREADY BEEN RECORDED", Toast.LENGTH_SHORT).show();




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
        ImageButton imageButton;


        public ViewHolderBoxAdapter(@NonNull View itemView) {
            super(itemView);
            imageViewRowMovieImage=itemView.findViewById(R.id.item_image);
            imageButton=itemView.findViewById(R.id.mainMovieAddButton);


        }
    }
}
