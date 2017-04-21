package com.example.prakhar.movieapp.ui.more_movies_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.ui.base.BaseActivity;

import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_TITLE;

/**
 * Created by Prakhar on 3/18/2017.
 */

public class MovieListActivity extends BaseActivity {

    public static final String EXTRA_GENRE_ID = "genreId";

    public static Intent newStartIntent(Context context, String activityTitle,
                                        @Nullable Integer genreId) {
        Intent intent = new Intent(context, MovieListActivity.class);
        intent.putExtra(ARG_TOOLBAR_TITLE, activityTitle);
        intent.putExtra(EXTRA_GENRE_ID, genreId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        String title = getIntent().getStringExtra(ARG_TOOLBAR_TITLE);
        Integer genreId = getIntent().getIntExtra(EXTRA_GENRE_ID, 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.more_container, MovieListFragment.newInstance(title, genreId))
                    .commitAllowingStateLoss();
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
