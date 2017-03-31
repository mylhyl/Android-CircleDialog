package com.mylhyl.circledialog.res.drawable;

import android.graphics.drawable.GradientDrawable;

/**
 * 输入框背景
 * Created by hupei on 2017/3/31.
 */

public class InputDrawable extends GradientDrawable {
    public InputDrawable(int strokeWidth, int strokeColor, int backgroundColor) {
        setColor(backgroundColor);//内部填充颜色
        setStroke(strokeWidth, strokeColor);//边框宽度,边框颜色
    }
}
