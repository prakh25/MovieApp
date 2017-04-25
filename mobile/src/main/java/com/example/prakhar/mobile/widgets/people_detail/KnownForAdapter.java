package com.example.prakhar.mobile.widgets.people_detail;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.core.model.person_search.KnownFor;
import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/4/2017.
 */

public class KnownForAdapter extends BaseAdapter {

    @BindView(R.id.people_known_for_card)
    CardView movieCard;
    @BindView(R.id.movie_know_for_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_known_for_title)
    TextView movieTitle;
    @BindView(R.id.movie_known_for_rating)
    TextView movieRating;

    private List<KnownFor> knownForList;
    private KnownForListener listener;

    KnownForAdapter(List<KnownFor> knownFors, KnownForListener knownForListener) {
        knownForList = new ArrayList<>();
        knownForList.addAll(knownFors);
        listener = knownForListener;
    }

    @Override
    public int getCount() {
        return knownForList.size();
    }

    @Override
    public Object getItem(int position) {
        return knownForList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.people_credits_acting_item, null);

            ButterKnife.bind(this, convertView);

            final ViewHolder viewHolder =
                    new ViewHolder(movieCard, moviePoster, movieTitle, movieRating);

            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        Glide.with(moviePoster.getContext())
                .load(Constants.TMDB_IMAGE_URL + "w185" + knownForList.get(position).getPosterPath())
                .placeholder(R.drawable.movie_poster_placeholder)
                .into(viewHolder.moviePoster);

        ViewCompat.setTransitionName(viewHolder.moviePoster,
                knownForList.get(position).getPosterPath());

        if (knownForList.get(position).getMediaType().equals("movie")) {
            movieTitle.setText(knownForList.get(position).getTitle());
        } else {
            movieTitle.setText(knownForList.get(position).getName());
        }

        if (knownForList.get(position).getVoteAverage() != 0.0) {
            movieRating.setText(String.format(Locale.US, "%.1f",
                    knownForList.get(position).getVoteAverage()));
        } else {
            movieRating.setText("-");
        }

        movieCard.setOnClickListener(v -> listener.knownForMovieClicked(
                knownForList.get(position).getId(), position, viewHolder.moviePoster));

        return convertView;
    }

    private class ViewHolder {
        CardView movieCard;
        ImageView moviePoster;
        TextView movieTitle;
        TextView movieRating;

        public ViewHolder(CardView movieCard, ImageView moviePoster, TextView movieTitle,
                          TextView movieRating) {
            this.movieCard = movieCard;
            this.moviePoster = moviePoster;
            this.movieTitle = movieTitle;
            this.movieRating = movieRating;
        }
    }

    public interface KnownForListener {
        void knownForMovieClicked(Integer movieId, int clickedPosition, ImageView sharedImageView);
    }
}
