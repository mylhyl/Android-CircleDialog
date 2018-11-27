package com.mylhyl.circledialog.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.LottieParams;
import com.mylhyl.circledialog.view.listener.OnCreateLottieListener;

/**
 * Created by hupei on 2018/7/7.
 */

final class BodyLottieView extends LinearLayout {
    private LottieAnimationView mLottieAnimationView;
    private LottieParams mLottieParams;

    public BodyLottieView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        setOrientation(LinearLayout.VERTICAL);
        DialogParams dialogParams = params.dialogParams;
        mLottieParams = params.lottieParams;

        //如果没有背景色，则使用默认色
        int backgroundColor = mLottieParams.backgroundColor != 0
                ? mLottieParams.backgroundColor : dialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

        mLottieAnimationView = new LottieAnimationView(getContext());
        int lottieWidth = mLottieParams.lottieWidth;
        int lottieHeight = mLottieParams.lottieHeight;
        LayoutParams layoutParams = new LayoutParams(
                lottieWidth <= 0 ? LayoutParams.WRAP_CONTENT : lottieWidth
                , lottieHeight <= 0 ? LayoutParams.WRAP_CONTENT : lottieHeight);
        int[] margins = mLottieParams.margins;
        if (margins != null)
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        layoutParams.gravity = Gravity.CENTER;

        if (mLottieParams.animationResId != 0) {
            mLottieAnimationView.setAnimation(mLottieParams.animationResId);
        }
        if (!TextUtils.isEmpty(mLottieParams.animationFileName)) {
            mLottieAnimationView.setAnimation(mLottieParams.animationFileName);
        }
        if (mLottieParams.autoPlay) {
            mLottieAnimationView.playAnimation();
        }
        if (mLottieParams.loop) {
            mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        }
        addView(mLottieAnimationView, layoutParams);

        //构建文本
        TextView textView = null;
        if (!TextUtils.isEmpty(mLottieParams.text)) {
            textView = new TextView(getContext());
            LayoutParams textLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textLayoutParams.gravity = Gravity.CENTER;
            int[] textMargins = mLottieParams.textMargins;
            if (textMargins != null)
                textLayoutParams.setMargins(textMargins[0], textMargins[1], textMargins[2], textMargins[3]);
            textView.setText(mLottieParams.text);
            textView.setTextSize(mLottieParams.textSize);
            textView.setTextColor(mLottieParams.textColor);
            textView.setTypeface(textView.getTypeface(), mLottieParams.styleText);
            int[] textPadding = mLottieParams.textPadding;
            if (textPadding != null)
                textView.setPadding(textPadding[0], textPadding[1], textPadding[2], textPadding[3]);
            addView(textView, textLayoutParams);
        }

        OnCreateLottieListener createLottieListener = params.createLottieListener;
        if (createLottieListener != null) {
            createLottieListener.onCreateLottieView(mLottieAnimationView, textView);
        }
    }
}
