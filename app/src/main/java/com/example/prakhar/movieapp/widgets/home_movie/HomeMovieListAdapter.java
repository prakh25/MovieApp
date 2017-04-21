package com.example.prakhar.movieapp.widgets.home_movie;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.home.movie.Result;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 2/24/2017.
 */

public class HomeMovieListAdapter extends
        RecyclerView.Adapter<HomeMovieListAdapter.MovieWrapperViewHolder> {

    private List<Result> resultList;
    private ListInteractionListener interactionListener;

    public HomeMovieListAdapter(List<Result> results,
                                ListInteractionListener listInteractionListener) {

        resultList = new ArrayList<>();
        interactionListener = listInteractionListener;
        resultList.addAll(results);
    }

    @Override
    public MovieWrapperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);
        return new MovieWrapperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeMovieListAdapter.MovieWrapperViewHolder holder, int position) {

        String posterPath = resultList.get(position).getPosterPath();

        String imageUrl = Constants.TMDB_IMAGE_URL + "w185";

        String posterUrl = imageUrl + posterPath;

        if (!posterUrl.isEmpty()) {
            Glide.with(holder.listItem.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.movie_poster_placeholder)
                    .crossFade()
                    .into(holder.moviePoster);
        }

        ViewCompat.setTransitionName(holder.moviePoster, posterPath);

        holder.title.setText(resultList.get(position).getTitle());

        if (resultList.get(position).getVoteAverage() != 0.0) {
            holder.movieRating.setText(String.format(Locale.US, "%.1f",
                    resultList.get(position).getVoteAverage()));
        } else {
            holder.movieRating.setText("-");
        }
    }

    public void addItems(List<Result> results) {
        resultList.addAll(results);
        notifyItemRangeInserted(getItemCount(), resultList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MovieWrapperViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_list_item_card)
        CardView listItemCard;
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.detail_similar_title)
        TextView title;
        @BindView(R.id.movie_list_rating)
        TextView movieRating;

        public final View listItem;

        public MovieWrapperViewHolder(View itemView) {
            super(itemView);
            listItem = itemView;
            ButterKnife.bind(this, itemView);

            listItemCard.setOnClickListener(v ->
                    interactionListener.onListItemClick
                            (resultList.get(getAdapterPosition()), getAdapterPosition(),
                                    moviePoster)
            );
        }
    }

    public interface ListInteractionListener {
        void onListItemClick(Result result, int position, ImageView poster);
    }
}
