package com.example.prakhar.movieapp.widgets.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.home.movie.Poster;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/28/2017.
 */

public class MovieImagesWrapper extends FrameLayout {

    @BindView(R.id.image_wrapper_title)
    TextView title;
    @BindView(R.id.images_list)
    RecyclerView recyclerView;
    @BindView(R.id.more_images_btn)
    Button moreImages;

    private MovieImagesAdapter adapter;
    private MovieImagesListener listener;

    public MovieImagesWrapper(@NonNull Context context) {
        super(context);
    }

    public MovieImagesWrapper(Context context, List<Poster> imageList, String wrapperTitle,
                              boolean showViewAllBtn, MovieImagesListener imagesListener,
                              MovieImagesAdapter.MovieImageAdapterListener adapterListener) {
        super(context);
        adapter = new MovieImagesAdapter(imageList, adapterListener);
        init(context);
        listener = imagesListener;
        title.setText(wrapperTitle);

        if(showViewAllBtn) {
            moreImages.setVisibility(VISIBLE);
            moreImages.setOnClickListener(v -> listener.onMoreImagesRequested());
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.detail_images_wrapper,  this);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.END);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public interface MovieImagesListener {
        void onMoreImagesRequested();
    }
}
