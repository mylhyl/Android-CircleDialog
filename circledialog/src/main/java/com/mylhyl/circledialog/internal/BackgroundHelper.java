package com.mylhyl.circledialog.internal;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.drawable.CircleDrawableSelector;

/**
 * Created by hupei on 2019/6/5 17:22.
 */
public class BackgroundHelper {

    public static void handleTitleBackground(View view, int backgroundColor, DialogParams dialogParams) {
        if (Controller.SDK_LOLLIPOP) {
            view.setBackgroundColor(backgroundColor);
        } else {
            int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
            CircleDrawable background = new CircleDrawable(backgroundColor, radius, radius, 0, 0);
            handleBackground(view, background);
        }
    }

    public static void handleBodyBackground(View view, int backgroundColor, CircleParams circleParams) {
        TitleParams titleParams = circleParams.titleParams;
        ButtonParams negativeParams = circleParams.negativeParams;
        ButtonParams positiveParams = circleParams.positiveParams;
        ButtonParams neutralParams = circleParams.neutralParams;

        int radius = Controller.dp2px(view.getContext(), circleParams.dialogParams.radius);

        if (Controller.SDK_LOLLIPOP) {
            view.setBackgroundColor(backgroundColor);
            return;
        }
        // 有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null && neutralParams == null) {
            CircleDrawable circleDrawable = new CircleDrawable(backgroundColor, 0, 0, radius, radius);
            handleBackground(view, circleDrawable);
        }
        // 没标题有按钮则顶部圆角
        else if (titleParams == null && (negativeParams != null || positiveParams != null || neutralParams != null)) {
            CircleDrawable circleDrawable = new CircleDrawable(backgroundColor, radius, radius, 0, 0);
            handleBackground(view, circleDrawable);
        }
        // 没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null && neutralParams == null) {
            CircleDrawable circleDrawable = new CircleDrawable(backgroundColor, radius);
            handleBackground(view, circleDrawable);
        }
        // 有标题有按钮则不用考虑圆角
        else {
            view.setBackgroundColor(backgroundColor);
        }
    }

    public static void handleNegativeButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        DialogParams dialogParams = circleParams.dialogParams;
        ButtonParams negativeParams = circleParams.negativeParams;
        CircleDrawableSelector selectorBtn;
        if (Controller.SDK_LOLLIPOP) {
            selectorBtn = new CircleDrawableSelector(backgroundColor, negativeParams.backgroundColorPress != 0 ?
                    negativeParams.backgroundColorPress : dialogParams.backgroundColorPress);
        } else {
            int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
            selectorBtn = new CircleDrawableSelector(backgroundColor, negativeParams.backgroundColorPress != 0 ?
                    negativeParams.backgroundColorPress : dialogParams.backgroundColorPress,
                    // 右边没按钮则右下是圆角
                    0, 0,
                    (circleParams.neutralParams == null && circleParams.positiveParams == null) ? radius : 0, radius);
        }
        handleBackground(view, selectorBtn);
    }

    public static void handlePositiveButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        DialogParams dialogParams = circleParams.dialogParams;
        ButtonParams positiveParams = circleParams.positiveParams;
        CircleDrawableSelector selectorBtn;
        if (Controller.SDK_LOLLIPOP) {
            selectorBtn = new CircleDrawableSelector(backgroundColor, positiveParams.backgroundColorPress != 0 ?
                    positiveParams.backgroundColorPress : dialogParams.backgroundColorPress);
        } else {
            int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
            selectorBtn = new CircleDrawableSelector(backgroundColor, positiveParams.backgroundColorPress != 0 ?
                    positiveParams.backgroundColorPress : dialogParams.backgroundColorPress,
                    // 左边没按钮则左下是圆角
                    0, 0, radius,
                    (circleParams.negativeParams == null && circleParams.neutralParams == null) ? radius : 0);
        }

        handleBackground(view, selectorBtn);
    }

    public static void handleNeutralButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        DialogParams dialogParams = circleParams.dialogParams;
        ButtonParams neutralParams = circleParams.neutralParams;
        CircleDrawableSelector selectorBtn;
        if (Controller.SDK_LOLLIPOP) {
            selectorBtn = new CircleDrawableSelector(backgroundColor, neutralParams.backgroundColorPress != 0 ?
                    neutralParams.backgroundColorPress : dialogParams.backgroundColorPress);
        } else {
            int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
            selectorBtn = new CircleDrawableSelector(backgroundColor, neutralParams.backgroundColorPress != 0 ?
                    neutralParams.backgroundColorPress : dialogParams.backgroundColorPress,
                    // 左右没按钮则左下右下是圆角
                    0, 0, circleParams.positiveParams == null ? radius : 0,
                    circleParams.negativeParams == null ? radius : 0);
        }
        handleBackground(view, selectorBtn);
    }

    public static void handleItemsNegativeButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        DialogParams dialogParams = circleParams.dialogParams;
        ButtonParams negativeParams = circleParams.negativeParams;
        int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
        // 右边没按钮则右下是圆角
        int rightRadius = (circleParams.neutralParams == null && circleParams.positiveParams == null) ? radius : 0;
        CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundColor,
                negativeParams.backgroundColorPress != 0 ?
                        negativeParams.backgroundColorPress :
                        dialogParams.backgroundColorPress, radius, rightRadius, rightRadius, radius);

        handleBackground(view, selectorBtn);
    }

    public static void handleItemsPositiveButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        DialogParams dialogParams = circleParams.dialogParams;
        ButtonParams positiveParams = circleParams.positiveParams;
        int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
        // 左边没按钮则左下是圆角
        int leftRadius = (circleParams.negativeParams == null && circleParams.neutralParams == null) ? radius : 0;
        CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundColor,
                positiveParams.backgroundColorPress != 0 ?
                        positiveParams.backgroundColorPress :
                        dialogParams.backgroundColorPress, leftRadius, radius, radius, leftRadius);

        handleBackground(view, selectorBtn);
    }

    public static void handleItemsNeutralButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        DialogParams dialogParams = circleParams.dialogParams;
        ButtonParams neutralParams = circleParams.neutralParams;
        int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
        // 左右没按钮则左下右下是圆角
        int leftRadius = circleParams.negativeParams == null ? radius : 0;
        int rightRadius = circleParams.positiveParams == null ? radius : 0;
        CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundColor,
                neutralParams.backgroundColorPress != 0 ?
                        neutralParams.backgroundColorPress :
                        dialogParams.backgroundColorPress, leftRadius, rightRadius, rightRadius, leftRadius);

        handleBackground(view, selectorBtn);
    }

    public static void handleBackground(View view, Drawable background) {
        if (Controller.SDK_JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public static void handleCircleBackground(View view, int backgroundColor, DialogParams dialogParams) {
        int radius = Controller.dp2px(view.getContext(), dialogParams.radius);
        CircleDrawable background = new CircleDrawable(backgroundColor, radius, radius, radius, radius);
        if (Controller.SDK_JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

}
