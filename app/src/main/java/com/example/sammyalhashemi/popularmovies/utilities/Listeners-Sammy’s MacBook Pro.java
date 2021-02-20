package com.example.sammyalhashemi.popularmovies.utilities;

import java.util.ArrayList;
import java.util.List;

import data.Movie;
import data.MovieReviewRepo;
import data.MovieTrailerRepo;

public class Listeners {
    public interface MovieResponseListener {
        void updateUI(ArrayList<Movie> movies);
        void toggleProgressBar();
    }

    public interface MovieReviewResponseListener {
        void addReviews(List<MovieReviewRepo.MovieReview> reviews);
    }

    public interface MovieTrailerResponseListener {
        void addTrailers(List<MovieTrailerRepo.MovieTrailer> trailers);
    }
}
