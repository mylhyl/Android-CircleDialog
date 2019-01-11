package com.mylhyl.circledialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/29.
 */

public abstract class AbsBaseCircleDialog extends DialogFragment {

    private static final String SAVED_GRAVITY = "circle:baseGravity";
    private static final String SAVED_TOUCH_OUT = "circle:baseTouchOut";
    private static final String SAVED_CANCELED_BACK = "circle:baseCanceledBack";
    private static final String SAVED_WIDTH = "circle:baseWidth";
    private static final String SAVED_HEIGHT_MAX = "circle:baseMaxHeight";
    private static final String SAVED_PADDING = "circle:basePadding";
    private static final String SAVED_ANIM_STYLE = "circle:baseAnimStyle";
    private static final String SAVED_DIM_ENABLED = "circle:baseDimEnabled";
    private static final String SAVED_BACKGROUND_COLOR = "circle:baseBackgroundColor";
    private static final String SAVED_RADIUS = "circle:baseRadius";
    private static final String SAVED_ALPHA = "circle:baseAlpha";
    private static final String SAVED_X = "circle:baseX";
    private static final String SAVED_Y = "circle:baseY";

    private SystemBarConfig mSystemBarConfig;
    private int mGravity = Gravity.CENTER;//对话框的位置
    private boolean mCanceledOnTouchOutside = true;//是否触摸外部关闭
    private boolean mCanceledBack = true;//是否返回键关闭
    private float mWidth = CircleDimen.DIALOG_WIDTH;//对话框宽度，范围：0-1；1整屏宽
    private float mMaxHeight;//对话框高度，范围：0-1；1整屏高
    private int[] mPadding;//对话框与屏幕边缘距离
    private int mAnimStyle;//显示动画
    private boolean isDimEnabled = true;
    private int mBackgroundColor = Color.TRANSPARENT;//对话框的背景色
    private int mRadius = CircleDimen.DIALOG_RADIUS;//对话框的圆角半径
    private float mAlpha = CircleDimen.DIALOG_ALPHA;//对话框透明度，范围：0-1；1不透明
    private int mX, mY;
    private int mSystemUiVisibility;

    public AbsBaseCircleDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSystemBarConfig = new SystemBarConfig(getActivity());
        //设置 无标题 无边框
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        if (savedInstanceState != null) {
            mGravity = savedInstanceState.getInt(SAVED_GRAVITY);
            mCanceledOnTouchOutside = savedInstanceState.getBoolean(SAVED_TOUCH_OUT);
            mCanceledBack = savedInstanceState.getBoolean(SAVED_CANCELED_BACK);
            mWidth = savedInstanceState.getFloat(SAVED_WIDTH);
            mMaxHeight = savedInstanceState.getFloat(SAVED_HEIGHT_MAX);
            mPadding = savedInstanceState.getIntArray(SAVED_PADDING);
            mAnimStyle = savedInstanceState.getInt(SAVED_ANIM_STYLE);
            isDimEnabled = savedInstanceState.getBoolean(SAVED_DIM_ENABLED);
            mBackgroundColor = savedInstanceState.getInt(SAVED_BACKGROUND_COLOR);
            mRadius = savedInstanceState.getInt(SAVED_RADIUS);
            mAlpha = savedInstanceState.getFloat(SAVED_ALPHA);
            mX = savedInstanceState.getInt(SAVED_X);
            mY = savedInstanceState.getInt(SAVED_Y);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        remove();
    }

    @Override
    public void onStart() {
        if (getView() != null && mMaxHeight > 0) {
            getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int height = getView().getHeight();
                    int screenHeight = mSystemBarConfig.getScreenHeight();
                    int maxHeight = (int) (screenHeight * mMaxHeight);
                    if (height > maxHeight) {
                        getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        getView().setLayoutParams(new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT, maxHeight));
                    }
                }
            });
        }
        final Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.setCancelable(mCanceledBack);
            setDialogGravity(dialog);//设置对话框布局

            if (mSystemUiVisibility != 0) {
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            }
        }
        super.onStart();
        if (dialog != null && mSystemUiVisibility != 0) {
            dialog.getWindow().getDecorView().setSystemUiVisibility(mSystemUiVisibility);
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }

    /**
     * 对话框配置
     *
     * @param dialog
     */
    private void setDialogGravity(Dialog dialog) {
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        int screenWidth = mSystemBarConfig.getScreenWidth();
        if (mWidth > 0 && mWidth <= 1) {
            wlp.width = (int) (screenWidth * mWidth);//宽度按屏幕大小的百分比设置
        } else {
            wlp.width = (int) mWidth;
        }
        wlp.gravity = mGravity;
        wlp.x = mX;
        wlp.y = mY;
        //边距
        if (mPadding != null) {
            int[] padding = mPadding;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.getDecorView().setPadding(padding[0], padding[1], padding[2], padding[3]);
        }
        window.setAttributes(wlp);
        //动画
        if (mAnimStyle != 0) {
            window.setWindowAnimations(mAnimStyle);
        }

        //背景灰暗
        if (isDimEnabled) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_GRAVITY, mGravity);
        outState.putBoolean(SAVED_TOUCH_OUT, mCanceledOnTouchOutside);
        outState.putBoolean(SAVED_CANCELED_BACK, mCanceledBack);
        outState.putFloat(SAVED_WIDTH, mWidth);
        outState.putFloat(SAVED_HEIGHT_MAX, mMaxHeight);
        if (mPadding != null) outState.putIntArray(SAVED_PADDING, mPadding);
        outState.putInt(SAVED_ANIM_STYLE, mAnimStyle);
        outState.putBoolean(SAVED_DIM_ENABLED, isDimEnabled);
        outState.putInt(SAVED_BACKGROUND_COLOR, mBackgroundColor);
        outState.putInt(SAVED_RADIUS, mRadius);
        outState.putFloat(SAVED_ALPHA, mAlpha);
        outState.putInt(SAVED_X, mX);
        outState.putInt(SAVED_Y, mY);
    }

    public void remove() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(this);
        ft.addToBackStack(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(getContext(), inflater, container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(new CircleDrawable(mBackgroundColor, mRadius));
        } else {
            view.setBackgroundDrawable(new CircleDrawable(mBackgroundColor, mRadius));
        }
        view.setAlpha(mAlpha);
        return view;
    }

    public abstract View createView(Context context, LayoutInflater inflater, ViewGroup container);

    /**
     * 设置对话框位置
     * {@link Gravity#CENTER 默认}
     *
     * @param gravity 位置
     */
    protected void setGravity(int gravity) {
        mGravity = gravity;
    }

    /**
     * 设置对话框点击外部关闭
     *
     * @param cancel true允许
     */
    protected void setCanceledOnTouchOutside(boolean cancel) {
        mCanceledOnTouchOutside = cancel;
    }

    /**
     * 设置对话框返回键关闭关闭
     *
     * @param cancel true允许
     */
    protected void setCanceledBack(boolean cancel) {
        mCanceledBack = cancel;
    }

    /**
     * 设置对话框宽度
     *
     * @param width 0.0 - 1.0
     */
    protected void setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
        mWidth = width;
    }

    /**
     * 设置对话框最大高度
     *
     * @param maxHeight 0f - 1f 之间
     */
    protected void setMaxHeight(@FloatRange(from = 0.0, to = 1.0) float maxHeight) {
        mMaxHeight = maxHeight;
    }

    /**
     * 设置边距
     *
     * @param left   px
     * @param top    px
     * @param right  px
     * @param bottom px
     */
    protected void setPadding(int left, int top, int right, int bottom) {
        mPadding = new int[]{left, top, right, bottom};
    }

    /**
     * 弹出对话框的动画
     *
     * @param animStyle StyleRes
     */
    protected void setAnimations(int animStyle) {
        mAnimStyle = animStyle;
    }


    /**
     * 设置背景是否昏暗，默认true
     *
     * @param dimEnabled true昏暗
     */
    protected void setDimEnabled(boolean dimEnabled) {
        isDimEnabled = dimEnabled;
    }

    /**
     * 设置对话框背景色
     *
     * @param color 颜色值
     */
    protected void setBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;
    }

    /**
     * 设置对话框圆角
     *
     * @param radius 半径
     */
    protected void setRadius(int radius) {
        mRadius = radius;
    }

    /**
     * 设置对话框透明度
     *
     * @param alpha 0.0 - 1.0
     */
    protected void setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        mAlpha = alpha;
    }

    protected void setX(int x) {
        mX = x;
    }

    protected void setY(int y) {
        mY = y;
    }

    /**
     * 底部位置且充满宽
     */
    protected void bottomFull() {
        this.mGravity = Gravity.BOTTOM;
        this.mRadius = 0;
        this.mWidth = 1f;
        this.mY = 0;
    }

    protected void setSystemUiVisibility(int systemUiVisibility) {
        this.mSystemUiVisibility = systemUiVisibility;
    }

    //显示键盘
    protected void setSoftInputMode() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public SystemBarConfig getSystemBarConfig() {
        return mSystemBarConfig;
    }
}
