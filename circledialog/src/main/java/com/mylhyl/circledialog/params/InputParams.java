package com.mylhyl.circledialog.params;

import android.graphics.Color;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/31.
 */
public class InputParams extends BaseParams {
    public int[] margins = CircleDimen.inputMargins;//输入框与body视图的距离
    public int inputHeight = CircleDimen.inputHeight;//输入框的高度
    public String hintText;//输入框提示语
    public int hintTextColor = CircleColor.content;//输入框提示语颜色
    public int inputBackgroundResourceId;//输入框背景资源文件ID
    public int strokeWidth = 1;//输入框边框线条粗细
    public int strokeColor = CircleColor.inputStroke;//输入框边框线条颜色
    public int inputBackgroundColor = Color.TRANSPARENT;//输入框的背景

    //backgroundColor   body视图的背景色
    public InputParams() {
        textSize = CircleDimen.contentTextSize;
        textColor = CircleColor.title;//标题字体颜色
    }
}
