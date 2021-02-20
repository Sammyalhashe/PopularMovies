package com.example.sammyalhashemi.popularmovies.utilities;

import android.util.Log;

import com.example.sammyalhashemi.popularmovies.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import data.MainContract;
import data.Movie;
import data.MovieRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieTrailersNetworkUtil {
    private static final String TAG = "MovieNetworkUtils";
    private MovieClientRetrofit request;
    private static MovieTrailersNetworkUtil sInstance;

    private MovieTrailersNetworkUtil() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(MainContract.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        this.request = retrofit.create(MovieClientRetrofit.class);
    }

    public static synchronized MovieTrailersNetworkUtil getInstance() {
        if (sInstance == null) sInstance = new MovieTrailersNetworkUtil();
        return sInstance;
    }


    public void getMoviesFromAPI(String sort, final Listeners.MovieResponseListener listener) {
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(MainContract.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.build();
//
//        MovieClientRetrofit movieClientRetrofit = retrofit.create(MovieClientRetrofit.class);
        Call<MovieRepo> call = this.request.getMoviesOfSort(sort, BuildConfig.MOVIE_API_KEY, "en-US");


        call.enqueue(new Callback<MovieRepo>() {
            @Override
            public void onResponse(Call<MovieRepo> call, Response<MovieRepo> response) {
                MovieRepo repo = response.body();
                Log.d(TAG, repo.getResults().toString());
                Log.d(TAG, repo.getTotal_pages());
                ArrayList<Movie> movies = repo.getResults();
                for (Movie movie : movies) {
                    Log.d(TAG, movie.get_overview());
                }
                listener.updateUI(movies);
            }

            @Override
            public void onFailure(Call<MovieRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
