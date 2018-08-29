package com.example.sammyalhashemi.popularmovies;

import android.databinding.DataBindingUtil;
import android.media.Image;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import data.Movie;

import com.example.sammyalhashemi.popularmovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView title;
    private ImageView poster;
    private TextView description;
    private TextView releasedate;
    private TextView runtime;
    private TextView vote_average;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActivityDetailBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        this.poster = (ImageView) findViewById(R.id.activity_detail_image_id);
        this.title = (TextView) findViewById(R.id.activity_detail_movie_title);
        this.description = (TextView) findViewById(R.id.activity_detail_movie_description);
        this.releasedate = (TextView) findViewById(R.id.releaseDate);
        this.vote_average = (TextView) findViewById(R.id.voteAverage);
        this.runtime = (TextView) findViewById(R.id.runtime);

        Movie incoming_movie = getIntent().getParcelableExtra("movieParcel");

        Picasso.get().load(Movie.getBasePosterPath() + incoming_movie.get_RELATIVE_POSTER_PATH()).into(this.poster);
        binding.setMovie(incoming_movie);
//        this.title.setText(incoming_movie.get_title());
        this.description.setText(incoming_movie.get_overview());
        this.releasedate.setText(incoming_movie.get_release_date());
        this.vote_average.setText(String.format("%s/%d",incoming_movie.get_vote_average(), 10));
        Toast.makeText(this,  incoming_movie.get_original_title().toString(), Toast.LENGTH_SHORT).show();
    }

}
