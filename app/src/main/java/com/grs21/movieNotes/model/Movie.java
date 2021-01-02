package com.grs21.movieNotes.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Movie implements Serializable {
    private  Integer id;
    private String rank;
    private String movieName;
    private String releaseDate;
    private String movieBackdropPathImageUrl;
    private ArrayList<String> movieGenres=new ArrayList<>();
    private String moviePosterImageURL;

    public ArrayList<String> getMovieGenres() {
        return movieGenres;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getMovieBackdropPathImageUrl() {
        return movieBackdropPathImageUrl;
    }
    public void setMovieBackdropPathImageUrl(String movieBackdropPathImageUrl) {
        this.movieBackdropPathImageUrl = movieBackdropPathImageUrl;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMoviePosterImageURL() {
        return moviePosterImageURL;
    }
    public void setMoviePosterImageURL(String moviePosterImageURL) {
        this.moviePosterImageURL = moviePosterImageURL;
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
    @Override
    public String toString() {
        return  movieName ;
    }
}
