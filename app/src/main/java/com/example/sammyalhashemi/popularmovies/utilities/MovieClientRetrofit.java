package com.example.sammyalhashemi.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import data.MovieRepo;
import data.MovieReviewRepo;
import data.MovieTrailerRepo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieClientRetrofit {

    @GET("movie/{sort}")
    Call<MovieRepo> getMoviesOfSort(@Path("sort") String SORT, @Query("api_key") String API_KEY, @Query("language") String language);

    @GET("movie/{id}/reviews")
    Call<MovieReviewRepo> getReviewsForMovie(@Path("id") String ID, @Query("api_key") String API_KEY, @Query("language") String language);

    @GET("movie/{id}/videos")
    Call<MovieTrailerRepo> getTrailersForMovie(@Path("id") String ID, @Query("api_key") String API_KEY, @Query("language") String language);
}
