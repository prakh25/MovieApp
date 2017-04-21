package com.example.prakhar.movieapp.ui.home.home_movie;

import com.example.prakhar.movieapp.model.home.movie.Result;
import com.example.prakhar.movieapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 2/22/2017.
 */

public interface HomeMovieContract {

    interface ViewActions {

        void onInitializeRequest();
    }

    interface HomeMovieView extends BaseView {

        void showComingSoonMovies(List<Result> resultList);

        void showNowPlayingMovies(List<Result> resultList);

        void showLinks();
    }
}
