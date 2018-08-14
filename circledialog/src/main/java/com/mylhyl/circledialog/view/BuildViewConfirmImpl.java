package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.view.listener.OnCreateTextListener;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewConfirmImpl extends BuildViewAbs {
    private ScaleTextView mBodyTextView;

    public BuildViewConfirmImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public ScaleTextView getBodyView() {
        return mBodyTextView;
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView(mRootCardViewByLinearLayout);
        if (mBodyTextView == null) {
            mBodyTextView = new ScaleTextView(mContext);
            mRootCardViewByLinearLayout.addView(mBodyTextView);

            DialogParams dialogParams = mParams.dialogParams;
            TextParams textParams = mParams.textParams;

            mBodyTextView.setGravity(textParams.gravity);

            //如果标题没有背景色，则使用默认色
            int backgroundColor = textParams.backgroundColor != 0
                    ? textParams.backgroundColor : dialogParams.backgroundColor;
            mBodyTextView.setBackgroundColor(backgroundColor);

            mBodyTextView.setMinHeight(textParams.height);
            mBodyTextView.setTextColor(textParams.textColor);
            mBodyTextView.setTextSize(textParams.textSize);
            mBodyTextView.setText(textParams.text);
            mBodyTextView.setTypeface(mBodyTextView.getTypeface(), textParams.styleText);

            int[] padding = textParams.padding;
            if (padding != null)
                mBodyTextView.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);

            OnCreateTextListener createTextListener = mParams.createTextListener;
            if (createTextListener != null) {
                createTextListener.onCreateText(mBodyTextView);
            }
        }
    }

    @Override
    public void refreshContent() {
        if (mBodyTextView != null && mParams.textParams != null) {
            mBodyTextView.post(new Runnable() {
                @Override
                public void run() {
                    mBodyTextView.setText(mParams.textParams.text);
                }
            });
        }
    }
}
