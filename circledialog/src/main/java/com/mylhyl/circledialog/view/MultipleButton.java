package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawableSelector;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
class MultipleButton extends ScaleLinearLayout implements Controller.OnClickListener
        , ButtonView {

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
            //右边没按钮则右下是圆角
            CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundNegative
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
            //左边没按钮则左下是圆角
            CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundPositive
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
            //左右没按钮则左下右下是圆角
            CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundNeutral
                    , mNeutralParams.backgroundColorPress != 0
                    ? mNeutralParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , 0, 0, mPositiveButton == null ? radius : 0
                    , mNegativeButton == null ? radius : 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNeutralButton.setBackground(selectorBtn);
            } else {
                mNeutralButton.setBackgroundDrawable(selectorBtn);
            }
        }
        OnCreateButtonListener createButtonListener = mCircleParams.createButtonListener;
        if (createButtonListener != null) {
            createButtonListener.onCreateButton(mNegativeButton, mPositiveButton, mNeutralButton);
        }
    }

    private void createNegative() {
        mNegativeButton = new ScaleTextView(getContext());
        mNegativeButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handleNegativeStyle();
        addView(mNegativeButton);
    }

    private void createDivider() {
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);
    }

    private void createNeutral() {
        mNeutralButton = new ScaleTextView(getContext());
        mNeutralButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handleNeutralStyle();
        addView(mNeutralButton);
    }

    private void createPositive() {
        mPositiveButton = new ScaleTextView(getContext());
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handlePositiveStyle();
        addView(mPositiveButton);
    }

    private void handleNegativeStyle() {
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(mNegativeParams.height);
        mNegativeButton.setTypeface(mNegativeButton.getTypeface(), mNegativeParams.styleText);
    }

    private void handleNeutralStyle() {
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(mNeutralParams.height);
        mNeutralButton.setTypeface(mNeutralButton.getTypeface(), mNeutralParams.styleText);
    }

    private void handlePositiveStyle() {
        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setEnabled(!mPositiveParams.disable);
        mPositiveButton.setTextColor(mPositiveParams.disable ?
                mPositiveParams.textColorDisable : mPositiveParams.textColor);
        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setHeight(mPositiveParams.height);
        mPositiveButton.setTypeface(mPositiveButton.getTypeface(), mPositiveParams.styleText);
    }

    @Override
    public void regNegativeListener(OnClickListener onClickListener) {
        if (mNegativeButton != null) {
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void regPositiveListener(OnClickListener onClickListener) {
        if (mPositiveButton != null) {
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void regNeutralListener(OnClickListener onClickListener) {
        if (mNeutralButton != null) {
            mNeutralButton.setOnClickListener(onClickListener);
        }
    }

    @Override
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
    public View getView() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return mNegativeParams == null && mPositiveParams == null && mNeutralParams == null;
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
