package com.mylhyl.circledialog;

import android.view.View;

/**
 * Created by hupei on 2017/3/29.
 */

public interface BuildView {
    void buildRoot();

    void buildTitle();

    void buildText();

    void buildItems();

    void buildMultipleButton();

    void buildSingleButton();

    void buildItemsButton();

    void refreshText();

    void refreshItems();

    View getView();
}
