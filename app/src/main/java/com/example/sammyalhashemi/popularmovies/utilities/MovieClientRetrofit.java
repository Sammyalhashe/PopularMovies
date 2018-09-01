package com.example.sammyalhashemi.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import data.MovieRepo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieClientRetrofit {

    @GET("movie/{sort}")
    Call<MovieRepo> getMoviesOfSort(@Path("sort") String SORT, @Query("api_key") String API_KEY, @Query("language") String language);
}
