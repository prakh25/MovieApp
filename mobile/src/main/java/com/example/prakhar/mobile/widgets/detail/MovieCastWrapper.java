package com.example.prakhar.mobile.widgets.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.example.core.model.movie_detail.tmdb.Cast;
import com.example.prakhar.mobile.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/6/2017.
 */

public class MovieCastWrapper extends FrameLayout {

    @BindView(R.id.credit_grid_view)
    GridView castGridView;
    @BindView(R.id.credit_wrapper_title)
    TextView wrapperTitle;
    @BindView(R.id.credit_wrapper_btn)
    Button castButton;

    private MovieCastAdapter adapter;
    private MovieCastListener listener;

    public MovieCastWrapper(@NonNull Context context) {
        super(context);
    }

    public MovieCastWrapper(Context context, List<Cast> castList, int size, MovieCastListener castListener,
                            MovieCastAdapter.InteractionListener interactionListener) {
        super(context);
        adapter = new MovieCastAdapter(castList, interactionListener, size);
        init(context);
        listener = castListener;
        wrapperTitle.setText(R.string.top_billed_cast);
        castButton.setText(R.string.full_cast_btn);
        castButton.setOnClickListener(v -> listener.onFullCastButtonClicked());
    }

    private void init(Context context) {
        inflate(context, R.layout.detail_cast_wrapper, this);
        ButterKnife.bind(this);
        castGridView.setAdapter(adapter);
    }

    public interface MovieCastListener {
        void onFullCastButtonClicked();
    }
}
