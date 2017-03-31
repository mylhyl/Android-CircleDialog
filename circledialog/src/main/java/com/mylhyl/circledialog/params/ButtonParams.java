package com.mylhyl.circledialog.params;

import android.view.View;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

/**
 * Created by hupei on 2017/3/30.
 */

public abstract class ButtonParams extends TitleParams {
    public abstract void dismiss();

    public View.OnClickListener listener;//按钮点击事件
    public OnInputClickListener inputListener;//输入框确定事件
    public int topMargin;//按钮框与顶部距离


    //backgroundColor   按钮背景颜色
    //text    按钮文本
    public ButtonParams() {
        textColor = CircleColor.button;//按钮文本颜色
        textSize = CircleDimen.footerTextSize;//按钮文本大小
        height = CircleDimen.footerHeight;//按钮高度
    }
}
