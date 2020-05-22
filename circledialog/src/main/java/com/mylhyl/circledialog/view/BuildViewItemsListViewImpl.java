package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.internal.CircleParams;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BuildViewItemsListViewImpl extends AbsBuildViewItems {

    public BuildViewItemsListViewImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();
        if (mItemsView != null) {
            return;
        }
        mItemsView = new BodyListView(mContext, mParams.dialogParams, mParams.itemsParams);
        addViewByBody(mItemsView.getView());
    }

}
