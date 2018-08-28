package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewInputImpl extends BuildViewAbs {
    private BodyInputView mBodyInputView;

    public BuildViewInputImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public BodyInputView getBodyView() {
        return mBodyInputView;
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView(mRootCardViewByLinearLayout);

        if (mBodyInputView == null) {
            mBodyInputView = new BodyInputView(mContext, mParams);
            mRootCardViewByLinearLayout.addView(mBodyInputView.getView());
        }
    }

    @Override
    public void refreshContent() {

    }
}
