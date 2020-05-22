package com.mylhyl.circledialog.view;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.params.ItemsParams;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BuildViewItemsRecyclerViewImpl extends AbsBuildViewItems {

    public BuildViewItemsRecyclerViewImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();

        if (mItemsView != null) {
            return;
        }
        ItemsParams itemsParams = mParams.itemsParams;
        mItemsView = new BodyRecyclerView(mContext, itemsParams, mParams.dialogParams);
        RecyclerView.LayoutManager layoutManager = itemsParams.layoutManager;
        int dividerHeight = itemsParams.dividerHeight;
        if (itemsParams.itemDecoration != null && dividerHeight > 0
                && layoutManager != null && layoutManager instanceof LinearLayoutManager
                && ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {

            DividerView dividerView = new DividerView(mContext, LinearLayout.HORIZONTAL, dividerHeight);
            addViewByBody(dividerView);
        }
        addViewByBody(mItemsView.getView());
    }
}
