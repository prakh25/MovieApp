package com.example.prakhar.movieapp.ui.search;

import com.example.prakhar.movieapp.model.search.SearchResult;
import com.example.prakhar.movieapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/20/2017.
 */

class SearchContract {

    interface ViewActions{
        void onSearchQueryEntered(String query);
    }

    interface SearchView extends BaseView {
        void showSearch(List<SearchResult> searchResultList);
    }
}
