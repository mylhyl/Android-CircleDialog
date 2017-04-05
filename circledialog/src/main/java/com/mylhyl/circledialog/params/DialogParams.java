package com.mylhyl.circledialog.params;

import android.graphics.Color;
import android.view.Gravity;

import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * 对话框参数
 * Created by hupei on 2017/3/30.
 */
public class DialogParams implements Serializable {
    /**
     * 对话框的位置
     */
    public int gravity = Gravity.NO_GRAVITY;
    /**
     * 是否触摸外部关闭
     */
    public boolean canceledOnTouchOutside = true;
    /**
     * 返回键是否关闭
     */
    public boolean cancelable = true;
    /**
     * 对话框透明度，范围：0-1；1不透明
     */
    public float alpha = 1f;
    /**
     * 对话框宽度，范围：0-1；1整屏宽
     */
    public float width = 0.9f;
    /**
     * 对话框与屏幕边距
     */
    public int[] mPadding;
    /**
     * 对话框弹出动画,StyleRes
     */
    public int animStyle;
    /**
     * 对话框刷新动画，AnimRes
     */
    public int refreshAnimation;
    /**
     * 对话框背景是否昏暗，默认true
     */
    public boolean isDimEnabled = true;
    /**
     * 对话框的背景色透明，因为列表模式情况，内容与按钮中间有距离
     */
    private int backgroundColor = Color.TRANSPARENT;
    /**
     * 对话框的圆角半径
     */
    public int radius = CircleDimen.RADIUS;
    /**
     * 对话框 x 坐标偏移
     */
    public int xOff;
    /**
     * 对话框 y 坐标偏移
     */
    public int yOff;

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
