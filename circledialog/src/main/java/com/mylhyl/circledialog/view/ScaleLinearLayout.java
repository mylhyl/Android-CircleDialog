package com.mylhyl.circledialog.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.scale.ScaleLayoutConfig;


/**
 * Created by hupei on 2017/3/29.
 */
class ScaleLinearLayout extends LinearLayout {
    public ScaleLinearLayout(Context context) {
        this(context, null);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ScaleLayoutConfig.init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScaleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ScaleLayoutConfig.init(context);
    }
}
