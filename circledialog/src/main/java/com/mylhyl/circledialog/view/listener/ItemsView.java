package com.mylhyl.circledialog.view.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by hupei on 2018/4/18.
 */

public interface ItemsView {
    void refreshItems();

    void regOnItemClickListener(AdapterView.OnItemClickListener listener);

    void regOnItemClickListener(OnRvItemClickListener listener);

    View getView();
}
