package com.mylhyl.circledialog.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.auto.AutoLayoutConfig;


/**
 * Created by hupei on 2017/3/29.
 */
class AutoLinearLayout extends LinearLayout {
    public AutoLinearLayout(Context context) {
        this(context, null);
    }

    public AutoLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AutoLayoutConfig.init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        AutoLayoutConfig.init(context);
    }
}
