package com.example.core.ui.base;

/**
 * Created by Prakhar on 2/22/2017.
 */

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showEmpty();

    void showError(String errorMessage);

    void showMessageLayout(boolean show);
}
