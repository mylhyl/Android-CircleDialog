package com.mylhyl.circledialog.view;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

import com.mylhyl.circledialog.scale.ScaleUtils;

/**
 * Created by hupei on 2017/3/31.
 */
class ScaleEditText extends android.support.v7.widget.AppCompatEditText {
    public ScaleEditText(Context context) {
        super(context);
        config();
    }

    private void config() {
        requestFocus();
        setFocusable(true);
        setFocusableInTouchMode(true);
        setGravity(Gravity.TOP | Gravity.LEFT);
    }

    @Override
    public void setTextSize(float size) {
        int dimenTextSize = ScaleUtils.scaleValue((int) size);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize);
    }

    @Override
    public void setHeight(int pixels) {
        int dimenHeight = ScaleUtils.scaleValue(pixels);
        super.setHeight(dimenHeight);
    }

    public void setAutoPadding(int left, int top, int right, int bottom) {
        int dimenLeft = ScaleUtils.scaleValue(left);
        int dimenTop = ScaleUtils.scaleValue(top);
        int dimenRight = ScaleUtils.scaleValue(right);
        int dimenBottom = ScaleUtils.scaleValue(bottom);
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom);
    }
}
