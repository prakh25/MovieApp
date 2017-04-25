package com.example.prakhar.mobile.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.utils.Constants;
import com.example.prakhar.mobile.utils.Utils;

import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_container, HomeFragment.newInstance())
                    .commitAllowingStateLoss();
        }

        if (countryCode.getString(Constants.COUNTRY_CODE, null) == null) {
            String country = Utils.getUserCountry(this);
            countryCode.edit().putString(Constants.COUNTRY_CODE, country).apply();
        }
        Timber.i(countryCode.getString(Constants.COUNTRY_CODE, null));
    }
}