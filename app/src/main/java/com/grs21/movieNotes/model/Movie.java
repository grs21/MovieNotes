package com.grs21.movieNotes.model;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    private  Integer id;
    private String movieId;
    private String movieName;
    private String movieDirector;
    private List movieActor;

    public Movie( String movieId, String movieDirector, List movieActor,String movieName) {

        this.movieName=movieName;
        this.movieId = movieId;
        this.movieDirector = movieDirector;
        this.movieActor = movieActor;
    }
    public Movie (String movieName){
        this.movieName=movieName;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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
}
