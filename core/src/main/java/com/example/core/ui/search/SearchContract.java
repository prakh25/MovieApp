package com.example.core.ui.search;


import com.example.core.model.search.SearchResult;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 3/20/2017.
 */

public interface SearchContract {

    interface ViewActions{
        void onSearchQueryEntered(String query);
    }

    interface SearchView extends BaseView {
        void showSearch(List<SearchResult> searchResultList);
    }
}
