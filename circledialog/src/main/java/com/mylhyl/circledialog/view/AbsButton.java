package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
abstract class AbsButton extends LinearLayout implements ButtonView {

    protected ButtonParams mNegativeParams;
    protected ButtonParams mPositiveParams;
    protected ButtonParams mNeutralParams;
    private DialogParams mDialogParams;
    private OnCreateButtonListener mOnCreateButtonListener;
    private TextView mNegativeButton;
    private TextView mPositiveButton;
    private TextView mNeutralButton;

    public AbsButton(Context context, CircleParams circleParams) {
        super(context);
        mDialogParams = circleParams.dialogParams;
        mNegativeParams = circleParams.negativeParams;
        mPositiveParams = circleParams.positiveParams;
        mNeutralParams = circleParams.neutralParams;
        mOnCreateButtonListener = circleParams.circleListeners.createButtonListener;
        init();
    }

    @Override
    public final void regNegativeListener(OnClickListener onClickListener) {
        if (mNegativeButton != null) {
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public final void regPositiveListener(OnClickListener onClickListener) {
        if (mPositiveButton != null) {
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public final void regNeutralListener(OnClickListener onClickListener) {
        if (mNeutralButton != null) {
            mNeutralButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public final void refreshText() {
        if (mNegativeParams != null && mNegativeButton != null) {
            handleNegativeStyle();
        }

        if (mPositiveParams != null && mPositiveButton != null) {
            handlePositiveStyle();
        }

        if (mNeutralParams != null && mNeutralButton != null) {
            handleNeutralStyle();
        }
    }

    @Override
    public final View getView() {
        return this;
    }

    @Override
    public final boolean isEmpty() {
        return mNegativeParams == null && mPositiveParams == null && mNeutralParams == null;
    }

    protected abstract void initView();

    protected abstract void setNegativeButtonBackground(View view, int backgroundColor);

    protected abstract void setNeutralButtonBackground(View view, int backgroundColor);

    protected abstract void setPositiveButtonBackground(View view, int backgroundColor);

    private void init() {

        initView();

        if (mNegativeParams != null) {
            //取消按钮
            createNegative();
            //如果取消按钮没有背景色，则使用默认色
            int backgroundColor = mNegativeParams.backgroundColor != 0
                    ? mNegativeParams.backgroundColor : mDialogParams.backgroundColor;
            setNegativeButtonBackground(mNegativeButton, backgroundColor);
        }

        if (mNeutralParams != null) {
            if (mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            createNeutral();
            //如果取消按钮没有背景色，则使用默认色
            int backgroundColor = mNeutralParams.backgroundColor != 0
                    ? mNeutralParams.backgroundColor : mDialogParams.backgroundColor;
            setNeutralButtonBackground(mNeutralButton, backgroundColor);
        }

        if (mPositiveParams != null) {
            if (mNeutralButton != null || mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            //确定按钮
            createPositive();
            //如果取消按钮没有背景色，则使用默认色
            int backgroundColor = mPositiveParams.backgroundColor != 0
                    ? mPositiveParams.backgroundColor : mDialogParams.backgroundColor;
            setPositiveButtonBackground(mPositiveButton, backgroundColor);
        }

        if (mOnCreateButtonListener != null) {
            mOnCreateButtonListener.onCreateButton(mNegativeButton, mPositiveButton, mNeutralButton);
        }
    }

    private void createNegative() {
        mNegativeButton = new TextView(getContext());
        mNegativeButton.setId(android.R.id.button1);
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
        mNeutralButton = new TextView(getContext());
        mNeutralButton.setId(android.R.id.button2);
        mNeutralButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handleNeutralStyle();
        addView(mNeutralButton);
    }

    private void createPositive() {
        mPositiveButton = new TextView(getContext());
        mPositiveButton.setId(android.R.id.button3);
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handlePositiveStyle();
        addView(mPositiveButton);
    }

    private void handleNegativeStyle() {
        if (mDialogParams.typeface != null) {
            mNegativeButton.setTypeface(mDialogParams.typeface);
        }
        mNegativeButton.setGravity(Gravity.CENTER);
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(Controller.dp2px(getContext(), mNegativeParams.height));
        mNegativeButton.setTypeface(mNegativeButton.getTypeface(), mNegativeParams.styleText);
    }

    private void handleNeutralStyle() {
        if (mDialogParams.typeface != null) {
            mNeutralButton.setTypeface(mDialogParams.typeface);
        }
        mNeutralButton.setGravity(Gravity.CENTER);
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(Controller.dp2px(getContext(), mNeutralParams.height));
        mNeutralButton.setTypeface(mNeutralButton.getTypeface(), mNeutralParams.styleText);
    }

    private void handlePositiveStyle() {
        if (mDialogParams.typeface != null) {
            mPositiveButton.setTypeface(mDialogParams.typeface);
        }
        mPositiveButton.setGravity(Gravity.CENTER);
        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setEnabled(!mPositiveParams.disable);
        mPositiveButton.setTextColor(mPositiveParams.disable ?
                mPositiveParams.textColorDisable : mPositiveParams.textColor);
        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setHeight(Controller.dp2px(getContext(), mPositiveParams.height));
        mPositiveButton.setTypeface(mPositiveButton.getTypeface(), mPositiveParams.styleText);
    }
}
