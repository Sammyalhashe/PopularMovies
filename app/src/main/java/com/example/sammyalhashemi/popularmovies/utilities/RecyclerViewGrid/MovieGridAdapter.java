package com.example.sammyalhashemi.popularmovies.utilities.RecyclerViewGrid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sammyalhashemi.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import data.Movie;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {

    // Tag for Debug
    private final String TAG = "MovieGridAdapter";

    // where all the movies are stored
    private List<Movie> Movies;

    // we use this context to gain access to utils, app resources and layout inflaters
    private final Context mContext;

    // our click handler instance var and the interface it is of type
    private final MovieGridAdapterOnClickHandler mOnClickHandler;


    // click listener to transfer over to detail_activity
    public interface MovieGridAdapterOnClickHandler {
        void onMoviePosterClick(Movie movie);
    }

    public MovieGridAdapter(Context context, MovieGridAdapterOnClickHandler onClickHandler, @Nullable List<Movie> movieList) {
        this.mContext = context;
        this.mOnClickHandler = onClickHandler;
        this.Movies = movieList;
    }

    /* static method calulates the span of the GridLayoutManager (how many items in a row) */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    /* Change the adapter values */

    public void ChangeAdapterValue(List<Movie> newMovies) {
        this.Movies.clear();
        this.Movies = newMovies;
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public MovieGridAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_grid_list_item, parent, false);
        return new MovieGridAdapterViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieGridAdapterViewHolder holder, int position) {
        final Movie movie = this.Movies.get(position);


        /* use picasso to dynamically load the images. This code sample was grabbed directly from their site.
           As they say, it can be done in one line!
         */
//        Log.i(TAG, Movie.getBasePosterPath() + movie.get_RELATIVE_POSTER_PATH());
        Picasso.get().load(Movie.getBasePosterPath() + movie.get_RELATIVE_POSTER_PATH()).into(holder.ivMoviePoster);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.Movies.size();
    }


    class MovieGridAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // ImageView storing the poster for the image in the viewholder
        final ImageView ivMoviePoster;

        MovieGridAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.ivMoviePoster = (ImageView) (ImageView) view.findViewById(R.id.iv_movie_grid_item);
        }


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie clickedMovie = Movies.get(position);
            mOnClickHandler.onMoviePosterClick(clickedMovie);
        }
    }
}