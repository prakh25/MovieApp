package com.example.prakhar.mobile.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.prakhar.mobile.R;

/**
 * Created by Prakhar on 3/20/2017.
 */

public class SearchActivity extends AppCompatActivity {

    public static Intent newStartIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.search_container, SearchFragment.newInstance())
                    .commit();
        }
    }
}
