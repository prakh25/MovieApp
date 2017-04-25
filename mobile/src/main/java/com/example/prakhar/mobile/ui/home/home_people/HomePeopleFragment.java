package com.example.prakhar.mobile.ui.home.home_people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.core.model.person_search.PersonSearchResult;
import com.example.core.network.DataManager;
import com.example.core.ui.home.home_people.HomePeopleContract;
import com.example.core.ui.home.home_people.HomePeoplePresenter;
import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.ui.people_detail.PeopleDetailActivity;
import com.example.prakhar.mobile.utils.EndlessRecyclerViewOnScrollListener;
import com.example.prakhar.mobile.utils.GridSpacingItemDecoration;
import com.example.prakhar.mobile.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.prakhar.mobile.utils.Constants.SCREEN_TABLET_DP_WIDTH;
import static com.example.prakhar.mobile.utils.Constants.TAB_LAYOUT_LANDSCAPE;
import static com.example.prakhar.mobile.utils.Constants.TAB_LAYOUT_PORTRAIT;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomePeopleFragment extends Fragment implements HomePeopleContract.HomePeopleView,
        HomePeopleAdapter.PersonInteractionListener {

    @BindView(R.id.home_people_list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_fragment)
    ProgressBar progressBar;
    @BindView(R.id.home_people_layout)
    LinearLayout layout;

    @BindView(R.id.iv_message)
    ImageView messageImage;
    @BindView(R.id.tv_message)
    TextView messageText;
    @BindView(R.id.btn_try_again)
    Button tryAgainBtn;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;

    private Unbinder unbinder;
    private HomePeoplePresenter homePeoplePresenter;
    private HomePeopleAdapter adapter;
    private AppCompatActivity activity;

    public HomePeopleFragment() {
    }

    public static HomePeopleFragment newInstance() {

        Bundle args = new Bundle();
        HomePeopleFragment fragment = new HomePeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        adapter = new HomePeopleAdapter();
        homePeoplePresenter = new HomePeoplePresenter(DataManager.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_people, container, false);
        homePeoplePresenter.attachView(this);
        adapter.setUpListener(this);
        init(view);
        homePeoplePresenter.onInitialisedRequest();
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        activity = (AppCompatActivity) getActivity();

        boolean isTabLayout = Utils.isScreenW(SCREEN_TABLET_DP_WIDTH);

        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(setUpLayoutManager(isTabLayout));
        if(!isTabLayout) {
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(TAB_LAYOUT_PORTRAIT,
                    Utils.dpToPx(16, activity), true));
        } else {
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(TAB_LAYOUT_LANDSCAPE,
                    Utils.dpToPx(16, activity), true));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(setupScrollListener(recyclerView.getLayoutManager()));

        tryAgainBtn.setOnClickListener(v -> homePeoplePresenter.onInitialisedRequest());

    }

    private RecyclerView.LayoutManager setUpLayoutManager(boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if(!isTabletLayout) {
            layoutManager = initGridLayoutManager(TAB_LAYOUT_PORTRAIT, 1);
        } else {
            layoutManager = initGridLayoutManager(TAB_LAYOUT_LANDSCAPE, 1);
        }
        return layoutManager;
    }

    private RecyclerView.LayoutManager initGridLayoutManager(final int spanCount, final int itemSpanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case HomePeopleAdapter.VIEW_TYPE_LOADING:
                        // If it is a loading view we wish to accomplish a single item per row
                        return spanCount;
                    default:
                        // Else, define the number of items per row (considering TAB_LAYOUT_SPAN_SIZE).
                        return itemSpanCount;
                }
            }
        });
        return gridLayoutManager;
    }


    private EndlessRecyclerViewOnScrollListener setupScrollListener(RecyclerView.LayoutManager layoutManager) {
        return new EndlessRecyclerViewOnScrollListener((GridLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                view.post(() -> {
                    if (adapter.addLoadingView()) {
                        homePeoplePresenter.onListEndReached(page);
                    }
                });
            }
        };
    }

    @Override
    public void showPopularPeople(List<PersonSearchResult> resultList) {
        adapter.addItems(resultList);
    }

    @Override
    public void showProgress() {
        if (adapter.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        adapter.removeLoadingView();
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
        recyclerView.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        homePeoplePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onPersonClicked(Integer personId, String profilePath, int clickedPosition) {
        startActivity(PeopleDetailActivity.newStartIntent(activity, personId, profilePath));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
