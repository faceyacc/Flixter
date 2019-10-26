package com.example.flixter;
import java.util.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.models.Movie;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    // API for movie sizes

    public static final String TAG = "MainActivity";


    // member variables
    List<Movie> movies; // arrayList of movies



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();



        // Create an adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);



        // Set up adapater on recycler view
        rvMovies.setAdapter(movieAdapter);


        // Set up layoutManager on recyecler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");

                JSONObject jsonObject = json.jsonObject;
                // a ref var for json object in API data

                try {
                    // gets all values within the results array(object)
                    JSONArray results =  jsonObject.getJSONArray("results");
                    // gets array object called "results" from API data,
                    // is wrapped around try/catch bc object may/may not exist.
                    Log.i(TAG, "Results: " + results.toString()); // just logging movie results
                    movies.addAll( Movie.fromJsonArray(results)); // params: array, returns: arrayList
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies : " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });


    }
}
