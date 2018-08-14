package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mylhyl.circledialog.CircleParams;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewCustomBodyImpl extends BuildViewAbs {
    private View mCustomBodyView;

    public BuildViewCustomBodyImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public View getBodyView() {
        return mCustomBodyView;
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView(mRootCardViewByLinearLayout);

        if (mCustomBodyView == null) {
            mCustomBodyView = LayoutInflater.from(mContext)
                    .inflate(mParams.bodyViewId, mRootCardViewByLinearLayout, false);
            mRootCardViewByLinearLayout.addView(mCustomBodyView);
        }
    }

    @Override
    public void refreshContent() {

    }
}
