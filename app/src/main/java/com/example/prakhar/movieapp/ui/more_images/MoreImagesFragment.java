package com.example.prakhar.movieapp.ui.more_images;

import android.graphics.Color;
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

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.example.prakhar.movieapp.ui.full_screen_image.FullScreenImageFragment;
import com.example.prakhar.movieapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_COLOR;
import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_TITLE;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class MoreImagesFragment extends Fragment implements
        MoreImagesAdapter.MoreImagesInteractionListener {

    private static final String ARG_IMAGE_LIST = "argImageList";

    private static final int TAB_LAYOUT_LANDSCAPE = 4;
    private static final int TAB_LAYOUT_POTRAIT = 3;
    private static final int SCREEN_TABLET_DP_WIDTH = 600;

    @BindView(R.id.more_images_list)
    RecyclerView recyclerView;
    @BindView(R.id.more_images_toolbar)
    Toolbar toolbar;

    public MoreImagesFragment() {
    }

    public static MoreImagesFragment newInstance(ArrayList<Poster> imageList, String title, int color) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_IMAGE_LIST, imageList);
        args.putString(ARG_TOOLBAR_TITLE, title);
        args.putInt(ARG_TOOLBAR_COLOR, color);
        MoreImagesFragment fragment = new MoreImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private AppCompatActivity mActivity;
    private List<Poster> imagesList;
    private String title;
    private int toolbarColor;

    private MoreImagesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        ButterKnife.bind(this, view);

        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setBackgroundColor(toolbarColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float[] hsv = new float[3];
            Color.colorToHSV(toolbarColor, hsv);
            hsv[2] *= 0.8f; // value component
            int statusBarColor = Color.HSVToColor(hsv);
            mActivity.getWindow().setStatusBarColor(statusBarColor);
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
            layoutManager = new GridLayoutManager(mActivity, TAB_LAYOUT_POTRAIT);
        } else {
            layoutManager = new GridLayoutManager(mActivity, TAB_LAYOUT_LANDSCAPE);
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
}
