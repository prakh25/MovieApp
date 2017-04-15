package com.example.prakhar.movieapp.ui.movie_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Result;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.example.prakhar.movieapp.utils.Constants.ARG_MOVIE_RESULT;
import static com.example.prakhar.movieapp.utils.Constants.ARG_USER_RATING;

/**
 * Created by Prakhar on 3/24/2017.
 */

public class RatingDialogFragment extends DialogFragment {

    @BindView(R.id.rating_dialog_title)
    TextView ratingDialogTitle;
    @BindView(R.id.ratingBar)
    MaterialRatingBar ratingBar;
    @BindView(R.id.rating_text)
    TextView ratingText;
    @BindView(R.id.rating_save_btn)
    Button saveRatingBan;
    @BindView(R.id.rating_delete_btn)
    Button deleteRatingBtn;

    private Integer userRating;
    private Result result;
    private RatingDialogListener listener;

    public RatingDialogFragment() {
    }

    public static RatingDialogFragment newInstance(Result result, Integer userRating) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_RESULT, result);
        args.putInt(ARG_USER_RATING, userRating);
        RatingDialogFragment fragment = new RatingDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rating, container);
        if (getArguments() != null) {
            result = getArguments().getParcelable(ARG_MOVIE_RESULT);
            userRating = getArguments().getInt(ARG_USER_RATING);
        }
        listener = (RatingDialogListener) getTargetFragment();
        init(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        ratingDialogTitle.setText(result.getTitle());

        ratingBar.setRating((float) userRating);

        if (userRating == 0) {
            deleteRatingBtn.setEnabled(false);
        } else {
            deleteRatingBtn.setEnabled(true);
        }

        ratingBar.setOnRatingChangeListener((ratingBar1, rating) -> {
            saveRatingBan.setEnabled(true);
            userRating = (int) rating;
            ratingText.setVisibility(View.VISIBLE);
            ratingText.setText(String.format(Locale.US, "%d / 10", userRating));
        });

        saveRatingBan.setOnClickListener(v -> {
            listener.onRatingSave(result, userRating);
            dismiss();
        });

        deleteRatingBtn.setOnClickListener(v -> {
            ratingBar.setRating(0);
            listener.onRatingDelete();
            dismiss();
        });
    }

    public interface RatingDialogListener {
        void onRatingSave(Result result, int rating);

        void onRatingDelete();
    }
}
