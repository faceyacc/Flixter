package com.example.flixter.models;

import android.util.Log;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.parceler.Parcel;




// Movie Data model

@Parcel
public class Movie   {

    public static final String TAG ="Movie";
    int movieId;
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    int vote_count;
    Double rating;
    String releaseDate;
    String Genre1;
    String Genre2;

    // empty constructor FOR PARCERLER ONLY
    public Movie () {

    }


    // constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        vote_count = jsonObject.getInt("vote_count");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
        releaseDate = jsonObject.getString("release_date");
        // TODO : initialize genre 1 & 2
    }





    // transform from jsonarray to an arrayList. Called in MainActivity
    // creates a new arrayList and returns it
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }


    // getters
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1280/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieId () {
        return movieId;
    }

    public int getVoteCount () {
        return vote_count;
    }

    public boolean isPopular() {
        boolean res = false;
        if(getVoteCount() > 800)
            res = true;
        if(getVoteCount() <= 800)
            res = false;
        return res;
    }

    // TODO : get genres 1 & 2
}
