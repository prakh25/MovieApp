package com.example.prakhar.mobile.widgets.people_detail;

import android.content.Context;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.core.model.person_search.KnownFor;
import com.example.prakhar.mobile.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 4/4/2017.
 */

public class PeopleKnownForWrapper extends LinearLayout {

    @BindView(R.id.people_known_for_grid)
    GridView gridView;
    @BindView(R.id.people_view_all_credits)
    TextView fullCredit;

    private KnownForAdapter adapter;
    private PublicKnownForListener listener;

    public PeopleKnownForWrapper(Context context) {
        super(context);
    }

    public PeopleKnownForWrapper(Context context, List<KnownFor> knownForList,
                                 KnownForAdapter.KnownForListener knownForListener,
                                 PublicKnownForListener publicKnownForListener) {
        super(context);
        adapter = new KnownForAdapter(knownForList, knownForListener);
        listener = publicKnownForListener;
        init(context);

        fullCredit.setOnClickListener(v -> listener.onViewFullCreditClicked());
    }

    private void init(Context context) {
        inflate(context, R.layout.people_credits_wrapper, this);
        ButterKnife.bind(this);
        gridView.setAdapter(adapter);
    }

    public interface PublicKnownForListener {
        void onViewFullCreditClicked();
    }
}
