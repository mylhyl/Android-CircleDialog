package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:10.
 */
public final class BuildViewAdImpl extends BuildViewAbs {
    private BodyAdView mBodyAdView;

    public BuildViewAdImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public BodyAdView getBodyView() {
        return mBodyAdView;
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();
        if (mBodyAdView == null) {
            mBodyAdView = new BodyAdView(mContext, mParams.dialogParams, mParams.adParams);
            addViewByBody(mBodyAdView);
        }
    }

    @Override
    public void refreshContent() {

    }
}
