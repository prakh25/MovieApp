package com.example.core.ui.people_detail;


import com.example.core.model.people_detail.ExternalIds;
import com.example.core.model.person_search.KnownFor;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 4/3/2017.
 */

public interface PeopleDetailContract {

    interface ViewActions {
        void onPersonDetailRequested(Integer personId);
    }

    interface PeopleDetailView extends BaseView {
        void showPeopleDetailHeader(String name, ExternalIds externalIds);

        void showPersonBio(String biography);

        void showPersonKnownFor(List<KnownFor> knownForList);

        void showPersonalInfo(Integer gender, String birthday,
                              String deathDay, String placeOfBirth, List<String> alsoKnownAs);

        void showExternalLinks(String personHomePage, Integer tmdbId, String personName);
    }
}
