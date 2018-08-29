package com.example.sammyalhashemi.popularmovies.utilities;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;


import com.example.sammyalhashemi.popularmovies.BuildConfig;
import com.example.sammyalhashemi.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import data.MainContract;


public final class NetworkUtils {



    private static final String QUERY_VAL = "api_key";
    private static final String API_KEY = BuildConfig.MOVIE_API_KEY;
    private static final String NETWORK_TAG = "NETWORK_UTILS";

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    // I admit this was straight up copied from the challenge course
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildURL(String movieOrder) {

        // Initialize url
        URL url = null;
        Uri uri = null;

        // using the base uri, build a uri by simpling adding the query params
        switch (movieOrder) {
            case ("popular"): {
                uri = Uri.parse(MainContract.BASE_URL_POPULAR)
                        .buildUpon()
                        .appendQueryParameter(QUERY_VAL, API_KEY)
                        .appendQueryParameter("language", "en-US")
                        .build();
                break;
            }
            case ("top_rated"): {
                uri = Uri.parse(MainContract.BASE_URL_TOP_RATED)
                        .buildUpon()
                        .appendQueryParameter(QUERY_VAL, API_KEY)
                        .appendQueryParameter("language", "en-US")
                        .build();
                break;
            }
            default:
                Log.d(NETWORK_TAG, Resources.getSystem().getString(R.string.sort_order_not_valid));
                return null;
        }


        // convert the uri to a url with some exception handling
        try {
            url = new URL(uri.toString());
            Log.d(NETWORK_TAG,url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        return url;
    }

}