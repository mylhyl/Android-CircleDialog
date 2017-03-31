package com.mylhyl.circledialog.params;

import android.graphics.Color;
import android.view.Gravity;
import android.view.animation.Animation;

import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * Created by hupei on 2017/3/30.
 */

public class DialogParams implements Serializable {
    public int gravity = Gravity.NO_GRAVITY;//对话框的位置
    public boolean canceledOnTouchOutside = true;//是否触摸外部关闭
    public boolean cancelable = true;//返回键是否关闭
    public float alpha = 1f;//对话框透明度，范围：0-1；1不透明
    public float width = 0.9f;//对话框宽度，范围：0-1；1整屏宽
    public int[] mPadding;//对话框与屏幕边距
    public int animStyle;//对话框弹出动画,style动画资源
    public boolean isDimEnabled = true;//对话框背景是否昏暗，默认true
    private int backgroundColor = Color.TRANSPARENT;//对话框的背景色透明，因为列表模式情况，内容与按钮中间有距离
    public int radius = CircleDimen.radius;//对话框的圆角半径
    public int x, y;
    public Animation refreshAnimation;//内容刷新动画

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
