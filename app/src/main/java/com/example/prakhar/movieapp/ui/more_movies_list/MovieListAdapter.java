package com.example.prakhar.movieapp.ui.more_movies_list;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.more_movie_list.MovieListResult;
import com.example.prakhar.movieapp.model.home.movie.Result;
import com.example.prakhar.movieapp.utils.Constants;
import com.example.prakhar.movieapp.utils.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Prakhar on 3/14/2017.
 */

class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MovieListResult> movieListResults;

    private static final int VIEW_TYPE_LIST = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    @IntDef({VIEW_TYPE_LOADING, VIEW_TYPE_LIST})
    @Retention(RetentionPolicy.SOURCE)
    private  @interface ViewType {
    }

    @ViewType
    private int viewType;

    private boolean includeWeekendGross = false;

    private ListInteractionListener listInteractionListener;

    MovieListAdapter(boolean revenue) {
        movieListResults = new ArrayList<>();
        viewType = VIEW_TYPE_LIST;
        listInteractionListener = null;
        includeWeekendGross = revenue;
    }

    @Override
    public int getItemViewType(int position) {
        return movieListResults.get(position) == null ?
                VIEW_TYPE_LOADING : movieListResults.get(position).getResult().getId();
    }

    @Override
    public long getItemId(int position) {
        return movieListResults.size() >= position ?
                movieListResults.get(position).getResult().getId() : -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return onIndicationViewHolder(parent);
        }
        return onGenericViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADING) {
            return; // no-op
        }
        onBindGenericItemViewHolder((MovieViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return movieListResults.size();
    }

    private RecyclerView.ViewHolder onIndicationViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_movie_item, parent, false);
        return new ProgressViewHolder(view);
    }

    private RecyclerView.ViewHolder onGenericViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.more_movies_item, parent, false);
        return new MovieViewHolder(view);
    }

    private void onBindGenericItemViewHolder(final MovieViewHolder holder, int position) {

        String posterUrl = Constants.TMDB_IMAGE_URL + "w154" +
                movieListResults.get(position).getResult().getPosterPath();

        Glide.with(holder.itemView.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.empty_image)
                .into(holder.poster);

        ViewCompat.setTransitionName(holder.poster,
                movieListResults.get(position).getResult().getPosterPath());

        if (!movieListResults.get(position).getResult().getReleaseDate().isEmpty() &&
                !movieListResults.get(position).getResult().getReleaseDate().equals("")) {

            int year = Utils.getReleaseYear(movieListResults.get(position).getResult().getReleaseDate());

            holder.title.setText(String.format(Locale.US, "%d. %s (%d)",
                    position + 1, movieListResults.get(position).getResult().getTitle(),
                    year));
        } else {
            holder.title.setText(String.format(Locale.US, "%d. %s",
                    position + 1, movieListResults.get(position).getResult().getTitle()));
        }

        holder.tmdbRating.setText(String.format(Locale.US, "%.1f",
                movieListResults.get(position).getResult().getVoteAverage()));

        if(movieListResults.get(position).getUserRating() == 0) {
            holder.userRating.setText("-");
        } else {
            holder.userRating.setText(String.format(Locale.US,"%d",
                    movieListResults.get(position).getUserRating()));
        }

        if (movieListResults.get(position).isAddedToWatchlist()) {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.bookmark_check_green)
                    .into(holder.watchlist);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.bookmark_plus_outline)
                    .into(holder.watchlist);
        }

        if (movieListResults.get(position).isMarkedAsFavorite()) {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.heart)
                    .into(holder.favorite);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.heart_outline)
                    .into(holder.favorite);
        }

        if(includeWeekendGross) {
            holder.boxOfficeGross.setText(String.format(Locale.US, "Weekend Gross: %s",
                    Utils.currencyConverter(movieListResults.get(position).getRevenue())));
            holder.boxOfficeGross.setVisibility(View.VISIBLE);
            holder.ratingsFrame.setVisibility(View.GONE);
        }
    }

    public void add(MovieListResult item) {
        add(null, item);
    }

    public void add(@Nullable Integer position, MovieListResult item) {
        if (position != null) {
            movieListResults.add(position, item);
            notifyItemInserted(position);
        } else {
            movieListResults.add(item);
            notifyItemInserted(movieListResults.size() - 1);
        }
    }

    public void addItems(List<MovieListResult> movieListResults) {
        this.movieListResults.addAll(movieListResults);
        notifyItemRangeInserted(getItemCount(), this.movieListResults.size() - 1);
    }

    void updateItem(boolean watchListStatus,boolean favoriteStatus,
                           int userRating, int position) {
        movieListResults.get(position).setAddedToWatchlist(watchListStatus);
        movieListResults.get(position).setMarkedAsFavorite(favoriteStatus);
        movieListResults.get(position).setUserRating(userRating);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        if (movieListResults.size() < position) {
            Timber.d("The item at position: " + position + " doesn't exist");
            return;
        }
        movieListResults.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        movieListResults.clear();
        notifyDataSetChanged();
    }

    boolean addLoadingView() {
        if (getItemViewType(movieListResults.size() - 1) != VIEW_TYPE_LOADING) {
            add(null);
            return true;
        }
        return false;
    }

    boolean removeLoadingView() {
        if (movieListResults.size() > 1) {
            int loadingViewPosition = movieListResults.size() - 1;
            if (getItemViewType(loadingViewPosition) == VIEW_TYPE_LOADING) {
                remove(loadingViewPosition);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(@ViewType int viewType) {
        this.viewType = viewType;
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_progress_bar)
        ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_more)
        CardView cardView;
        @BindView(R.id.more_movie_poster)
        ImageView poster;
        @BindView(R.id.more_movie_title)
        TextView title;
        @BindView(R.id.more_tmdb_rating)
        TextView tmdbRating;
        @BindView(R.id.more_user_rating)
        TextView userRating;
        @BindView(R.id.weekend_box_office)
        TextView boxOfficeGross;
        @BindView(R.id.more_watchlist)
        ImageView watchlist;
        @BindView(R.id.more_favorite)
        ImageView favorite;
        @BindView(R.id.tmdb_rating_frame)
        FrameLayout tmdbFrame;
        @BindView(R.id.user_rating_frame)
        FrameLayout userFrame;
        @BindView(R.id.ratings_frame)
        LinearLayout ratingsFrame;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cardView.setOnClickListener(v ->
                    listInteractionListener.onItemClick(
                            movieListResults.get(getAdapterPosition()).getResult(),
                            getAdapterPosition(), poster)
            );

        }
    }

    public interface ListInteractionListener {
        void onItemClick(Result result, int clickedPosition, ImageView sharedImageView);
    }

    void setListInteractionListener(ListInteractionListener interactionListener) {
        listInteractionListener = interactionListener;
    }
}
