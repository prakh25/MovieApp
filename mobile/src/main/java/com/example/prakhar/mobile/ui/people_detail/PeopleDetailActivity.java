package com.example.prakhar.mobile.ui.people_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.ui.base.BaseActivity;

import timber.log.Timber;

/**
 * Created by Prakhar on 4/3/2017.
 */

public class PeopleDetailActivity extends BaseActivity {

    private static String EXTRA_PERSON_ID = "extraPersonId";
    private static String EXTRA_PROFILE_PATH = "extraProfilePath";

    public static Intent newStartIntent(Context context, Integer personId, @Nullable String profilePath) {
        Intent intent = new Intent(context, PeopleDetailActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        intent.putExtra(EXTRA_PROFILE_PATH, profilePath);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Integer personId = getIntent().getIntExtra(EXTRA_PERSON_ID, 0);
        String profilePath = getIntent().getStringExtra(EXTRA_PROFILE_PATH);
        Timber.i(personId.toString());
        Timber.i(profilePath);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.people_container, PeopleDetailFragment.newInstance(personId, profilePath))
                    .commit();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
