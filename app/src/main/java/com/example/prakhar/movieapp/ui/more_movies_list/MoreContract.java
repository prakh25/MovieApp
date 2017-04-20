package com.example.prakhar.movieapp.ui.more_movies_list;

import com.example.prakhar.movieapp.model.more.MoreListResult;
import com.example.prakhar.movieapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/14/2017.
 */

class MoreContract {

    interface ViewActions {
        void mostPopularListRequested();

        void onTopRatedMoviesRequested();

        void onBoxOfficeRequested();

        void onPopularMovieByGenreRequested(Integer genreId);

        void onListEndReached(int pageNo, int id);
    }

    interface MoreView extends BaseView {
        void showMoviesList(List<MoreListResult> resultList);
    }
}
