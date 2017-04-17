package com.example.prakhar.movieapp.ui.full_screen_image;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.example.prakhar.movieapp.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.prakhar.movieapp.utils.Constants.ARA_SELECT_POSITION;
import static com.example.prakhar.movieapp.utils.Constants.ARG_IMAGE_LIST;
import static com.example.prakhar.movieapp.utils.Constants.DELAY_MILLI_SECONDS;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class FullScreenImageFragment extends DialogFragment implements
        FullScreenImageAdapter.FullScreenInteractionListener{

    @BindView(R.id.full_screen_toolbar_frame)
    FrameLayout toolbarFrame;
    @BindView(R.id.full_screen_back_btn)
    ImageView backButton;
    @BindView(R.id.full_screen_view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.full_screen_page_count)
    TextView pageCount;

    private boolean isToolbarVisible = false;
    private boolean isPageCountVisible = false;
    private List<Poster> imageList;
    private int selectedPosition = 0;
    private Unbinder unbinder;

    public FullScreenImageFragment() {
    }

    public static FullScreenImageFragment newInstance(ArrayList<Poster> imageList, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_IMAGE_LIST, imageList);
        args.putInt(ARA_SELECT_POSITION, position);
        FullScreenImageFragment fragment = new FullScreenImageFragment();
        fragment.setArguments(args);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(500);
            fragment.setEnterTransition(fade);
        }
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screen_image, container, false);
        init(view);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);

        imageList = new ArrayList<>();
        if(getArguments() != null) {
            imageList.addAll(getArguments().getParcelableArrayList(ARG_IMAGE_LIST));
            selectedPosition = getArguments().getInt(ARA_SELECT_POSITION);
        }

        FullScreenImageAdapter adapter = new FullScreenImageAdapter(getActivity(), imageList, this);
        viewPager.setAdapter(adapter);
        backButton.setOnClickListener(v -> dismiss());
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayPageCount(position);
    }

    CustomViewPager.OnPageChangeListener viewPagerPageChangeListener =
            new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            toolbarFrame.setVisibility(View.GONE);
            pageCount.setVisibility(View.GONE);
        }

        @Override
        public void onPageSelected(int position) {
            displayPageCount(position);
            pageCount.setVisibility(View.VISIBLE);
            toolbarFrame.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            pageCount.setVisibility(View.VISIBLE);
            isPageCountVisible = true;

            toolbarFrame.setVisibility(View.VISIBLE);
            isToolbarVisible = true;

            pageCount.postDelayed(() -> {
                pageCount.setVisibility(View.GONE);
                isPageCountVisible = false;
            }, DELAY_MILLI_SECONDS);

            toolbarFrame.postDelayed(() -> {
                toolbarFrame.setVisibility(View.GONE);
                isToolbarVisible = false;
            }, DELAY_MILLI_SECONDS);
        }
    };

    private void displayPageCount(int position) {
        pageCount.setText(String.format(Locale.US,"%d / %d", position + 1, imageList.size()));
    }

    @Override
    public void onImageClicked(int position) {
        if(!isToolbarVisible) {
            toolbarFrame.setVisibility(View.VISIBLE);
            toolbarFrame.postDelayed(() -> {
                toolbarFrame.setVisibility(View.GONE);
                isToolbarVisible = false;
            }, DELAY_MILLI_SECONDS);
        }
        if(!isPageCountVisible) {
            pageCount.setVisibility(View.VISIBLE);
            pageCount.postDelayed(() -> {
                pageCount.setVisibility(View.GONE);
                isPageCountVisible = false;
            }, DELAY_MILLI_SECONDS);
        }
    }
}
