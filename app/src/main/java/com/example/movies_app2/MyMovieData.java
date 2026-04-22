package com.example.movies_app2;

public class MyMovieData {

    private int movieId;
    private String movieName;
    private String moviDate;
    private Integer movieImage;

    private String movieDescription;

    public MyMovieData(int movieId, String movieName, String moviDate, Integer movieImage, String movieDescription) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.moviDate = moviDate;
        this.movieImage = movieImage;
        this.movieDescription = movieDescription;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMoviDate() {
        return moviDate;
    }

    public Integer getMovieImage() {
        return movieImage;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMoviDate(String moviDate) {
        this.moviDate = moviDate;
    }

    public void setMovieImage(Integer movieImage) {
        this.movieImage = movieImage;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }
}