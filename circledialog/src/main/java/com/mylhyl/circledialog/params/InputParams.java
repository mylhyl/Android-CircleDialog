package com.mylhyl.circledialog.params;

import android.graphics.Color;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/31.
 */
public class InputParams extends BaseParams {
    public int[] margins = CircleDimen.inputMargins;
    public int inputHeight = CircleDimen.inputHeight;//输入框的高度
    public String hintText;
    public int hintTextColor = CircleColor.content;
    public int inputBackgroundResourceId;
    public int strokeWidth = 1;
    public int strokeColor = CircleColor.inputStroke;
    public int inputBackgroundColor = Color.TRANSPARENT;

    public InputParams() {
        textSize = CircleDimen.contentTextSize;
        textColor = CircleColor.title;//标题字体颜色
    }
}
