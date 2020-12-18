package com.grs21.movieNotes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {

    private  Integer id;
    private String rank;
    private String movieName;
    private String movieDirector;
    private String releaseDate;
    private String movieBackdropPathImageUrl;
    private List movieActor;
    private ArrayList<String> movieGenres=new ArrayList<>();
    private String moviePosterImageURL;
    public  ArrayList<Integer> moviesSaveArrayList =new ArrayList<>();


    public ArrayList<String> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(ArrayList<String> movieGenres) {
        this.movieGenres = movieGenres;
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

    public void setMoviesSaveArrayList(ArrayList<Integer> moviesSaveArrayList) {
        this.moviesSaveArrayList = moviesSaveArrayList;
    }

    public ArrayList<Integer> getMoviesSaveArrayList() {
        return moviesSaveArrayList;
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
        return  movieName +" "+ moviesSaveArrayList +'\'';
    }
}
