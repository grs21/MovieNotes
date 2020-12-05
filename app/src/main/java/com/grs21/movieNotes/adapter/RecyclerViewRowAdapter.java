package com.grs21.movieNotes.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.MainActivity;
import com.grs21.movieNotes.model.Genres;
import com.grs21.movieNotes.model.Movie;
import com.grs21.movieNotes.util.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRowAdapter extends RecyclerView.Adapter<RecyclerViewRowAdapter.ViewHolderRowAdapter>{

    private static final String TAG = "RowAdapter";
    public static final String JSON_DATA_URL="https://api.themoviedb.org/3/movie/popular?api_key=e502c799007bd295e5f591cb3ae8fb46&language=en-US&page=%d";
    private Context context;
    private List<String> genresLit;
    private RecyclerViewBoxAdapter recyclerViewBoxAdapter;

    private ArrayList<Movie> movieArrayList;
    private ArrayList<Movie> popularMovieList=new ArrayList<>();
    private ArrayList<Movie> topRateMovieList=new ArrayList<>();
    private ArrayList<Movie> upComingMovieList=new ArrayList<>();
    private ArrayList<Movie> nowPlayingList=new ArrayList<>();

    public RecyclerViewRowAdapter(Context context, List<String> genresLit,ArrayList<Movie>movieArrayList) {
        this.movieArrayList=movieArrayList;
        this.context = context;
        this.genresLit = genresLit;
    }

    public RecyclerViewRowAdapter(Context context, List<String> genresLit) {
        this.context = context;
        this.genresLit = genresLit;

    }

    @NonNull
    @Override
    public ViewHolderRowAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_row_layout,parent,false);

        return new ViewHolderRowAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRowAdapter holder, int position) {


        holder.textViewRowTitle.setText(genresLit.get(position));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Log.d(TAG, "onBindViewHolder: MovieArrayList"+position+movieArrayList);

        for (int i=0;i<20;i++){
            popularMovieList.add(i,movieArrayList.get(i));
            Log.d(TAG, "onBindViewHolder: POPULAR"+popularMovieList);


        }for (int a=20;a<40;a++){

            topRateMovieList.add(movieArrayList.get(a));
            Log.d(TAG, "onBindViewHolder: POPULAR2"+topRateMovieList);
        }
        for (int b=40;b<60;b++){
            nowPlayingList.add(movieArrayList.get(b));
            Log.d(TAG, "onBindViewHolder: POPULAR3"+nowPlayingList);

        }
        for (int c=60;c<80;c++){
            upComingMovieList.add(movieArrayList.get(c));
            Log.d(TAG, "onBindViewHolder: POPULAR4"+upComingMovieList);
        }

      switch (position) {

          case 0:
              recyclerViewBoxAdapter = new RecyclerViewBoxAdapter(popularMovieList);
              holder.recyclerViewRow.setAdapter(recyclerViewBoxAdapter);
              holder.recyclerViewRow.setLayoutManager(layoutManager);
              break;
          case 1:
              recyclerViewBoxAdapter=new RecyclerViewBoxAdapter(topRateMovieList);
              holder.recyclerViewRow.setAdapter(recyclerViewBoxAdapter);
              holder.recyclerViewRow.setLayoutManager(layoutManager);
              break;
          case 2:
              recyclerViewBoxAdapter=new RecyclerViewBoxAdapter(nowPlayingList);
              holder.recyclerViewRow.setAdapter(recyclerViewBoxAdapter);
              holder.recyclerViewRow.setLayoutManager(layoutManager);
              break;
          case 3:
              recyclerViewBoxAdapter=new RecyclerViewBoxAdapter(upComingMovieList);
              holder.recyclerViewRow.setAdapter(recyclerViewBoxAdapter);
              holder.recyclerViewRow.setLayoutManager(layoutManager);
              break;
      }


        //holder.recyclerViewRow.setLayoutManager(layoutManager);
        //holder.recyclerViewRow.setAdapter(recyclerViewBoxAdapter);
/*
       holder.recyclerViewRow.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {



            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return genresLit.size();
    }

    public class ViewHolderRowAdapter extends RecyclerView.ViewHolder {

        TextView textViewRowTitle;
        RecyclerView recyclerViewRow;

        public ViewHolderRowAdapter(@NonNull View itemView) {
            super(itemView);
            textViewRowTitle=itemView.findViewById(R.id.editTextRowTitle);
            recyclerViewRow=itemView.findViewById(R.id.recyclerViewRow);
        }
    }


}
