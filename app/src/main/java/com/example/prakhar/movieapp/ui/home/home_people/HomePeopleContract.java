package com.example.prakhar.movieapp.ui.home.home_people;

import com.example.prakhar.movieapp.model.person_search.PersonSearchResult;
import com.example.prakhar.movieapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomePeopleContract {

    interface ViewActions {
        void onInitialisedRequest();

        void onListEndReached(int page);
    }

    interface HomePeopleView extends BaseView {
        void showPopularPeople(List<PersonSearchResult> resultList);
    }
}
