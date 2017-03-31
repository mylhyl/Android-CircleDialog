package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.drawable.ProgressDrawable;
import com.mylhyl.circledialog.res.values.CircleColor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by hupei on 2017/3/31.
 */

class BodyProgressView extends ScaleLinearLayout {
    private ProgressParams mProgressParams;
    private ProgressBar mProgressBar;
    private Handler mViewUpdateHandler;

    public BodyProgressView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        setOrientation(LinearLayout.VERTICAL);
        DialogParams dialogParams = params.getDialogParams();
        TitleParams titleParams = params.getTitleParams();
        ButtonParams negativeParams = params.getNegativeParams();
        ButtonParams positiveParams = params.getPositiveParams();
        mProgressParams = params.getProgressParams();

        //如果没有背景色，则使用默认色
        int backgroundColor = mProgressParams.backgroundColor != 0 ? mProgressParams.backgroundColor : CircleColor
                .bgDialog;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null)
            setBackground(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius, dialogParams.radius));
            //没标题有按钮则顶部圆角
        else if (titleParams == null && (negativeParams != null || positiveParams != null))
            setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams.radius, 0, 0));
            //没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null)
            setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
            //有标题有按钮则不用考虑圆角
        else setBackgroundColor(backgroundColor);

        //进度条
        mProgressBar = new ProgressBar(getContext());
        setFieldValue(mProgressBar, "mOnlyIndeterminate", new Boolean(false));
        mProgressBar.setIndeterminate(false);

        int progressDrawableId = mProgressParams.progressDrawableId;
        if (progressDrawableId == 0) {//库自带
            LayerDrawable layerDrawable = new ProgressDrawable().getLayerDrawable();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable d = getMethod("tileify", mProgressBar, new Object[]{layerDrawable, false});
                mProgressBar.setProgressDrawable(d);
            } else mProgressBar.setProgressDrawableTiled(layerDrawable);
        } else {
            //使用者自定义xml
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mProgressBar.setProgressDrawable(context.getDrawable(progressDrawableId));
            else mProgressBar.setProgressDrawable(context.getResources().getDrawable(progressDrawableId));
        }

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mProgressParams.progressHeight);
        int[] margins = mProgressParams.margins;
        if (margins != null) layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);

        addView(mProgressBar, layoutParams);

        final ScaleTextView textView = new ScaleTextView(getContext());
        textView.setTextSize(mProgressParams.textSize);
        textView.setTextColor(mProgressParams.textColor);
        int[] padding = mProgressParams.padding;
        if (padding != null) textView.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);

        addView(textView);

        mViewUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = mProgressBar.getProgress();
                int max = mProgressBar.getMax();
                int percent = (int) (((float) progress / (float) max) * 100);
                String args = percent + "%";
                if (!TextUtils.isEmpty(mProgressParams.text) && mProgressParams.text.contains("%s"))
                    textView.setText(String.format(mProgressParams.text, args));
                else textView.setText(mProgressParams.text + args);
            }
        };
    }


    public void refreshProgress() {
        mProgressBar.setMax(mProgressParams.max);
        mProgressBar.setProgress(mProgressParams.progress);
        mProgressBar.setSecondaryProgress(mProgressParams.progress + 10);
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (mViewUpdateHandler != null && !mViewUpdateHandler.hasMessages(0)) mViewUpdateHandler.sendEmptyMessage(0);
    }

    private static Drawable getMethod(String MethodName, Object o, Object[] paras) {
        Drawable newDrawable = null;
        try {
            Class c[] = new Class[2];
            c[0] = Drawable.class;
            c[1] = boolean.class;
            Method method = ProgressBar.class.getDeclaredMethod(MethodName, c);
            method.setAccessible(true);
            newDrawable = (Drawable) method.invoke(o, paras);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDrawable;
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    private static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
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
     * 循环向上转型,获取类的DeclaredField.
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(final Class clazz, final String fieldName) {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();// Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }
}
