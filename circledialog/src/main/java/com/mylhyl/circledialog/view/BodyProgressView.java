package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.OnCreateProgressListener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by hupei on 2017/3/31.
 */

final class BodyProgressView extends LinearLayout {
    private DialogParams mDialogParams;
    private ProgressParams mProgressParams;
    private OnCreateProgressListener mOnCreateProgressListener;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private Handler mViewUpdateHandler;

    public BodyProgressView(Context context, DialogParams dialogParams
            , ProgressParams progressParams, OnCreateProgressListener onCreateProgressListener) {
        super(context);
        this.mDialogParams = dialogParams;
        this.mProgressParams = progressParams;
        this.mOnCreateProgressListener = onCreateProgressListener;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);

        //如果没有背景色，则使用默认色
        int backgroundColor = mProgressParams.backgroundColor != 0
                ? mProgressParams.backgroundColor : mDialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);
        createProgressBar();
        createText();

        if (mOnCreateProgressListener != null) {
            mOnCreateProgressListener.onCreateProgressView(mProgressBar, mTextView);
        }
    }

    private void createText() {
        //构建文本
        mTextView = new TextView(getContext());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(mProgressParams.textSize);
        mTextView.setTextColor(mProgressParams.textColor);
        mTextView.setTypeface(mTextView.getTypeface(), mProgressParams.styleText);
        int[] padding = mProgressParams.padding;
        if (padding != null) {
            mTextView.setPadding(padding[0], padding[1], padding[2], padding[3]);
        }
        addView(mTextView);

        if (mProgressParams.style == ProgressParams.STYLE_HORIZONTAL
                && !TextUtils.isEmpty(mProgressParams.text)) {
            mViewUpdateHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    int progress = mProgressBar.getProgress();
                    int max = mProgressBar.getMax();
                    int percent = (int) (((float) progress / (float) max) * 100);
                    String args = percent + "%";
                    if (mProgressParams.text.contains("%s"))
                        mTextView.setText(String.format(mProgressParams.text, args));
                    else mTextView.setText(mProgressParams.text + args);
                }
            };
        } else {
            mTextView.setText(mProgressParams.text);
        }
    }

    private void createProgressBar() {
        //自定义样式
        int progressDrawableId = mProgressParams.progressDrawableId;
        //水平样式
        if (mProgressParams.style == ProgressParams.STYLE_HORIZONTAL) {
            if (progressDrawableId != 0) {
                mProgressBar = new ProgressBar(getContext());
                setFieldValue(mProgressBar, "mOnlyIndeterminate", new Boolean(false));
                mProgressBar.setIndeterminate(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mProgressBar.setProgressDrawableTiled(getContext().getDrawable(progressDrawableId));
                else
                    mProgressBar.setProgressDrawable(getContext().getResources().getDrawable
                            (progressDrawableId));
            } else {
                mProgressBar = new ProgressBar(getContext(), null, android.R.attr
                        .progressBarStyleHorizontal);
            }
            mProgressParams.progressHeight = CircleDimen.PROGRESS_HEIGHT_HORIZONTAL;
        }
        //旋转样式
        else {
            if (progressDrawableId != 0) {
                mProgressBar = new ProgressBar(getContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mProgressBar.setIndeterminateDrawableTiled(getContext().getDrawable
                            (progressDrawableId));
                else
                    mProgressBar.setIndeterminateDrawable(getContext().getResources().getDrawable
                            (progressDrawableId));
            } else {
                mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
            }
            mProgressParams.progressHeight = CircleDimen.PROGRESS_HEIGHT_SPINNER;
        }

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mProgressParams
                .progressHeight);
        int[] margins = mProgressParams.margins;
        if (margins != null)
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        addView(mProgressBar, layoutParams);
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    private static void setFieldValue(final Object object, final String fieldName, final Object
            value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on " +
                    "target [" + object + "]");
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field
                .getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(final Class clazz, final String fieldName) {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();// Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    public void refreshProgress() {
        mProgressBar.setMax(mProgressParams.max);
        mProgressBar.setProgress(mProgressParams.progress);
        mProgressBar.setSecondaryProgress(mProgressParams.progress + 10);
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (mViewUpdateHandler != null && !mViewUpdateHandler.hasMessages(0))
            mViewUpdateHandler.sendEmptyMessage(0);
    }
}
