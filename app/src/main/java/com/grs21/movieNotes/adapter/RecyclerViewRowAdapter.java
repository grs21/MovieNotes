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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewRowAdapter extends RecyclerView.Adapter<RecyclerViewRowAdapter.ViewHolderRowAdapter>{

    private static final String TAG = "RowAdapter";
    private Context context;
    private List<Genres> genresLit;
    private ArrayList<Movie> moviesList;
    private RecyclerViewBoxAdapter recyclerViewBoxAdapter;

    public RecyclerViewRowAdapter(Context context, List<Genres> genresLit, RecyclerViewBoxAdapter recyclerViewBoxAdapter, ArrayList<Movie> moviesList) {
        this.context = context;
        this.genresLit = genresLit;
        this.recyclerViewBoxAdapter=recyclerViewBoxAdapter;
        this.moviesList=moviesList;
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

        Genres genres=genresLit.get(position);
        holder.textViewRowTitle.setText(genres.getName());







        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerViewRow.setLayoutManager(layoutManager);


/*
        for (int i=0;i<=moviesList.size();i++){
                Movie movie=moviesList.get(i);
              for (Integer value:movie.getGenresId()){

                        if (genres.getId()== value){


                        }

              }

        }
*/
        holder.recyclerViewRow.setAdapter(recyclerViewBoxAdapter);


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
