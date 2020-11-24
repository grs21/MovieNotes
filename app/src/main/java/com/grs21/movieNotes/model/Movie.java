package com.grs21.movieNotes.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    private  Integer id;
    private String rank;
    private String movieName;
    private String movieDirector;
    private String movieYear;
    private List movieActor;

    public Movie( String movieDirector, List movieActor,String movieName) {

        this.movieName=movieName;
        this.movieDirector = movieDirector;
        this.movieActor = movieActor;
    }
    public Movie(){

    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public List getMovieActor() {
        return movieActor;
    }

    public void setMovieActor(List movieActor) {
        this.movieActor = movieActor;
    }

    @Override
    public String toString() {
        return  movieName + '\'';
    }
}
