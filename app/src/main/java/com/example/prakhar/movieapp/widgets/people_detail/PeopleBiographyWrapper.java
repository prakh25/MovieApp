package com.example.prakhar.movieapp.widgets.people_detail;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.prakhar.movieapp.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/3/2017.
 */

public class PeopleBiographyWrapper extends LinearLayout {

    @BindView(R.id.people_person_bio)
    ExpandableTextView expandableTextView;

    public PeopleBiographyWrapper(Context context) {
        super(context);
    }

    public PeopleBiographyWrapper(Context context, String biography) {
        super(context);

        inflate(context, R.layout.people_biography_wrapper, this);
        ButterKnife.bind(this);

        if(biography != null && !biography.isEmpty()) {
            expandableTextView.setText(biography);
        }
    }
}
