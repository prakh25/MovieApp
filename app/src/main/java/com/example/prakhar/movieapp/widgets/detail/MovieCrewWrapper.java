package com.example.prakhar.movieapp.widgets.detail;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/29/2017.
 */

public class MovieCrewWrapper extends LinearLayout {

    @BindView(R.id.director_name)
    TextView directorsName;
    @BindView(R.id.writers_name)
    TextView writersName;
    @BindView(R.id.screenplay_name)
    TextView screenPlayName;

    @BindView(R.id.director_name_frame)
    FrameLayout directorsFrame;
    @BindView(R.id.screenplay_name_frame)
    FrameLayout screenPlayFrame;
    @BindView(R.id.writers_name_frame)
    FrameLayout writersNameFrame;

    @BindView(R.id.detail_full_crew_btn)
    Button fullCrewButton;

    private MovieCrewListener crewListener;

    public MovieCrewWrapper(Context context) {
        super(context);
    }

    public MovieCrewWrapper(Context context, List<Crew> directors, List<Crew> writers,
                            List<Crew> screenPlays, MovieCrewListener listener) {
        super(context);

        init(context);

        crewListener = listener;

        if (!directors.isEmpty()) {
            directorsName.setText(android.text.TextUtils.join(", ", getNames(directors)));
            if (directors.size() == 1) {
                directorsFrame.setOnClickListener(v ->
                        listener.startPeopleDetailActivity(directors.get(0).getId(),
                                directors.get(0).getProfilePath()));
            } else if (directors.size() > 1) {
                directorsFrame.setOnClickListener(v ->
                        listener.startFullCreditActivity(directors, "Directed By"));
            }
        }

        if (!writers.isEmpty()) {
            writersName.setText(android.text.TextUtils.join(", ", getNames(writers)));
            if (writers.size() == 1) {
                writersNameFrame.setOnClickListener(v ->
                        listener.startPeopleDetailActivity(writers.get(0).getId(),
                                writers.get(0).getProfilePath()));
            } else if (writers.size() > 1) {
                writersNameFrame.setOnClickListener(v ->
                        listener.startFullCreditActivity(writers, "Written By"));
            }
        } else {
            writersNameFrame.setVisibility(GONE);
        }

        if (!screenPlays.isEmpty()) {
            screenPlayName.setText(android.text.TextUtils.join(", ", getNames(screenPlays)));
            if (screenPlays.size() == 1) {
                screenPlayFrame.setOnClickListener(v ->
                        listener.startPeopleDetailActivity(screenPlays.get(0).getId(),
                                screenPlays.get(0).getProfilePath()));
            } else if (screenPlays.size() > 1) {
                screenPlayFrame.setOnClickListener(v ->
                        listener.startFullCreditActivity(screenPlays, "ScreenPlay Credits"));
            }
        } else {
            screenPlayFrame.setVisibility(GONE);
        }

        fullCrewButton.setOnClickListener(v -> crewListener.onFullCrewButtonClicked());
    }

    void init(Context context) {
        inflate(context, R.layout.detail_crew_wrapper, this);
        ButterKnife.bind(this);
    }

    private List<String> getNames(List<Crew> crewList) {
        List<String> strings = new ArrayList<>();
        for (Crew crew : crewList) {
            strings.add(crew.getName());
        }
        return strings;
    }

    public interface MovieCrewListener {
        void onFullCrewButtonClicked();

        void startPeopleDetailActivity(Integer personId, String profilePath);

        void startFullCreditActivity(List<Crew> crewList, String title);
    }
}
