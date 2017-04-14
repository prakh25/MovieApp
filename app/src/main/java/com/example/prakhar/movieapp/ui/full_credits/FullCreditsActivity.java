package com.example.prakhar.movieapp.ui.full_credits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;
import com.example.prakhar.movieapp.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Prakhar on 3/30/2017.
 */

public class FullCreditsActivity extends BaseActivity {

    public static final String EXTRA_CREW_LIST = "extraCrew";
    public static final String EXTRA_CAST_LIST = "extraCast";
    public static final String EXTRA_TTILE = "extraTitle";
    public static final String EXTRA_COLOR = "extraColor";

    public static Intent newStartIntent(Context context, @Nullable ArrayList<Crew> creditList,
                                        @Nullable ArrayList<Cast> casts,
                                        String title, int color) {

        Intent intent = new Intent(context, FullCreditsActivity.class);

        intent.putParcelableArrayListExtra(EXTRA_CREW_LIST, creditList);
        intent.putParcelableArrayListExtra(EXTRA_CAST_LIST, casts);
        intent.putExtra(EXTRA_TTILE, title);
        intent.putExtra(EXTRA_COLOR, color);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_credits);
        String title = getIntent().getStringExtra(EXTRA_TTILE);
        int color = getIntent().getIntExtra(EXTRA_COLOR, R.color.colorPrimary);
        ArrayList<Crew> crewList = new ArrayList<>();
        crewList.addAll(getIntent().getParcelableArrayListExtra(EXTRA_CREW_LIST));
        ArrayList<Cast> castLIst = new ArrayList<>();
        castLIst.addAll(getIntent().getParcelableArrayListExtra(EXTRA_CAST_LIST));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.full_credit_container, FullCreditFragment.newInstance(crewList, castLIst, title, color))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
