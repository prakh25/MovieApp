package com.example.prakhar.movieapp.ui.people_detail;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.people_detail.Cast;
import com.example.prakhar.movieapp.model.people_detail.Crew;
import com.example.prakhar.movieapp.model.people_detail.ExternalIds;
import com.example.prakhar.movieapp.model.person_search.KnownFor;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.movie_detail.MovieDetailActivity;
import com.example.prakhar.movieapp.ui.people_full_credit.PeopleFullCreditActivity;
import com.example.prakhar.movieapp.utils.Constants;
import com.example.prakhar.movieapp.utils.Utils;
import com.example.prakhar.movieapp.utils.palette.PaletteBitmap;
import com.example.prakhar.movieapp.utils.palette.PaletteBitmapTranscoder;
import com.example.prakhar.movieapp.widgets.people_detail.KnownForAdapter;
import com.example.prakhar.movieapp.widgets.people_detail.PeopleBiographyWrapper;
import com.example.prakhar.movieapp.widgets.people_detail.PeopleExternalLinksWrapper;
import com.example.prakhar.movieapp.widgets.people_detail.PeopleKnownForWrapper;
import com.example.prakhar.movieapp.widgets.people_detail.PeoplePersonalInfoWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.prakhar.movieapp.utils.Constants.ARG_PERSON_ID;
import static com.example.prakhar.movieapp.utils.Constants.ARG_PROFILE_PATH;

/**
 * Created by Prakhar on 4/3/2017.
 */

public class PeopleDetailFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener,
        PeopleDetailContract.PeopleDetailView, KnownForAdapter.KnownForListener,
        PeopleKnownForWrapper.PublicKnownForListener {

    @BindView(R.id.people_app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.people_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.people_header_frame)
    FrameLayout headerFrame;
    @BindView(R.id.people_widget_container)
    LinearLayout peopleFrame;
    @BindView(R.id.people_toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.people_name)
    TextView activityTtile;
    @BindView(R.id.people_person_name)
    TextView personName;
    @BindView(R.id.people_person_image)
    ImageView personProfileImage;
    @BindView(R.id.people_external_ids_layout)
    LinearLayout externalIds;
    @BindView(R.id.people_facebook_btn)
    ImageButton facebookBtn;
    @BindView(R.id.people_instagram_btn)
    ImageButton instagramBtn;
    @BindView(R.id.people_twitter_btn)
    ImageButton twitterBtn;
    @BindView(R.id.people_imdb_btn)
    ImageButton imdbBtn;
    @BindView(R.id.progress_bar_fragment)
    ProgressBar progressBar;
    @BindView(R.id.detail_people_layout)
    CoordinatorLayout layout;

    @BindView(R.id.iv_message)
    ImageView messageImage;
    @BindView(R.id.tv_message)
    TextView messageText;
    @BindView(R.id.btn_try_again)
    Button tryAgainBtn;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;

    private Integer personId;
    private String profilePath;
    private AppCompatActivity activity;
    private PeopleDetailPresenter presenter;
    private Unbinder unbinder;
    private GradientDrawable gradientDrawable;
    private Animation animation;
    private int toolbarColor;

    public PeopleDetailFragment() {
    }

    public static PeopleDetailFragment newInstance(Integer personId,
                                                   @Nullable String profilePath) {

        Bundle args = new Bundle();
        args.putInt(ARG_PERSON_ID, personId);
        args.putString(ARG_PROFILE_PATH, profilePath);
        PeopleDetailFragment fragment = new PeopleDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personId = getArguments().getInt(ARG_PERSON_ID);
            profilePath = getArguments().getString(ARG_PROFILE_PATH);
        }
        presenter = new PeopleDetailPresenter(DataManager.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_detail, container, false);
        presenter.attachView(this);
        init(view);
        presenter.onPersonDetailRequested(personId);
        appBarLayout.addOnOffsetChangedListener(this);
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        animation = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
        animation.setDuration(1000);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        tryAgainBtn.setOnClickListener(v -> presenter.onPersonDetailRequested(personId));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
            activityTtile.setVisibility(View.VISIBLE);
            activityTtile.setText(personName.getText());
            toolbar.setBackgroundColor(toolbarColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Utils.getDarkColor(toolbarColor));
            }
        } else {
            activityTtile.setVisibility(View.GONE);
            toolbar.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, android.R.color.transparent));
            }
        }
    }

    @Override
    public void showPeopleDetailHeader(String name, ExternalIds externalIds) {
        if (profilePath != null && !profilePath.isEmpty()) {
            BitmapRequestBuilder<String, PaletteBitmap> glideRequest =
                    Glide.with(activity).fromString().asBitmap()
                            .transcode(new PaletteBitmapTranscoder(activity), PaletteBitmap.class);

            glideRequest.load(Constants.TMDB_IMAGE_URL + "w185" + profilePath)
                    .into(new ImageViewTarget<PaletteBitmap>(personProfileImage) {
                        @Override
                        protected void setResource(PaletteBitmap resource) {
                            super.view.setImageBitmap(resource.bitmap);
                            toolbarColor = resource.palette.getVibrantColor(
                                    resource.palette.getDominantColor(
                                            resource.palette.getMutedColor(
                                                    resource.palette.getDarkVibrantColor(
                                                            resource.palette.getDarkMutedColor(
                                                                    ContextCompat.getColor(activity, R.color.colorPrimary))))));

                            int[] colors = {toolbarColor, ContextCompat
                                    .getColor(activity, android.R.color.transparent)};

                            gradientDrawable = new GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM,
                                    colors);
                            gradientDrawable.setCornerRadius(0f);
                            gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                headerFrame.setBackground(gradientDrawable);
                            } else {
                                headerFrame.setBackgroundDrawable(gradientDrawable);
                            }
                        }
                    });
        } else {
            BitmapRequestBuilder<Integer, PaletteBitmap> glideRequest;
            glideRequest = Glide.with(this).fromResource().asBitmap()
                    .transcode(new PaletteBitmapTranscoder(activity), PaletteBitmap.class);
            glideRequest.load(R.drawable.movie_people_placeholder)
                    .into(new ImageViewTarget<PaletteBitmap>(personProfileImage) {
                        @Override
                        protected void setResource(PaletteBitmap resource) {
                            super.view.setImageBitmap(resource.bitmap);
                            toolbarColor = resource.palette.getVibrantColor(
                                    resource.palette.getDominantColor(ContextCompat.getColor(activity, R.color.colorPrimary)));
                            int[] colors = {toolbarColor, ContextCompat
                                    .getColor(activity, android.R.color.transparent)};

                            gradientDrawable = new GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM,
                                    colors);
                            gradientDrawable.setCornerRadius(0f);
                            gradientDrawable.setAlpha(60);
                            gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                headerFrame.setBackground(gradientDrawable);
                            } else {
                                headerFrame.setBackgroundDrawable(gradientDrawable);
                            }
                        }
                    });
        }

        personName.setText(name);

        if (externalIds.getImdbId() != null && !externalIds.getImdbId().isEmpty()) {
            imdbBtn.setVisibility(View.VISIBLE);
        }
        if (externalIds.getFacebookId() != null && !externalIds.getFacebookId().isEmpty()) {
            facebookBtn.setVisibility(View.VISIBLE);
        }
        if (externalIds.getInstagramId() != null && !externalIds.getInstagramId().isEmpty()) {
            instagramBtn.setVisibility(View.VISIBLE);
        }
        if (externalIds.getTwitterId() != null && !externalIds.getTwitterId().isEmpty()) {
            twitterBtn.setVisibility(View.VISIBLE);
        }

        headerFrame.setAnimation(animation);
    }

    @Override
    public void showPersonBio(String biography) {
        Animation animations = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        PeopleBiographyWrapper wrapper = new PeopleBiographyWrapper(activity, biography);
        peopleFrame.addView(wrapper);
        peopleFrame.setAnimation(animations);
    }

    @Override
    public void showPersonKnownFor(List<KnownFor> knownForList) {
        PeopleKnownForWrapper wrapper = new PeopleKnownForWrapper(activity, knownForList,
                this, this);
        peopleFrame.addView(wrapper);
    }

    @Override
    public void showPersonalInfo(Integer gender, String birthday, String deathDay, String placeOfBirth, List<String> alsoKnownAs) {
        PeoplePersonalInfoWrapper wrapper = new PeoplePersonalInfoWrapper(activity,
                gender, birthday, deathDay, placeOfBirth, alsoKnownAs);
        peopleFrame.addView(wrapper);
    }

    @Override
    public void showExternalLinks(String personHomePage, Integer tmdbId, String personName) {
        PeopleExternalLinksWrapper wrapper = new PeopleExternalLinksWrapper(activity,
                personHomePage, tmdbId, personName);
        peopleFrame.addView(wrapper);
    }

    @Override
    public void showProgress() {
        if (progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
        peopleFrame.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        peopleFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        messageImage.setImageResource(R.drawable.ic_error_white_24dp);
        messageText.setText(getString(R.string.nothing_to_display));
        tryAgainBtn.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        messageImage.setImageResource(R.drawable.ic_error_white_24dp);
        messageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        tryAgainBtn.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        messageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        layout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onViewFullCreditClicked() {
        ArrayList<Cast> casts = new ArrayList<>();
        casts.addAll(presenter.getCastList());
        ArrayList<Crew> crews = new ArrayList<>();
        crews.addAll(presenter.getCrewList());
        startActivity(PeopleFullCreditActivity.newStartIntent(activity, crews, casts,
                toolbarColor));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    @Override
    public void knownForMovieClicked(Integer movieId, int clickedPosition) {
        startActivity(MovieDetailActivity.newStartIntent(activity, movieId));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
