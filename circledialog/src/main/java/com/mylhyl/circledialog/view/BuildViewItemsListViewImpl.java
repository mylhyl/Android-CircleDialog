package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BuildViewItemsListViewImpl extends BuildViewItemsAbs {

    public BuildViewItemsListViewImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView(mRootCardViewByLinearLayout);
        if (mItemsView == null) {
            mItemsView = new BodyListView(mContext, mParams.dialogParams, mParams.itemsParams);
            mRootCardViewByLinearLayout.addView(mItemsView.getView());
        }
    }

}
