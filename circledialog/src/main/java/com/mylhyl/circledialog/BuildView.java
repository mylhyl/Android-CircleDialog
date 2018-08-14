package com.mylhyl.circledialog;

import android.view.View;

import com.mylhyl.circledialog.view.listener.ButtonView;

/**
 * Created by hupei on 2017/3/29.
 */

public interface BuildView {

    <T> T getBodyView();

    void buildBodyView();

    View getRootView();

    ButtonView buildButton();

    void refreshContent();

    void refreshButton();
}
