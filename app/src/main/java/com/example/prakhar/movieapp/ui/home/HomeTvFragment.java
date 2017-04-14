package com.example.prakhar.movieapp.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prakhar.movieapp.R;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomeTvFragment extends Fragment {

    public HomeTvFragment() {
    }

    public static HomeTvFragment newInstance() {

        Bundle args = new Bundle();

        HomeTvFragment fragment = new HomeTvFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_tv, container, false);
    }
}
