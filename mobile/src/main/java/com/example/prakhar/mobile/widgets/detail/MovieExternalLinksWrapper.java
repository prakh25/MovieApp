package com.example.prakhar.mobile.widgets.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.utils.Constants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/1/2017.
 */

public class MovieExternalLinksWrapper extends LinearLayout {

    @BindView(R.id.detail_links_movie_homepage_frame)
    FrameLayout homepageFrame;
    @BindView(R.id.detail_links_tmdb_frame)
    FrameLayout tmdbFrame;
    @BindView(R.id.detail_links_imdb_frame)
    FrameLayout imdbFrame;
    @BindView(R.id.detail_links_google_frame)
    FrameLayout googleFrame;

    public MovieExternalLinksWrapper(Context context) {
        super(context);
    }

    public MovieExternalLinksWrapper(Context context, String movieHomepage, Integer tmdbId,
                                     String imdbId, String movieName) {
        super(context);
        init(context);

        if(movieHomepage != null && !movieHomepage.isEmpty()) {
            homepageFrame.setVisibility(VISIBLE);
            homepageFrame.setOnClickListener(v ->
                context.startActivity(browserIntent(movieHomepage))
            );
        }

        if(imdbId != null && !imdbId.isEmpty()) {
            imdbFrame.setVisibility(VISIBLE);
            String imdbUrl = Constants.HTTP_IMDB_COM_TITLE + imdbId;
            imdbFrame.setOnClickListener(v -> context.startActivity(browserIntent(imdbUrl)));
        }

        String tmdbUrl = String.format(Locale.US, "%s%d", Constants.TMDB_MOVIE_LINK, tmdbId);
        tmdbFrame.setOnClickListener(v -> context.startActivity(browserIntent(tmdbUrl)));

        String googleUrl = Constants.GO0GLE_SEARCH_LINK + movieName;
        googleFrame.setOnClickListener(v -> context.startActivity(browserIntent(googleUrl)));
    }

    private void init(Context context) {
        inflate(context, R.layout.external_links_wrapper, this);
        ButterKnife.bind(this);
    }

    private Intent browserIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }
}
