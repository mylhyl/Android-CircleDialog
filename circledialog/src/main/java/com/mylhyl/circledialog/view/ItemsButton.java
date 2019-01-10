package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawableSelector;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;

/**
 * 列表对话框的取消按钮视图
 * Created by hupei on 2017/3/30.
 */
final class ItemsButton extends LinearLayout implements ButtonView {

    private DialogParams mDialogParams;
    private ButtonParams mNegativeParams;
    private ButtonParams mPositiveParams;
    private ButtonParams mNeutralParams;
    private OnCreateButtonListener mOnCreateButtonListener;
    private TextView mNegativeButton;
    private TextView mPositiveButton;
    private TextView mNeutralButton;

    public ItemsButton(Context context, DialogParams dialogParams, ButtonParams negativeParams
            , ButtonParams positiveParams, ButtonParams neutralParams
            , OnCreateButtonListener onCreateButtonListener) {
        super(context);
        mDialogParams = dialogParams;
        mNegativeParams = negativeParams;
        mPositiveParams = positiveParams;
        mNeutralParams = neutralParams;
        mOnCreateButtonListener = onCreateButtonListener;
        init();
    }

    private void init() {
        int radius = mDialogParams.radius;

        int backgroundNegative = 0;
        int backgroundNeutral = 0;
        int backgroundPositive = 0;
        if (mNegativeParams != null) {
            //取消按钮
            createNegative();
            //如果取消按钮没有背景色，则使用默认色
            backgroundNegative = mNegativeParams.backgroundColor != 0
                    ? mNegativeParams.backgroundColor : mDialogParams.backgroundColor;
        }
        if (mNeutralParams != null) {
            if (mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            createNeutral();
            //如果取消按钮没有背景色，则使用默认色
            backgroundNeutral = mNeutralParams.backgroundColor != 0
                    ? mNeutralParams.backgroundColor : mDialogParams.backgroundColor;

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
                    ? mPositiveParams.backgroundColor : mDialogParams.backgroundColor;
        }

        if (mNegativeButton != null && mNegativeParams != null) {
            //右边没按钮则右下是圆角
            int rightRadius = (mNeutralButton == null && mPositiveButton == null) ? radius : 0;
            CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundNegative
                    , mNegativeParams.backgroundColorPress != 0
                    ? mNegativeParams.backgroundColorPress : mDialogParams.backgroundColorPress
                    , radius, rightRadius, rightRadius, radius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNegativeButton.setBackground(selectorBtn);
            } else {
                mNegativeButton.setBackgroundDrawable(selectorBtn);
            }
        }
        if (mPositiveButton != null && mPositiveParams != null) {
            //左边没按钮则左下是圆角
            int leftRadius = (mNegativeButton == null && mNeutralButton == null) ? radius : 0;
            CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundPositive
                    , mPositiveParams.backgroundColorPress != 0
                    ? mPositiveParams.backgroundColorPress : mDialogParams.backgroundColorPress
                    , leftRadius, radius, radius, leftRadius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPositiveButton.setBackground(selectorBtn);
            } else {
                mPositiveButton.setBackgroundDrawable(selectorBtn);
            }
        }
        if (mNeutralButton != null && mNeutralParams != null) {
            //左右没按钮则左下右下是圆角
            int leftRadius = mNegativeButton == null ? radius : 0;
            int rightRadius = mPositiveButton == null ? radius : 0;
            CircleDrawableSelector selectorBtn = new CircleDrawableSelector(backgroundNeutral
                    , mNeutralParams.backgroundColorPress != 0
                    ? mNeutralParams.backgroundColorPress : mDialogParams.backgroundColorPress
                    , leftRadius, rightRadius, rightRadius, leftRadius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNeutralButton.setBackground(selectorBtn);
            } else {
                mNeutralButton.setBackgroundDrawable(selectorBtn);
            }
        }

        if (mOnCreateButtonListener != null) {
            mOnCreateButtonListener.onCreateButton(mNegativeButton, mPositiveButton, mNeutralButton);
        }
    }

    private void createNegative() {
        mNegativeButton = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的上距离
        if (mNegativeParams.topMargin > 0) {
            params.topMargin = mNegativeParams.topMargin;
        }
        mNegativeButton.setLayoutParams(params);
        handleNegativeStyle();
        addView(mNegativeButton);
    }

    private void createDivider() {
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);
    }

    private void createNeutral() {
        mNeutralButton = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的上距离
        if (mNeutralParams.topMargin > 0) {
            params.topMargin = mNeutralParams.topMargin;
        }
        mNeutralButton.setLayoutParams(params);
        handleNeutralStyle();
        addView(mNeutralButton);
    }

    private void createPositive() {
        mPositiveButton = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的上距离
        if (mPositiveParams.topMargin > 0) {
            params.topMargin = mPositiveParams.topMargin;
        }
        mPositiveButton.setLayoutParams(params);
        handlePositiveStyle();
        addView(mPositiveButton);
    }

    private void handleNegativeStyle() {
        mNegativeButton.setGravity(Gravity.CENTER);
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(mNegativeParams.height);
        mNegativeButton.setTypeface(mNegativeButton.getTypeface(), mNegativeParams.styleText);
    }

    private void handleNeutralStyle() {
        mNeutralButton.setGravity(Gravity.CENTER);
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(mNeutralParams.height);
        mNeutralButton.setTypeface(mNeutralButton.getTypeface(), mNeutralParams.styleText);
    }

    private void handlePositiveStyle() {
        mPositiveButton.setGravity(Gravity.CENTER);
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
}
