package com.grs21.movieNotes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.UserMovieTopListActivity;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.TxtFileReader;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class RecyclerViewUserTopMovieListAdapter extends RecyclerView.Adapter<RecyclerViewUserTopMovieListAdapter.TopMovieListVH> {

    ArrayList<Movie> movieArrayList;
    Context context;
    private static final String TAG = "RecyclerViewUserTopMovi";

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

        movieDeleteButton(holder,movie);

    }

    private void movieDeleteButton(TopMovieListVH holder,Movie movie2) {

        holder.deleteMovieImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TxtFileReader txtFileReader=new TxtFileReader();
                ArrayList<String>movieIdInTheFile=txtFileReader.read();
                Log.d(TAG, "onClick: gelen:"+movieIdInTheFile);
                for (int i = 0; i < movieIdInTheFile.size(); i++){
                    if (String.valueOf(movie2.getId()).equals(movieIdInTheFile.get(i))){
                        movieIdInTheFile.remove(i);
                        saveChanges(movieIdInTheFile);
                        RecyclerViewUserTopMovieListAdapter.this.notifyItemRemoved(i);
                        
                        Log.d(TAG, "onClick: sonrakihali:"+movieIdInTheFile);
                    }
                }
            }
        });
    }

    private void saveChanges(ArrayList<String> movieID) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream=context.openFileOutput("movie_id.txt",Context.MODE_PRIVATE);
            for (int i = 0; i < movieID.size(); i++) {

                fileOutputStream.write(" ".getBytes());
                fileOutputStream.write(movieID.get(i).getBytes());
            }
            Log.d(TAG, "saveChanges: yazılan array:"+movieID);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
