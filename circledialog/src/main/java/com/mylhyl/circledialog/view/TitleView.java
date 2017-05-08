package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;

import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 对话框标题
 * Created by hupei on 2017/3/29.
 */
class TitleView extends ScaleTextView {

    public TitleView(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;

        setGravity(titleParams.gravity);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = titleParams.backgroundColor != 0 ? titleParams.backgroundColor :
                CircleColor.bgDialog;

        //有内容则顶部圆角
        if (params.textParams != null || params.itemsParams != null || params.progressParams != null
                || params.inputParams != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams
                        .radius, 0, 0));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius,
                        dialogParams.radius, 0, 0));
            }
        }
        //无内容则全部圆角
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius));
            }
        }

        setHeight(titleParams.height);
        setTextColor(titleParams.textColor);
        setTextSize(titleParams.textSize);
        setText(titleParams.text);
    }
}
