package com.example.core.ui.more_movies_list;


import com.example.core.model.more_movie_list.MovieListResult;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/14/2017.
 */

public interface MovieListContract {

    interface ViewActions {
        void mostPopularListRequested();

        void onTopRatedMoviesRequested();

        void onBoxOfficeRequested();

        void onPopularMovieByGenreRequested(Integer genreId);

        void onListEndReached(int pageNo, int id);
    }

    interface MoreView extends BaseView {
        void showMoviesList(List<MovieListResult> resultList);
    }
}
