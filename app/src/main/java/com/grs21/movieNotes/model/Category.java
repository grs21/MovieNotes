package com.grs21.movieNotes.model;


import java.util.ArrayList;

public class Category {


    private String categoryTitle;

    ArrayList<Movie> movieArrayList;

    public Category() {
    }

    public Category(String categoryTitle, ArrayList<Movie> movieArrayList) {

        this.categoryTitle = categoryTitle;
        this.movieArrayList = movieArrayList;
    }

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
    }



    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    @Override
    public String toString() {
        return "\n"+categoryTitle+": "+movieArrayList;
    }
}
