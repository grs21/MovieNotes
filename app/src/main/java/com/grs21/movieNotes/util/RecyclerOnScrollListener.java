package com.grs21.movieNotes.util;

import android.widget.AbsListView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean isScrolling= false;
    int currentItem, scrollOutItems, totalItems;

    private int currentPage=1;
    private final LinearLayoutManager myLinearLayoutManager;

    public RecyclerOnScrollListener(LinearLayoutManager linearLayoutManager){
        this.myLinearLayoutManager=linearLayoutManager;
    }


    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
             isScrolling=true;
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        currentItem  = myLinearLayoutManager.getChildCount();
        totalItems = myLinearLayoutManager.getItemCount();
        scrollOutItems = myLinearLayoutManager.findFirstVisibleItemPosition();

        if (isScrolling && (currentItem+scrollOutItems==totalItems)){
            currentPage++;
            isScrolling=false;
            onLoadMore(currentPage);
        }
    }

    public abstract  void onLoadMore(int currentPage);

}
