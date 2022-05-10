package com.example.movierec;

public class Movie {
    String  poster ,title,overview,genres;
    Double rating;

    public Movie (String poster ,String title , String overview , String genres , Double rating){
        this.genres = genres;
        this.overview = overview;
        this.poster = poster;
        this.title = title;
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getGenres() {
        return genres;
    }

    public Double getRating() {
        return rating;
    }
}
