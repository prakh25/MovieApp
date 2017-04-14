package com.example.prakhar.movieapp.widgets.people_detail;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.person_search.KnownFor;
import com.example.prakhar.movieapp.utils.Constants;

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

    public KnownForAdapter(List<KnownFor> knownFors, KnownForListener knownForListener) {
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
        }
        ButterKnife.bind(this, convertView);

        Glide.with(moviePoster.getContext())
                .load(Constants.TMDB_IMAGE_URL + "w185" + knownForList.get(position).getPosterPath())
                .placeholder(R.drawable.movie_poster_placeholder)
                .crossFade()
                .into(moviePoster);

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
                knownForList.get(position).getId(), position));

        return convertView;
    }

    public interface KnownForListener {
        void knownForMovieClicked(Integer movieId, int clickedPosition);
    }
}
