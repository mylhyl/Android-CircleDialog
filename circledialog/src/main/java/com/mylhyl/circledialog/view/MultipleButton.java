package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
public class MultipleButton extends ScaleLinearLayout implements Controller.OnClickListener {
    private CircleParams mCircleParams;
    private ButtonParams mNegativeParams;
    private ButtonParams mPositiveParams;
    private ButtonParams mNeutralParams;
    private ScaleTextView mNegativeButton;
    private ScaleTextView mPositiveButton;
    private ScaleTextView mNeutralButton;

    public MultipleButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        setOrientation(HORIZONTAL);
        mCircleParams = params;
        mNegativeParams = params.negativeParams;
        mPositiveParams = params.positiveParams;
        mNeutralParams = params.neutralParams;

        int radius = params.dialogParams.radius;

        int backgroundNegative = 0;
        int backgroundNeutral = 0;
        int backgroundPositive = 0;
        if (mNegativeParams != null) {
            //取消按钮
            createNegative();
            //如果取消按钮没有背景色，则使用默认色
            backgroundNegative = mNegativeParams.backgroundColor != 0
                    ? mNegativeParams.backgroundColor : params.dialogParams.backgroundColor;
        }
        if (mNeutralParams != null) {
            if (mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            createNeutral();
            //如果取消按钮没有背景色，则使用默认色
            backgroundNeutral = mNeutralParams.backgroundColor != 0
                    ? mNeutralParams.backgroundColor : params.dialogParams.backgroundColor;


        }
        if (mPositiveParams != null) {
            if (mNeutralButton != null || mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            //确定按钮
            createPositive();
            //如果取消按钮没有背景色，则使用默认色
            backgroundPositive = mPositiveParams.backgroundColor != 0
                    ? mPositiveParams.backgroundColor : params.dialogParams.backgroundColor;
        }

        if (mNegativeButton != null && mNegativeParams != null) {
            //右边没按钮则右边是圆角
            SelectorBtn selectorBtn = new SelectorBtn(backgroundNegative
                    , mNegativeParams.backgroundColorPress != 0
                    ? mNegativeParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , 0, 0, (mNeutralButton == null && mPositiveButton == null) ? radius : 0, radius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNegativeButton.setBackground(selectorBtn);
            } else {
                mNegativeButton.setBackgroundDrawable(selectorBtn);
            }
        }
        if (mPositiveButton != null && mPositiveParams != null) {
            //左右没按钮则左右是圆角
            SelectorBtn selectorBtn = new SelectorBtn(backgroundPositive
                    , mPositiveParams.backgroundColorPress != 0
                    ? mPositiveParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , 0, 0, radius, (mNegativeButton == null && mNeutralButton == null) ? radius : 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPositiveButton.setBackground(selectorBtn);
            } else {
                mPositiveButton.setBackgroundDrawable(selectorBtn);
            }
        }
        if (mNeutralButton != null && mNeutralParams != null) {
            //左边没按钮则左边是圆角
            SelectorBtn selectorBtn = new SelectorBtn(backgroundNeutral
                    , mNeutralParams.backgroundColorPress != 0
                    ? mNeutralParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , 0, 0, mPositiveButton != null ? 0 : radius, mNegativeButton != null ? 0 : radius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNeutralButton.setBackground(selectorBtn);
            } else {
                mNeutralButton.setBackgroundDrawable(selectorBtn);
            }
        }
    }

    private void createDivider() {
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);
    }

    private void createNegative() {
        mNegativeButton = new ScaleTextView(getContext());
        mNegativeButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(mNegativeParams.height);
        handleNegativeStyle();

        addView(mNegativeButton);
    }

    private void handleNegativeStyle() {
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
    }

    private void createPositive() {

        mPositiveButton = new ScaleTextView(getContext());
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setHeight(mPositiveParams.height);
        handlePositiveStyle();

        addView(mPositiveButton);
    }

    private void handlePositiveStyle() {
        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setEnabled(!mPositiveParams.disable);
        mPositiveButton.setTextColor(mPositiveParams.disable ?
                mPositiveParams.textColorDisable : mPositiveParams.textColor);
    }

    private void createNeutral() {

        mNeutralButton = new ScaleTextView(getContext());
        mNeutralButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(mNeutralParams.height);
        handleNeutralStyle();

        addView(mNeutralButton);
    }

    private void handleNeutralStyle() {
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
    }

    public void regNegativeListener(OnClickListener onClickListener) {
        if (mNegativeButton != null) {
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    public void regPositiveListener(OnClickListener onClickListener) {
        if (mPositiveButton != null) {
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    public void regNeutralListener(OnClickListener onClickListener) {
        if (mNeutralButton != null) {
            mNeutralButton.setOnClickListener(onClickListener);
        }
    }


    public void refreshText() {
        if (mNegativeParams == null || mNegativeButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleNegativeStyle();
            }
        });

        if (mPositiveParams == null || mPositiveButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handlePositiveStyle();
            }
        });


        if (mNeutralParams == null || mNeutralButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleNeutralStyle();
            }
        });
    }

    @Override
    public void onClick(View view, int which) {
        if (which == Controller.BUTTON_NEGATIVE) {
            if (mCircleParams.clickNegativeListener != null) {
                mCircleParams.clickNegativeListener.onClick(mNegativeButton);
            }
        } else if (which == Controller.BUTTON_POSITIVE) {
            if (mCircleParams.clickPositiveListener != null) {
                mCircleParams.clickPositiveListener.onClick(mPositiveButton);
            }
        } else if (which == Controller.BUTTON_NEUTRAL) {
            if (mCircleParams.clickNeutralListener != null) {
                mCircleParams.clickNeutralListener.onClick(mNeutralButton);
            }
        }

    }
}
