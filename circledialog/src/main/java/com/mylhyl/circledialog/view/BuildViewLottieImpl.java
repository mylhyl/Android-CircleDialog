package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewLottieImpl extends BuildViewAbs {
    private BodyLottieView mBodyLottieView;

    public BuildViewLottieImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public BodyLottieView getBodyView() {
        return mBodyLottieView;
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();

        if (mBodyLottieView == null) {
            mBodyLottieView = new BodyLottieView(mContext, mParams.dialogParams
                    , mParams.lottieParams, mParams.createLottieListener);
            addViewByBody(mBodyLottieView);
        }
    }

    @Override
    public void refreshContent() {
    }
}
