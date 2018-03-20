package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 对话框单个按钮的视图
 * Created by hupei on 2017/3/30.
 *
 * @hide
 * @deprecated
 */
public final class SingleButton extends ScaleTextView implements Controller.OnClickListener {
    private CircleParams mCircleParams;
    private ButtonParams mButtonParams;

    public SingleButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        mCircleParams = params;
        mButtonParams = params.negativeParams != null ? params.negativeParams : params
                .positiveParams;

        setTextSize(mButtonParams.textSize);
        setHeight(mButtonParams.height);
        handleStyle();

        //如果取消按钮没有背景色，则使用默认色
        int backgroundColor = mButtonParams.backgroundColor != 0
                ? mButtonParams.backgroundColor : params.dialogParams.backgroundColor == 0
                ? CircleColor.bgDialog : params.dialogParams.backgroundColor;

        int radius = params.dialogParams.radius;
        SelectorBtn selectorBtn = new SelectorBtn(backgroundColor
                , mButtonParams.backgroundColorPress != 0
                ? mButtonParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                , 0, 0, radius, radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(selectorBtn);
        } else {
            setBackgroundDrawable(selectorBtn);
        }
    }

    private void handleStyle() {
        setText(mButtonParams.text);
        setEnabled(!mButtonParams.disable);
        //禁用按钮则改变文字颜色
        setTextColor(mButtonParams.disable ? mButtonParams.textColorDisable : mButtonParams.textColor);
    }

    public void regOnClickListener(OnClickListener onClickListener) {
        setOnClickListener(onClickListener);
    }

    public void refreshText() {
        if (mButtonParams == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleStyle();
            }
        });
    }

    @Override
    public void onClick(View view, int which) {
        if (which == Controller.BUTTON_NEGATIVE) {
            if (mCircleParams.clickNegativeListener != null) {
                mCircleParams.clickNegativeListener.onClick(this);
            }
        } else if (which == Controller.BUTTON_POSITIVE) {
            if (mCircleParams.clickPositiveListener != null) {
                mCircleParams.clickPositiveListener.onClick(this);
            }
        }


    }
}
