package com.mylhyl.circledialog.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mylhyl.circledialog.scale.ScaleLayoutConfig;


/**
 * Created by hupei on 2018/6/13.
 */
class ScaleRelativeLayout extends RelativeLayout {

    public ScaleRelativeLayout(Context context) {
        super(context);
        ScaleLayoutConfig.init(context.getApplicationContext());
    }
}
