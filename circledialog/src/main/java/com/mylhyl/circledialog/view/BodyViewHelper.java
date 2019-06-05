package com.mylhyl.circledialog.view;

import android.os.Build;
import android.view.View;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;

/**
 * Created by hupei on 2019/6/5 17:22.
 */
class BodyViewHelper {

    CircleParams circleParams;

    public BodyViewHelper(CircleParams circleParams) {
        this.circleParams = circleParams;
    }

    public void handleBackground(View view, int backgroundColor) {
        DialogParams dialogParams = circleParams.dialogParams;
        TitleParams titleParams = circleParams.titleParams;
        ButtonParams negativeParams = circleParams.negativeParams;
        ButtonParams positiveParams = circleParams.positiveParams;
        ButtonParams neutralParams = circleParams.neutralParams;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundColor(backgroundColor);
        } else {
            // 有标题没按钮则底部圆角
            if (titleParams != null && negativeParams == null && positiveParams == null
                    && neutralParams == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                            dialogParams.radius));
                } else {
                    view.setBackgroundDrawable(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                            dialogParams.radius));
                }
            }
            // 没标题有按钮则顶部圆角
            else if (titleParams == null
                    && (negativeParams != null || positiveParams != null || neutralParams != null)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams
                            .radius, 0, 0));
                } else {
                    view.setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius,
                            dialogParams.radius, 0, 0));
                }
            }
            // 没标题没按钮则全部圆角
            else if (titleParams == null && negativeParams == null && positiveParams == null
                    && neutralParams == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
                } else {
                    view.setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius));
                }
            }
            // 有标题有按钮则不用考虑圆角
            else {
                view.setBackgroundColor(backgroundColor);
            }
        }
    }
}
