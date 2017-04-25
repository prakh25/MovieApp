package com.example.prakhar.mobile.widgets.home_movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.prakhar.mobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/13/2017.
 */

public class HomeLinksWrapper extends LinearLayout {

    @BindView(R.id.home_popular_frame)
    FrameLayout popularFrame;
    @BindView(R.id.home_rated_frame)
    FrameLayout topRatedFrame;
    @BindView(R.id.home_box_office_frame)
    FrameLayout boxOfficeFrame;
    @BindView(R.id.home_genre_frame)
    FrameLayout genreFrame;

    private LinkInteractionListener listener;

    public HomeLinksWrapper(@NonNull Context context, LinkInteractionListener interactionListener) {
        super(context);
        init(context);
        listener = interactionListener;

        popularFrame.setOnClickListener(v ->
            listener.onFrameClick("Most Popular Movies")
        );

        topRatedFrame.setOnClickListener(v ->
                listener.onFrameClick("TMDB Top Rated"));

        boxOfficeFrame.setOnClickListener(v ->
                listener.onFrameClick("Weekend Box Office"));

        genreFrame.setOnClickListener(v ->
                listener.onGenreFrameClick("Genres"));
    }

    private void init(Context context) {
        inflate(context, R.layout.home_movie_list_wrapper, this);
        ButterKnife.bind(this);
    }

    public interface LinkInteractionListener {
        void onFrameClick(String title);

        void onGenreFrameClick(String title);
    }
}
