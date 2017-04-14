package com.example.prakhar.movieapp.widgets.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.BelongsToCollection;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/29/2017.
 */

public class MovieCollectionWrapper extends FrameLayout {

    @BindView(R.id.collection_backdrop)
    ImageView collectionBackDrop;
    @BindView(R.id.collection_title)
    TextView collectionTitle;
    @BindView(R.id.collection_btn)
    Button collectionBtn;

    public MovieCollectionWrapper(@NonNull Context context) {
        super(context);
    }

    public MovieCollectionWrapper(Context context, BelongsToCollection belongsToCollection) {
        super(context);
        init(context);

        Glide.with(context)
                .load(Constants.TMDB_IMAGE_URL + "w780" + belongsToCollection.getBackdropPath())
                .into(collectionBackDrop);

        collectionTitle.setText(String.format(Locale.US, "Part of %s",
                belongsToCollection.getName()));
    }

    private void init(Context context) {
        inflate(context, R.layout.detail_collection_wrapper, this);
        ButterKnife.bind(this);
    }
}
