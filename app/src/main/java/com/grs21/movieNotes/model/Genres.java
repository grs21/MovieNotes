package com.grs21.movieNotes.model;

import java.util.ArrayList;

public class Genres {

    private Integer id;
    private String name;
    public  ArrayList<Movie> getMovieArrayList;






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name +" "+ id ;
    }

}
