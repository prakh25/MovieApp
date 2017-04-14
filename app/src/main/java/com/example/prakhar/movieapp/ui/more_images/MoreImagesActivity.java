package com.example.prakhar.movieapp.ui.more_images;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.example.prakhar.movieapp.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Prakhar on 3/31/2017.
 */

public class MoreImagesActivity extends BaseActivity {

    private static final String EXTRA_IMAGE_LIST = "extraImageList";
    private static final String EXTRA_TTILE = "extraTitle";
    private static final String EXTRA_COLOR = "extraColor";

    public static Intent newStartIntent(Context context,@Nullable ArrayList<Poster> imageList,
                                        String title, int color) {
        Intent intent = new Intent(context, MoreImagesActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_IMAGE_LIST, imageList);
        intent.putExtra(EXTRA_TTILE, title);
        intent.putExtra(EXTRA_COLOR, color);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_images);

        ArrayList<Poster> imageList = getIntent().getParcelableArrayListExtra(EXTRA_IMAGE_LIST);
        String title = getIntent().getStringExtra(EXTRA_TTILE);
        int color = getIntent().getIntExtra(EXTRA_COLOR, R.color.colorPrimary);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.more_images_container,
                            MoreImagesFragment.newInstance(imageList, title, color))
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
