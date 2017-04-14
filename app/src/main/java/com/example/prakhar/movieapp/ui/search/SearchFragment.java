package com.example.prakhar.movieapp.ui.search;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.search.SearchResult;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.movie_detail.MovieDetailActivity;
import com.example.prakhar.movieapp.ui.people_detail.PeopleDetailActivity;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Prakhar on 3/20/2017.
 */

public class SearchFragment extends Fragment implements SearchContract.SearchView,
        SearchAdapter.ListInteractionListener {

    @BindView(R.id.search_back_btn)
    ImageView backBtn;
    @BindView(R.id.search_text_query)
    AutoCompleteTextView searchQueryView;
    @BindView(R.id.search_clear_btn)
    ImageView clearBtn;
    @BindView(R.id.search_list)
    RecyclerView recyclerView;

    private SearchAdapter searchAdapter;
    private SearchPresenter searchPresenter;
    private Unbinder unbinder;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchAdapter = new SearchAdapter();
        searchPresenter = new SearchPresenter(DataManager.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchPresenter.attachView(this);
        searchAdapter.setInteractionListener(this);
        init(view);

        return view;
    }

    void init(View view) {

        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(searchAdapter);

        searchQueryView.addTextChangedListener(new TextWatcher() {

            long lastChange = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    searchAdapter.removeAll();
                }

                new Handler().postDelayed(() -> {
                    if (System.currentTimeMillis() - lastChange >= 400) {
                        searchPresenter.onSearchQueryEntered(s.toString());
                    }
                }, 400);
                lastChange = System.currentTimeMillis();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBtn.setOnClickListener(v -> getActivity().onBackPressed());

        clearBtn.setOnClickListener(v -> {
            searchAdapter.removeAll();
            searchQueryView.setText(null);
            searchQueryView.requestFocus();
        });
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
    public void showSearch(List<SearchResult> searchResultList) {
        searchAdapter.removeAll();
        searchAdapter.addItems(searchResultList);
    }

    @Override
    public void onMovieItemClick(Integer movieId, int clickedPosition) {
        startActivity(MovieDetailActivity.newStartIntent(getActivity(),movieId));
    }

    @Override
    public void onPersonClicked(Integer personId, String profilePath, int clickedPosition) {
        startActivity(PeopleDetailActivity.newStartIntent(getActivity(), personId, profilePath));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        searchPresenter.onDestroy();
        searchPresenter.detachView();
        super.onDestroy();
    }
}
