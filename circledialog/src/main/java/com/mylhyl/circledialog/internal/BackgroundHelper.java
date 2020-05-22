package com.mylhyl.circledialog.internal;

import android.content.Context;
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

public enum BackgroundHelper {
    INSTANCE;

    private CircleParams circleParams;
    private int backgroundColorPress;
    private int radius;

    public void init(Context context, CircleParams circleParams) {
        this.circleParams = circleParams;
        DialogParams dialogParams = circleParams.dialogParams;
        this.radius = Controller.dp2px(context, dialogParams.radius);
        this.backgroundColorPress = dialogParams.backgroundColorPress;
    }

    public void handleTitleBackground(View view, int backgroundColor) {
        if (Controller.SDK_LOLLIPOP) {
            view.setBackgroundColor(backgroundColor);
        } else {
            CircleDrawable background = new CircleDrawable(backgroundColor, radius, radius, 0, 0);
            handleBackground(view, background);
        }
    }

    public void handleBodyBackground(View view, int backgroundColor) {
        TitleParams titleParams = circleParams.titleParams;
        ButtonParams negativeParams = circleParams.negativeParams;
        ButtonParams positiveParams = circleParams.positiveParams;
        ButtonParams neutralParams = circleParams.neutralParams;

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

    public void handleNegativeButtonBackground(View view, int backgroundColor) {
        ButtonParams negativeParams = circleParams.negativeParams;
        CircleDrawableSelector selectorBtn;
        if (Controller.SDK_LOLLIPOP) {
            selectorBtn = new CircleDrawableSelector(backgroundColor, negativeParams.backgroundColorPress != 0 ?
                    negativeParams.backgroundColorPress : backgroundColorPress);
        } else {
            selectorBtn = new CircleDrawableSelector(backgroundColor, negativeParams.backgroundColorPress != 0 ?
                    negativeParams.backgroundColorPress : backgroundColorPress,
                    // 右边没按钮则右下是圆角
                    0, 0,
                    (circleParams.neutralParams == null && circleParams.positiveParams == null) ? radius : 0, radius);
        }

        handleBackground(view, selectorBtn);
    }

    public void handlePositiveButtonBackground(View view, int backgroundColor) {
        ButtonParams positiveParams = circleParams.positiveParams;
        CircleDrawableSelector selectorBtn;
        if (Controller.SDK_LOLLIPOP) {
            selectorBtn = new CircleDrawableSelector(backgroundColor, positiveParams.backgroundColorPress != 0 ?
                    positiveParams.backgroundColorPress : backgroundColorPress);
        } else {
            selectorBtn = new CircleDrawableSelector(backgroundColor, positiveParams.backgroundColorPress != 0 ?
                    positiveParams.backgroundColorPress : backgroundColorPress,
                    // 左边没按钮则左下是圆角
                    0, 0, radius,
                    (circleParams.negativeParams == null && circleParams.neutralParams == null) ? radius : 0);
        }

        handleBackground(view, selectorBtn);
    }

    public void handleNeutralButtonBackground(View view, int backgroundColor) {
        ButtonParams neutralParams = circleParams.neutralParams;
        CircleDrawableSelector selectorBtn;
        if (Controller.SDK_LOLLIPOP) {
            selectorBtn = new CircleDrawableSelector(backgroundColor, neutralParams.backgroundColorPress != 0 ?
                    neutralParams.backgroundColorPress : backgroundColorPress);
        } else {
            selectorBtn = new CircleDrawableSelector(backgroundColor, neutralParams.backgroundColorPress != 0 ?
                    neutralParams.backgroundColorPress : backgroundColorPress,
                    // 左右没按钮则左下右下是圆角
                    0, 0, circleParams.positiveParams == null ? radius : 0,
                    circleParams.negativeParams == null ? radius : 0);
        }
        handleBackground(view, selectorBtn);
    }

    public void handleItemsNegativeButtonBackground(View view, int backgroundColor) {
        ButtonParams negativeParams = circleParams.negativeParams;
        // 右边没按钮则右下是圆角
        int rightRadius = (circleParams.neutralParams == null && circleParams.positiveParams == null) ? radius : 0;
        CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundColor,
                negativeParams.backgroundColorPress != 0 ?
                        negativeParams.backgroundColorPress :
                        backgroundColorPress, radius, rightRadius, rightRadius, radius);

        handleBackground(view, selectorBtn);
    }

    public void handleItemsPositiveButtonBackground(View view, int backgroundColor) {
        ButtonParams positiveParams = circleParams.positiveParams;
        // 左边没按钮则左下是圆角
        int leftRadius = (circleParams.negativeParams == null && circleParams.neutralParams == null) ? radius : 0;
        CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundColor,
                positiveParams.backgroundColorPress != 0 ?
                        positiveParams.backgroundColorPress :
                        backgroundColorPress, leftRadius, radius, radius, leftRadius);

        handleBackground(view, selectorBtn);
    }

    public void handleItemsNeutralButtonBackground(View view, int backgroundColor) {
        ButtonParams neutralParams = circleParams.neutralParams;
        // 左右没按钮则左下右下是圆角
        int leftRadius = circleParams.negativeParams == null ? radius : 0;
        int rightRadius = circleParams.positiveParams == null ? radius : 0;
        CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundColor,
                neutralParams.backgroundColorPress != 0 ?
                        neutralParams.backgroundColorPress :
                        backgroundColorPress, leftRadius, rightRadius, rightRadius, leftRadius);

        handleBackground(view, selectorBtn);
    }

    public void handleBackground(View view, Drawable background) {
        if (Controller.SDK_JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public void handleCircleBackground(View view, int backgroundColor) {
        CircleDrawable background = new CircleDrawable(backgroundColor, radius, radius, radius, radius);
        if (Controller.SDK_JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }
}
