package com.example.sammyalhashemi.popularmovies.utilities.RecyclerViewGrid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sammyalhashemi.popularmovies.R;

import java.util.List;

import data.MovieReviewRepo;

public class TrailerReviewLinearAdapter extends RecyclerView.Adapter<TrailerReviewLinearAdapter.TrailerReviewViewHolder> {

    // TAG for TrailerReviewLinearAdapter
    private final String TAG = "TrailerReviewLinearAdapter";

    // Where all the reviews for the movie are stored
    private List<MovieReviewRepo.MovieReview> movieReviewList;

    // Context
    private final Context context;

    public TrailerReviewLinearAdapter(Context context, List<MovieReviewRepo.MovieReview> movieReviewList) {
        this.context = context;
        this.movieReviewList = movieReviewList;
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
    public TrailerReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_review_list_item, parent, false);
        return new TrailerReviewViewHolder(view);
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
    public void onBindViewHolder(@NonNull TrailerReviewViewHolder holder, int position) {
        final String review = this.movieReviewList.get(position).getContent();
        holder.content.setText(review);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.movieReviewList.size();
    }

    public interface TrailerReviewOnClickHandler {
        void onClick(boolean Trailer_or_Review);
    }

    public void changeAdapterValue(List<MovieReviewRepo.MovieReview> newMovieReviews) {
        this.movieReviewList.clear();
        this.movieReviewList = newMovieReviews;
    }

    class TrailerReviewViewHolder extends RecyclerView.ViewHolder {

        final TextView content;
//        final String key;

        public TrailerReviewViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            this.content =  itemView.findViewById(R.id.reviewContent);
        }
    }


}
