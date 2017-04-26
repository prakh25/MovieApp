package com.example.prakhar.mobile.ui.more_images;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.model.home.movie.Poster;
import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.ui.full_screen_image.FullScreenImageFragment;
import com.example.prakhar.mobile.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.example.prakhar.mobile.utils.Constants.ARG_IMAGE_LIST;
import static com.example.prakhar.mobile.utils.Constants.ARG_TOOLBAR_COLOR;
import static com.example.prakhar.mobile.utils.Constants.ARG_TOOLBAR_TITLE;
import static com.example.prakhar.mobile.utils.Constants.SCREEN_TABLET_DP_WIDTH;
import static com.example.prakhar.mobile.utils.Constants.TAB_LAYOUT_LANDSCAPE_IMAGE;
import static com.example.prakhar.mobile.utils.Constants.TAB_LAYOUT_PORTRAIT_IMAGE;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class MoreImagesFragment extends Fragment implements
        MoreImagesAdapter.MoreImagesInteractionListener {

    @BindView(R.id.more_images_list)
    RecyclerView recyclerView;
    @BindView(R.id.more_images_toolbar)
    Toolbar toolbar;

    private AppCompatActivity mActivity;
    private List<Poster> imagesList;
    private String title;
    private int toolbarColor;
    private MoreImagesAdapter adapter;
    private Unbinder unbinder;

    public static MoreImagesFragment newInstance(ArrayList<Poster> imageList, String title, int color) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_IMAGE_LIST, imageList);
        args.putString(ARG_TOOLBAR_TITLE, title);
        args.putInt(ARG_TOOLBAR_COLOR, color);
        MoreImagesFragment fragment = new MoreImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        imagesList = new ArrayList<>();
        if(getArguments() != null) {
            imagesList.addAll(getArguments().getParcelableArrayList(ARG_IMAGE_LIST));
            title = getArguments().getString(ARG_TOOLBAR_TITLE);
            toolbarColor = getArguments().getInt(ARG_TOOLBAR_COLOR);
        }
        adapter = new MoreImagesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_images, container, false);
        adapter.setListener(this);
        init(view);
        adapter.addItems(imagesList);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.i("onResume");

    }

    private void init(View view) {

        unbinder = ButterKnife.bind(this, view);

        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setBackgroundColor(toolbarColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.getWindow().setStatusBarColor(Utils.getDarkColor(toolbarColor));
        }
        toolbar.setTitle(title);

        boolean isTabLayout = Utils.isScreenW(SCREEN_TABLET_DP_WIDTH);

        recyclerView.setLayoutManager(setUpLayoutManager(isTabLayout));
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private RecyclerView.LayoutManager setUpLayoutManager(boolean isTabletLayout) {
        RecyclerView.LayoutManager layoutManager;
        if(!isTabletLayout) {
            layoutManager = new GridLayoutManager(mActivity, TAB_LAYOUT_PORTRAIT_IMAGE);
        } else {
            layoutManager = new GridLayoutManager(mActivity, TAB_LAYOUT_LANDSCAPE_IMAGE);
        }
        return layoutManager;
    }

    @Override
    public void onImageClicked(int position) {
        ArrayList<Poster> images = new ArrayList<>();
        images.addAll(imagesList);
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FullScreenImageFragment fragment = FullScreenImageFragment.newInstance(images, position);
        fragment.setTargetFragment(MoreImagesFragment.this, 300);
        fragment.show(fragmentManager, "fullScreenImage");
    }

    @Override
    public void onDestroyView() {
        recyclerView.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }
}
