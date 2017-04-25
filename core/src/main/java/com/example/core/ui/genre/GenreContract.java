package com.example.core.ui.genre;


import com.example.core.model.genre.Genre;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/27/2017.
 */

public interface GenreContract {

    interface ViewActions {
        void getGenreList();
    }

    interface GenreView extends BaseView {
        void showGenreList(List<Genre> genreList);
    }
}
