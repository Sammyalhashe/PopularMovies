package com.example.sammyalhashemi.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;

import com.example.sammyalhashemi.popularmovies.utilities.NetworkUtils;
import com.example.sammyalhashemi.popularmovies.utilities.RecyclerViewGrid.MovieGridAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Movie;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, MovieGridAdapter.MovieGridAdapterOnClickHandler {

    // holds the main activity layout elements
    private RecyclerView mMovieRecyclerView;
    private MovieGridAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private List<Movie> MoviesList;

    // Loader ID
    private static final int MOVIE_LOADER_ID = 2000;

    private String POPULAR_SORT;
    private String TOP_RATED_SORT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mMovieRecyclerView = findViewById(R.id.movie_grid_rv);
        this.mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.rv_span_count)));
//         this.adapter = new MovieGridAdapter(this, this, new ArrayList<Movie>() );
        this.mProgressBar = findViewById(R.id.pb_loading_indicator);
        // sort order vars
        POPULAR_SORT = getString(R.string.sort_order_popular);
        TOP_RATED_SORT = getString(R.string.sort_order_top_rated);

        this.MoviesList = new ArrayList<>();


        /* Setting fixed size for the RecyclerView improves performance because you
         * you know changes in content will not change child layout sizes in RecyclerView
         *
         */
        this.mMovieRecyclerView.setHasFixedSize(true);
        // this.mMovieRecyclerView.setAdapter(adapter);

        Bundle sortArguments = new Bundle();

        sortArguments.putString(getString(R.string.sort_order_key), POPULAR_SORT);


        LoaderManager loaderManager = getSupportLoaderManager();


        loaderManager.initLoader(MOVIE_LOADER_ID,sortArguments, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                // maybe have
                mProgressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            protected void onStopLoading() {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public String loadInBackground() {
                try {
                    String JSON_String = NetworkUtils.getResponseFromHttpUrl(
                            NetworkUtils.buildURL(
                                    args.getString(
                                            getString(R.string.sort_order_key)
                                    )
                            )
                    );
                    return JSON_String;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context, * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            if (data != null) {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.json_movie_results_array));
                MoviesList.clear();


                for (int i = 0; i < jsonArray.length(); i++) {
                    // individual object from array
                    JSONObject i_object = jsonArray.getJSONObject(i);

                    Movie movie = new Movie(
                            i_object.getString(getString(R.string.json_movie_object_poster_path)),
                            i_object.getString(getString(R.string.json_movie_object_backdrop_path)),
                            i_object.getString(getString(R.string.json_movie_object_title)),
                            i_object.getString(getString(R.string.json_movie_object_original_title)),
                            i_object.getString(getString(R.string.json_movie_object_original_language)),
                            i_object.getString(getString(R.string.json_movie_object_overview)),
                            i_object.getString(getString(R.string.json_movie_object_release_date)),
                            i_object.getInt(getString(R.string.json_movie_object_vote_count)),
                            i_object.getInt(getString(R.string.json_movie_object_id)),
                            i_object.getDouble(getString(R.string.json_movie_object_vote_average)),
                            i_object.getDouble(getString(R.string.json_movie_object_popularity)),
                            i_object.getBoolean(getString(R.string.json_movie_object_video)),
                            i_object.getBoolean(getString(R.string.json_movie_object_adult))
                    );
                    MoviesList.add(movie);


                    // add method to MovieGridAdapter to swap the data Array

                    //this.adapter.ChangeAdapterValue(MoviesList);

                    this.adapter = new MovieGridAdapter(this, this, MoviesList );
                    this.adapter.notifyDataSetChanged();
                    this.mMovieRecyclerView.setAdapter(this.adapter);
                    this.mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onMoviePosterClick(Movie movie) {
        Intent intent = new Intent(getBaseContext(), DetailActivity.class);
        intent.putExtra("movieParcel", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LoaderManager loaderManager = getSupportLoaderManager();
        Bundle sortBundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.menu_popular:
                sortBundle.putString(getString(R.string.sort_order_key), POPULAR_SORT);
                loaderManager.restartLoader(MOVIE_LOADER_ID, sortBundle, this);
                return true;
            case R.id.menu_top:
                sortBundle.putString(getString(R.string.sort_order_key), TOP_RATED_SORT);
                loaderManager.restartLoader(MOVIE_LOADER_ID, sortBundle, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Create menu method executions here */
}
