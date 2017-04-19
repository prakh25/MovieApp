package com.example.prakhar.movieapp.ui.home.home_tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.tv.TvResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomeTvFragment extends Fragment implements HomeTvContract.HomeTvView {

    @BindView(R.id.home_tv_content_frame)
    LinearLayout contentFrame;
    @BindView(R.id.progress_bar_fragment)
    ProgressBar progressBar;

    private Unbinder unbinder;

    public HomeTvFragment() {
    }

    public static HomeTvFragment newInstance() {

        Bundle args = new Bundle();
        HomeTvFragment fragment = new HomeTvFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tv, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    @Override
    public void showPopularTvShows(List<TvResult> tvResultList) {

    }

    @Override
    public void showTopRatedTvShows(List<TvResult> tvResultList) {

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
