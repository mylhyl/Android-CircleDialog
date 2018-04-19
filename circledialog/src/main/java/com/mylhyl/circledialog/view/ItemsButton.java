package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.scale.ScaleUtils;

/**
 * 列表对话框的取消按钮视图
 * Created by hupei on 2017/3/30.
 *
 * @hide
 */
public final class ItemsButton extends ScaleTextView implements Controller.OnClickListener {
    CircleParams params;

    public ItemsButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(final CircleParams params) {
        this.params = params;
        ButtonParams negativeParams = params.negativeParams;
        final ButtonParams buttonParams = negativeParams != null ? negativeParams : params
                .positiveParams;
        //为列表显示时，设置列表与按钮之间的距离
        if (buttonParams.topMargin == -1)
            buttonParams.topMargin = CircleDimen.BUTTON_ITEMS_MARGIN;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = ScaleUtils.scaleValue(buttonParams.topMargin);
        setLayoutParams(layoutParams);

        setClickable(true);

        setText(buttonParams.text);
        setTextSize(buttonParams.textSize);
        setTextColor(buttonParams.textColor);
        setHeight(buttonParams.height);

        //如果取消按钮没有背景色，则使用默认色
        int backgroundColor = buttonParams.backgroundColor != 0
                ? buttonParams.backgroundColor : params.dialogParams.backgroundColor;
        int radius = params.dialogParams.radius;
        final SelectorBtn selectorBtn = new SelectorBtn(backgroundColor
                , buttonParams.backgroundColorPress != 0
                ? buttonParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                , radius, radius, radius, radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(selectorBtn);
        } else {
            setBackgroundDrawable(selectorBtn);
        }
    }

    public void regOnClickListener(OnClickListener onClickListener) {

        setOnClickListener(onClickListener);
    }


    @Override
    public void onClick(View view, int which) {
        if (params.clickPositiveListener != null)
            params.clickPositiveListener.onClick(this);
        else if (params.clickNegativeListener != null)
            params.clickNegativeListener.onClick(this);
    }
}
