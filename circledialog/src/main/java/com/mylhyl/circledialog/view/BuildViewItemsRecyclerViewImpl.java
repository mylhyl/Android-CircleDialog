package com.mylhyl.circledialog.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.ItemsParams;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BuildViewItemsRecyclerViewImpl extends BuildViewItemsAbs {

    public BuildViewItemsRecyclerViewImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();
        if (mItemsView == null) {
            ItemsParams itemsParams = mParams.itemsParams;
            mItemsView = new BodyRecyclerView(mContext, itemsParams, mParams.dialogParams);
            if (itemsParams != null && itemsParams.itemDecoration != null
                    && itemsParams.layoutManager != null && itemsParams.dividerHeight > 0
                    && itemsParams.layoutManager instanceof LinearLayoutManager
                    && ((LinearLayoutManager) itemsParams.layoutManager).getOrientation()
                    == LinearLayoutManager.HORIZONTAL) {
                DividerView dividerView = new DividerView(mContext, LinearLayout.HORIZONTAL
                        , itemsParams.dividerHeight);
                addViewByBody(dividerView);
            }
            addViewByBody(mItemsView.getView());
        }
    }
}
