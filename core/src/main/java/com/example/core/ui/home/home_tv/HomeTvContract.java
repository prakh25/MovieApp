package com.example.core.ui.home.home_tv;


import com.example.core.model.home.tv.TvResult;
import com.example.core.ui.base.BaseView;

import java.util.List;

/**
 * Created by Prakhar on 4/19/2017.
 */

public interface HomeTvContract {
    interface ViewActions {
        void onInitialRequest();
    }

    interface HomeTvView extends BaseView {
        void showOnAirToday(List<TvResult> tvResultList);

        void showPopularTvShows(List<TvResult> tvResultList);

        void showTopRatedTvShows(List<TvResult> tvResultList);
    }
}
