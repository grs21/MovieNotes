package com.grs21.movieNotes.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grs21.movieNotes.R;
import com.grs21.movieNotes.activity.MainActivity;
import com.grs21.movieNotes.model.Movie;

import java.util.ArrayList;

public class RecyclerViewSearchViewAdapter extends  RecyclerView.Adapter<RecyclerViewSearchViewAdapter.SearchVH>{

    ArrayList<Movie> movieArrayList;

    public RecyclerViewSearchViewAdapter(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new SearchVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
            holder.textView.setText(movieArrayList.get(position).getMovieName());
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class SearchVH extends RecyclerView.ViewHolder{

        TextView textView;
        public SearchVH(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(android.R.id.text1);
        }
    }
}
