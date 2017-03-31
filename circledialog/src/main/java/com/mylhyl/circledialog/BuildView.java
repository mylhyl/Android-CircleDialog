package com.mylhyl.circledialog;

import android.view.View;

/**
 * Created by hupei on 2017/3/29.
 */

public interface BuildView {
    void buildRoot();

    void buildTitle();

    void buildText();

    void refreshText();

    void buildItems();

    void buildItemsButton();

    void refreshItems();

    void buildProgress();

    void refreshProgress();

    void buildInput();

    void buildMultipleButton();

    void buildSingleButton();

    View getView();
}
