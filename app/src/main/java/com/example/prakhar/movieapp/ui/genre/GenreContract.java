package com.example.prakhar.movieapp.ui.genre;

import com.example.prakhar.movieapp.model.genre.Genre;
import com.example.prakhar.movieapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/27/2017.
 */

public class GenreContract {

    interface ViewActions {
        void getGenreList();
    }

    interface GenreView extends BaseView {
        void showGenreList(List<Genre> genreList);
    }
}
