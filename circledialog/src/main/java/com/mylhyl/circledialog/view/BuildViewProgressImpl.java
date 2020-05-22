package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.internal.CircleParams;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewProgressImpl extends AbsBuildView {
    private BodyProgressView mBodyProgressView;

    public BuildViewProgressImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();

        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams);
            addViewByBody(mBodyProgressView);
        }
    }

    @Override
    public BodyProgressView getBodyView() {
        return mBodyProgressView;
    }

    @Override
    public void refreshContent() {
        if (mBodyProgressView != null) {
            mBodyProgressView.refreshProgress();
        }
    }
}
