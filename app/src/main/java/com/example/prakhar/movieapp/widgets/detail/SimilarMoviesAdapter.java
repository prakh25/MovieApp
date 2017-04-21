package com.example.prakhar.movieapp.widgets.detail;

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
import timber.log.Timber;

/**
 * Created by Prakhar on 3/6/2017.
 */

public class SimilarMoviesAdapter extends
        RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesViewHolder> {

    private List<Result> resultList;
    private ListInteractionListener interactionListener;

    public SimilarMoviesAdapter(List<Result> movies,
                                ListInteractionListener listInteractionListener) {
        resultList = new ArrayList<>();
        interactionListener = listInteractionListener;
        resultList.addAll(movies);
    }

    @Override
    public SimilarMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);
        return new SimilarMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarMoviesAdapter.SimilarMoviesViewHolder holder, int position) {
        String posterPath = resultList.get(position).getPosterPath();

        String imageUrl = Constants.TMDB_IMAGE_URL + "w185";

        Timber.i(imageUrl);
        String posterUrl = imageUrl + posterPath;

        ViewCompat.setTransitionName(holder.moviePoster, posterPath);

        if (!posterUrl.isEmpty()) {
            Glide.with(holder.listItem.getContext())
                    .load(posterUrl)
                    .placeholder(R.drawable.movie_poster_placeholder)
                    .into(holder.moviePoster);
        }

        holder.title.setText(resultList.get(position).getTitle());

        if (resultList.get(position).getVoteAverage() != 0.0) {
            holder.movieRating.setText(String.format(Locale.US, "%.1f",
                    resultList.get(position).getVoteAverage()));
        } else {
            holder.movieRating.setText("-");
        }
    }

    public Result getItem(int position) {
        return resultList.get(position);
    }

    public void addItems(List<Result> movies) {
        resultList.addAll(movies);
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

    public class SimilarMoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_list_item_card)
        CardView listItemCard;
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.detail_similar_title)
        TextView title;
        @BindView(R.id.movie_list_rating)
        TextView movieRating;

        public final View listItem;

        public SimilarMoviesViewHolder(View itemView) {
            super(itemView);
            listItem = itemView;
            ButterKnife.bind(this, itemView);
            listItemCard.setOnClickListener(v ->
                    interactionListener.onSimilarListItemClick
                            (resultList.get(getAdapterPosition()),
                                    moviePoster, getAdapterPosition(),
                                    moviePoster)
            );
        }
    }

    public interface ListInteractionListener {
        void onSimilarListItemClick(Result result, View sharedElementView, int position,
                                    ImageView sharedImageView);
    }
}
