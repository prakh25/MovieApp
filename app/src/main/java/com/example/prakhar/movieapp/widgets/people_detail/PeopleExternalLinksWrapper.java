package com.example.prakhar.movieapp.widgets.people_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/4/2017.
 */

public class PeopleExternalLinksWrapper extends LinearLayout {

    @BindView(R.id.detail_links_movie_homepage_frame)
    FrameLayout homepageFrame;
    @BindView(R.id.home_page_link)
    TextView homepageLink;
    @BindView(R.id.detail_links_tmdb_frame)
    FrameLayout tmdbFrame;
    @BindView(R.id.detail_links_google_frame)
    FrameLayout googleFrame;

    public PeopleExternalLinksWrapper(Context context) {
        super(context);
    }

    public PeopleExternalLinksWrapper(Context context, String personHomepage, Integer tmdbId, String personName) {
        super(context);
        init(context);

        if(personHomepage != null && !personHomepage.isEmpty()) {
            homepageFrame.setVisibility(VISIBLE);
            homepageLink.setText("Open Official Site");
            homepageFrame.setOnClickListener(v ->
                    context.startActivity(browserIntent(personHomepage))
            );
        }

        String tmdbUrl = String.format(Locale.US, "%s%d", Constants.TMDB_PERSON_LINK, tmdbId);
        tmdbFrame.setOnClickListener(v -> context.startActivity(browserIntent(tmdbUrl)));

        String googleUrl = Constants.GO0GLE_SEARCH_LINK + personName;
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
