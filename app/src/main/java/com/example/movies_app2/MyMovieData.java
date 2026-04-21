package com.example.movies_app2;

public class MyMovieData {
    private String movieName;
    private String moviDate;
    private Integer movieImage;

    public MyMovieData(String movieName, String moviDate, Integer movieImage) {
        this.movieName = movieName;
        this.moviDate = moviDate;
        this.movieImage = movieImage;
    }

    public String getMoviDate() {
        return moviDate;
    }

    public String getMovieName() {
        return movieName;
    }

    public Integer getMovieImage() {
        return movieImage;
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
}
