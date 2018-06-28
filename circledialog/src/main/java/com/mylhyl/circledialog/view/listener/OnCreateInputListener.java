package com.mylhyl.circledialog.view.listener;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hupei on 2018/6/13.
 */

public interface OnCreateInputListener {
    void onCreateText(RelativeLayout inputLayout, EditText inputView, TextView counterView);
}
