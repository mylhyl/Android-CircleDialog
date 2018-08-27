package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleParams;

/**
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewInputImpl extends BuildViewAbs {
    private BodyInputView mBodyInputView;
    private BaseCircleDialog mDialog;

    public BuildViewInputImpl(Context context, BaseCircleDialog dialog, CircleParams params) {
        super(context, params);
        this.mDialog = dialog;
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
            mBodyInputView = new BodyInputView(mContext, mDialog, mParams);
            mRootCardViewByLinearLayout.addView(mBodyInputView.getView());
        }
    }

    @Override
    public void refreshContent() {

    }
}
