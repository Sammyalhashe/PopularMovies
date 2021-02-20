package com.example.sammyalhashemi.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sammyalhashemi.popularmovies.BuildConfig;
import com.example.sammyalhashemi.popularmovies.MainActivity;
import com.example.sammyalhashemi.popularmovies.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.MainContract;
import data.Movie;
import data.MovieRepo;
import data.MovieReviewRepo;
import data.MovieTrailerRepo;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieNetworkUtil {
    private static final String TAG = "MovieNetworkUtils";
    private MovieClientRetrofit request;
    private static MovieNetworkUtil sInstance;
    private final long cacheSize = (5 * 1024 * 1024);
    private Context applicationContext;

    private boolean hasNetwork(Context context) {
        boolean isConnected = false; // initial value
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        try {
           activeNetwork = connectivityManager.getActiveNetworkInfo();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }

        if (activeNetwork != null && activeNetwork.isConnected()) {
            isConnected = true;
        }

        return isConnected;


    }

    private MovieNetworkUtil(final Context context) {
        this.applicationContext = context;
        Cache cache = new Cache(this.applicationContext.getCacheDir(), cacheSize);


        // retrofit bootstrap
        // use retrofit's builder to setup what URL it requests
        // as well as the serializer and deserializer (the converter Factory)
        // Also setup caching to store results
        // followed tutorial: https://medium.com/mindorks/caching-with-retrofit-store-responses-offline-71439ed32fda
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (hasNetwork(context)) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                        } else {
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + (60 * 60 * 24 * 7)).build();
                        }
                        return chain.proceed(request);
                    }
                }).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(MainContract.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);
        Retrofit retrofit = builder.build();
        this.request = retrofit.create(MovieClientRetrofit.class);
    }

    public static synchronized MovieNetworkUtil getInstance(Context context) {
        if (sInstance == null) sInstance = new MovieNetworkUtil(context);
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

        listener.toggleProgressBar();
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
                listener.toggleProgressBar();
                listener.updateUI(movies);
            }

            @Override
            public void onFailure(Call<MovieRepo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getReviewsForMovie(String id, final Listeners.MovieReviewResponseListener listener) {
        Call<MovieReviewRepo> call = this.request.getReviewsForMovie(id, BuildConfig.MOVIE_API_KEY, "en-US");

        call.enqueue(new Callback<MovieReviewRepo>() {
            @Override
            public void onResponse(Call<MovieReviewRepo> call, Response<MovieReviewRepo> response) {
                MovieReviewRepo repo = response.body();
                List<MovieReviewRepo.MovieReview> movieReviews = repo.getResults();
                for (MovieReviewRepo.MovieReview review: movieReviews) {
                    Log.d(TAG,"Review: " + review.getContent());
                }
                listener.addReviews(movieReviews);
            }

            @Override
            public void onFailure(Call<MovieReviewRepo> call, Throwable t) {

            }
        });
    }

    public void getTrailersForMovie(String id, final Listeners.MovieTrailerResponseListener listener) {
        Call<MovieTrailerRepo> call = this.request.getTrailersForMovie(
                id,
                BuildConfig.MOVIE_API_KEY,
                this.applicationContext
                            .getResources()
                            .getString(R.string.language)
        );

        call.enqueue(new Callback<MovieTrailerRepo>() {
            @Override
            public void onResponse(Call<MovieTrailerRepo> call, Response<MovieTrailerRepo> response) {
                MovieTrailerRepo repo = response.body();
                List<MovieTrailerRepo.MovieTrailer> trailers = repo.getResults();
                for (MovieTrailerRepo.MovieTrailer trailer: trailers) {
                    Log.d(TAG, "Trailer: " + trailer.getName());
                }
                listener.addTrailers(trailers);
            }

            @Override
            public void onFailure(Call<MovieTrailerRepo> call, Throwable t) {

            }
        });
    }
}
