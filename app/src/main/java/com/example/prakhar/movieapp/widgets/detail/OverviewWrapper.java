package com.example.prakhar.movieapp.widgets.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.utils.Utils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/5/2017.
 */

public class OverviewWrapper extends LinearLayout {

    @BindView(R.id.detail_plot_frame)
    FrameLayout plotFrame;
    @BindView(R.id.detail_plot_text)
    TextView overViewText;
    @BindView(R.id.detail_release_date_frame)
    FrameLayout releaseDateFrame;
    @BindView(R.id.detail_release_date)
    TextView releaseDateText;
    @BindView(R.id.detail_tagline_frame)
    FrameLayout tagLineFrame;
    @BindView(R.id.detail_tagline)
    TextView tagLineText;

    public OverviewWrapper(@NonNull Context context) {
        super(context);
    }

    public OverviewWrapper(Context context, String releaseDate, String region,
                           String tagLine, String overview) {
        super(context);
        init(context);

        if(releaseDate != null && !releaseDate.isEmpty()) {
            releaseDateText.setText(String.format(Locale.US,"%s (%s)",
                    Utils.getReleaseDate(releaseDate), region));
        } else {
            releaseDateFrame.setVisibility(GONE);
        }

        if(tagLine != null && !tagLine.isEmpty()) {
            tagLineText.setText(tagLine);
        } else {
            tagLineFrame.setVisibility(GONE);
        }

        if(overview != null && !overview.isEmpty()) {
            overViewText.setText(overview);
        } else {
            overViewText.setText(R.string.no_description);
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.detail_overview_wrapper, this);
        ButterKnife.bind(this);
    }

}
