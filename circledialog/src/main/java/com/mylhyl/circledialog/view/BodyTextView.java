package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 对话框纯文本视图
 * Created by hupei on 2017/3/30.
 */
class BodyTextView extends ScaleTextView {
    private CircleParams mParams;

    public BodyTextView(Context context, CircleParams params) {
        super(context);
        this.mParams = params;
        init(params);
    }

    private void init(CircleParams params) {
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        TextParams textParams = params.textParams;
        ButtonParams negativeParams = params.negativeParams;
        ButtonParams positiveParams = params.positiveParams;

        setGravity(textParams.gravity);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = textParams.backgroundColor != 0 ? textParams.backgroundColor :
                CircleColor.bgDialog;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            }
        }
        //没标题有按钮则顶部圆角
        else if (titleParams == null && (negativeParams != null || positiveParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams
                        .radius, 0, 0));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius,
                        dialogParams.radius, 0, 0));
            }
        }
        //没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius));
            }
        }
        //有标题有按钮则不用考虑圆角
        else setBackgroundColor(backgroundColor);

//        setHeight(textParams.height);
        setMinHeight(textParams.height);
        setTextColor(textParams.textColor);
        setTextSize(textParams.textSize);
        setText(textParams.text);

        int[] padding = textParams.padding;
        if (padding != null) setAutoPadding(padding[0], padding[1], padding[2], padding[3]);
    }

    public void refreshText() {
        if (mParams.textParams == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                setText(mParams.textParams.text);
            }
        });
    }
}
