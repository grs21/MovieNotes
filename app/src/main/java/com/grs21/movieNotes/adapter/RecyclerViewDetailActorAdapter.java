package com.grs21.movieNotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.grs21.movieNotes.R;
import com.grs21.movieNotes.model.Actor;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class RecyclerViewDetailActorAdapter extends RecyclerView.Adapter<RecyclerViewDetailActorAdapter.ActorVH> {
    ArrayList<Actor> actorArrayList;
    public RecyclerViewDetailActorAdapter(ArrayList<Actor> actorArrayList){
        this.actorArrayList=actorArrayList;
    }
    @NonNull
    @Override
    public ActorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_recyclerview_actor_item
                ,parent,false);
        return new ActorVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ActorVH holder, int position) {

        holder.textView.setText(actorArrayList.get(position).getName());
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+actorArrayList.get(position)
                    .getActorPosterURl()).into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return actorArrayList.size();
    }
    public class ActorVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ActorVH(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.actor_image);
            textView=itemView.findViewById(R.id.actor_name_textView);
        }
    }
}
