package com.example.prakhar.movieapp.ui.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.genre.Genre;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.more_movies_list.MoreActivity;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Prakhar on 3/27/2017.
 */

public class GenreFragment extends Fragment implements
        GenreContract.GenreView, GenreAdapter.GenreInteractionListener {

    @BindView(R.id.genre_list)
    RecyclerView recyclerView;
    @BindView(R.id.general_toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar_fragment)
    ProgressBar progressBar;

    @BindView(R.id.iv_message)
    ImageView messageImage;
    @BindView(R.id.tv_message)
    TextView messageText;
    @BindView(R.id.btn_try_again)
    Button tryAgainBtn;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;

    private String title;
    private GenreAdapter genreAdapter;
    private AppCompatActivity activity;
    private GenrePresenter genrePresenter;
    private Unbinder unbinder;

    public GenreFragment() {
    }

    public static GenreFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(Constants.GENRE_ACTIVITY_TITLE, title);
        GenreFragment fragment = new GenreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            title = getArguments().getString(Constants.GENRE_ACTIVITY_TITLE);
        }
        genrePresenter = new GenrePresenter(DataManager.getInstance());
        genreAdapter = new GenreAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_genre, container, false);
        genrePresenter.attachView(this);
        genreAdapter.setGenreInteractionListener(this);
        init(view);
        genrePresenter.getGenreList();
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);

        activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(title);
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, layoutManager.getOrientation()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(genreAdapter);
    }

    @Override
    public void onGenreSelected(String name, Integer id) {
        String title = String.format(Locale.US, "Popular %s Movies", name);
        startActivity(MoreActivity.newStartIntent(activity, title, id));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
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
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showGenreList(List<Genre> genreList) {
        genreAdapter.addItems(genreList);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        genrePresenter.detachView();
        super.onDestroy();
    }
}
