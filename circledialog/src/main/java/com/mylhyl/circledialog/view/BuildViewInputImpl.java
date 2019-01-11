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
        buildTitleView();

        if (mBodyInputView == null) {
            mBodyInputView = new BodyInputView(mContext, mParams.dialogParams, mParams.titleParams
                    , mParams.subTitleParams, mParams.inputParams
                    , mParams.inputCounterChangeListener, mParams.createInputListener);
            addViewByBody(mBodyInputView.getView());
        }
    }

    @Override
    public void refreshContent() {

    }
}
