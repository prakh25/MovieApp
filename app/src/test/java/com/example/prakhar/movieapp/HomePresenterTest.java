package com.example.prakhar.movieapp;

import android.text.TextUtils;

import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.home.home_movie.HomeMovieContract;
import com.example.prakhar.movieapp.ui.home.home_movie.HomeMoviePresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Prakhar on 2/23/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class HomePresenterTest {

    @Mock
    private DataManager dataManager;

    @Mock
    private HomeMovieContract.HomeMovieView homeMovieView;

    private HomeMoviePresenter homeMoviePresenter;

    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        homeMoviePresenter = new HomeMoviePresenter(dataManager);
        homeMoviePresenter.attachView(homeMovieView);
    }


    @After
    public void tearDown() {
        homeMoviePresenter.detachView();
    }

}
