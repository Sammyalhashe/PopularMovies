package com.example.sammyalhashemi.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    // holds the main activity layout elements
    private RecyclerView mMovieRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mMovieRecyclerView = findViewById(R.id.movie_grid_rv);
        this.mProgressBar = findViewById(R.id.pb_loading_indicator);


        /* Setting fixed size for the RecyclerView improves performance because you
         * you know changes in content will not change child layout sizes in RecyclerView
         *
         */
        this.mMovieRecyclerView.setHasFixedSize(true);



    }
}
