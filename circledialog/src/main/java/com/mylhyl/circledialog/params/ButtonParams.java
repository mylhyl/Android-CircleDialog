package com.mylhyl.circledialog.params;

import android.view.View;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

import java.io.Serializable;

/**
 * 按钮参数
 * Created by hupei on 2017/3/30.
 */
public abstract class ButtonParams implements Serializable {
    public abstract void dismiss();

    /**
     * 按钮点击事件
     */
    public View.OnClickListener listener;
    /**
     * 输入框确定事件
     */
    public OnInputClickListener inputListener;
    /**
     * 按钮框与顶部距离
     */
    public int topMargin;
    /**
     * 按钮文本颜色
     */
    public int textColor = CircleColor.button;
    /**
     * 按钮文本大小
     */
    public int textSize = CircleDimen.FOOTER_TEXT_SIZE;
    /**
     * 按钮高度
     */
    public int height = CircleDimen.FOOTER_HEIGHT;
    /**
     * 按钮背景颜色
     */
    public int backgroundColor;
    /**
     * 按钮文本
     */
    public String text;
}
