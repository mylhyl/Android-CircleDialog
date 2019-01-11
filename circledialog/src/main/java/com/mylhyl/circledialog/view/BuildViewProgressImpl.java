package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewProgressImpl extends BuildViewAbs {
    private BodyProgressView mBodyProgressView;

    public BuildViewProgressImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public BodyProgressView getBodyView() {
        return mBodyProgressView;
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();

        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams.dialogParams
                    , mParams.progressParams, mParams.createProgressListener);
            addViewByBody(mBodyProgressView);
        }
    }

    @Override
    public void refreshContent() {
        if (mBodyProgressView != null) {
            mBodyProgressView.refreshProgress();
        }
    }
}
