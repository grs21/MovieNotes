package com.grs21.movieNotes.model;

public class Actor {

    private String name;
    private String actorPosterURl;


    public Actor(String name, String actorPosterURl) {
        this.name = name;
        this.actorPosterURl = actorPosterURl;
    }


    public String getActorPosterURl() {
        return actorPosterURl;
    }

    public String getName() {
        return name;
    }
}
