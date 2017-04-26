package com.example.prakhar.mobile.ui.genre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.ui.base.BaseActivity;


/**
 * Created by Prakhar on 3/27/2017.
 */

public class GenreActivity extends BaseActivity {

    public static final String GENRE_ACTIVITY_TITLE = "argGenreTitle";

    public static Intent newStartIntent(Context context, String activityTitle) {
        Intent intent = new Intent(context, GenreActivity.class);
        intent.putExtra(GENRE_ACTIVITY_TITLE, activityTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        String title = getIntent().getStringExtra(GENRE_ACTIVITY_TITLE);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.genre_container, GenreFragment.newInstance(title))
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
