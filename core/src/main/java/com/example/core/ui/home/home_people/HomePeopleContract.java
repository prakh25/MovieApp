package com.example.core.ui.home.home_people;


import com.example.core.model.person_search.PersonSearchResult;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 4/8/2017.
 */

public interface HomePeopleContract {

    interface ViewActions {
        void onInitialisedRequest();

        void onListEndReached(int page);
    }

    interface HomePeopleView extends BaseView {
        void showPopularPeople(List<PersonSearchResult> resultList);
    }
}
