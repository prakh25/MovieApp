package com.example.prakhar.movieapp.ui.search;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                    progressBar.setVisibility(View.GONE);
                } else {
                    new Handler().postDelayed(() -> {
                        if (System.currentTimeMillis() - lastChange >= 400) {
                            searchPresenter.onSearchQueryEntered(s.toString());
                        }
                    }, 400);
                }
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
            messageLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        });
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
    public void showSearch(List<SearchResult> searchResultList) {
        searchAdapter.removeAll();
        searchAdapter.addItems(searchResultList);
    }

    @Override
    public void onMovieItemClick(Integer movieId, int clickedPosition, ImageView sharedImageView) {
        startActivity(MovieDetailActivity.newStartIntent(getActivity(),movieId,
                ViewCompat.getTransitionName(sharedImageView)),
                makeTransitionBundle(sharedImageView));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransitionAnimation();
        }
    }

    private Bundle makeTransitionBundle(ImageView sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransitionAnimation() {
        Transition transition = TransitionInflater.from(getActivity())
                .inflateTransition(R.transition.arc_motion);
        getActivity().getWindow().setSharedElementReenterTransition(transition);
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
        searchPresenter.detachView();
        super.onDestroy();
    }
}
