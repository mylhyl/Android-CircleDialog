package com.mylhyl.circledialog.view;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.mylhyl.circledialog.auto.AutoUtils;

/**
 * Created by hupei on 2017/3/29.
 */

class AutoTextView extends TextView {
    public AutoTextView(Context context) {
        super(context);
        config();
    }

    private void config() {
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setHeight(int pixels) {
        int dimenHeight = AutoUtils.scaleValue(pixels);
        super.setHeight(dimenHeight);
    }

    @Override
    public void setTextSize(float size) {
        int dimenTextSize = AutoUtils.scaleValue((int) size);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize);
    }

    public void setAutoPadding(int left, int top, int right, int bottom) {
        int dimenLeft = AutoUtils.scaleValue(left);
        int dimenTop = AutoUtils.scaleValue(top);
        int dimenRight = AutoUtils.scaleValue(right);
        int dimenBottom = AutoUtils.scaleValue(bottom);
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom);
    }
}
