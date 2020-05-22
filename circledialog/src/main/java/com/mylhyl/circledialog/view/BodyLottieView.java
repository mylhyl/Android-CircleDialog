package com.mylhyl.circledialog.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.mylhyl.circledialog.internal.BackgroundHelper;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.LottieParams;
import com.mylhyl.circledialog.view.listener.OnCreateLottieListener;

/**
 * Created by hupei on 2018/7/7.
 */

final class BodyLottieView extends LinearLayout {
    private LottieAnimationView mLottieAnimationView;
    private TextView mTextView;
    private DialogParams mDialogParams;
    private LottieParams mLottieParams;
    private OnCreateLottieListener mOnCreateLottieListener;

    public BodyLottieView(Context context, CircleParams circleParams) {
        super(context);
        this.mDialogParams = circleParams.dialogParams;
        this.mLottieParams = circleParams.lottieParams;
        this.mOnCreateLottieListener = circleParams.circleListeners.createLottieListener;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);

        // 如果没有背景色，则使用默认色
        int backgroundColor = mLottieParams.backgroundColor != 0 ?
                mLottieParams.backgroundColor : mDialogParams.backgroundColor;
        BackgroundHelper.INSTANCE.handleBodyBackground(this, backgroundColor);

        createLottieView();
        createText();

        if (mOnCreateLottieListener != null) {
            mOnCreateLottieListener.onCreateLottieView(mLottieAnimationView, mTextView);
        }
    }

    private void createLottieView() {
        mLottieAnimationView = new LottieAnimationView(getContext());
        int lottieWidth = Controller.dp2px(getContext(), mLottieParams.lottieWidth);
        int lottieHeight = Controller.dp2px(getContext(), mLottieParams.lottieHeight);
        LayoutParams layoutParams = new LayoutParams(lottieWidth <= 0 ? LayoutParams.WRAP_CONTENT : lottieWidth,
                lottieHeight <= 0 ? LayoutParams.WRAP_CONTENT : lottieHeight);
        int[] margins = mLottieParams.margins;
        if (margins != null)
            layoutParams.setMargins(Controller.dp2px(getContext(), margins[0]),
                    Controller.dp2px(getContext(), margins[1]),
                    Controller.dp2px(getContext(), margins[2]),
                    Controller.dp2px(getContext(), margins[3]));
        layoutParams.gravity = Gravity.CENTER;

        if (mLottieParams.animationResId != 0) {
            mLottieAnimationView.setAnimation(mLottieParams.animationResId);
        }
        if (!TextUtils.isEmpty(mLottieParams.animationFileName)) {
            mLottieAnimationView.setAnimation(mLottieParams.animationFileName);
        }
        if (!TextUtils.isEmpty(mLottieParams.imageAssetsFolder)) {
            mLottieAnimationView.setImageAssetsFolder(mLottieParams.imageAssetsFolder);
        }
        if (mLottieParams.autoPlay) {
            mLottieAnimationView.playAnimation();
        }
        if (mLottieParams.loop) {
            mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        }
        addView(mLottieAnimationView, layoutParams);
    }

    @Nullable
    private void createText() {
        //构建文本
        if (!TextUtils.isEmpty(mLottieParams.text)) {
            mTextView = new TextView(getContext());
            LayoutParams textLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textLayoutParams.gravity = Gravity.CENTER;
            int[] textMargins = mLottieParams.textMargins;
            if (textMargins != null) {
                textLayoutParams.setMargins(Controller.dp2px(getContext(), textMargins[0]),
                        Controller.dp2px(getContext(), textMargins[1]),
                        Controller.dp2px(getContext(), textMargins[2]),
                        Controller.dp2px(getContext(), textMargins[3]));
            }
            if (mDialogParams.typeface != null) {
                mTextView.setTypeface(mDialogParams.typeface);
            }
            mTextView.setText(mLottieParams.text);
            mTextView.setTextSize(mLottieParams.textSize);
            mTextView.setTextColor(mLottieParams.textColor);
            mTextView.setTypeface(mTextView.getTypeface(), mLottieParams.styleText);
            int[] textPadding = mLottieParams.textPadding;
            if (textPadding != null) {
                mTextView.setPadding(Controller.dp2px(getContext(), textPadding[0]),
                        Controller.dp2px(getContext(), textPadding[1]),
                        Controller.dp2px(getContext(), textPadding[2]),
                        Controller.dp2px(getContext(), textPadding[3]));
            }
            addView(mTextView, textLayoutParams);
        }
    }

    public void refreshText() {
        if (mLottieParams == null) {
            return;
        }
        if (mLottieAnimationView != null) {
            if (mLottieParams.animationResId != 0) {
                mLottieAnimationView.setAnimation(mLottieParams.animationResId);
            }
            if (!TextUtils.isEmpty(mLottieParams.animationFileName)) {
                mLottieAnimationView.setAnimation(mLottieParams.animationFileName);
            }
            if (!TextUtils.isEmpty(mLottieParams.imageAssetsFolder)) {
                mLottieAnimationView.setImageAssetsFolder(mLottieParams.imageAssetsFolder);
            }
            mLottieAnimationView.playAnimation();
        }

        if (mTextView != null && !TextUtils.isEmpty(mLottieParams.text)) {
            mTextView.setText(mLottieParams.text);
        }
    }
}
