package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * Created by hupei on 2017/3/29.
 */

abstract class BuildViewItemsAbs extends BuildViewAbs {
    protected ItemsView mItemsView;

    public BuildViewItemsAbs(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public ItemsView getBodyView() {
        return mItemsView;
    }

    @Override
    public void refreshContent() {
        if (mItemsView != null) {
            mItemsView.refreshItems();
        }
    }

    @Override
    protected void buildRootView() {
        LinearLayout rootItem = new LinearLayout(mContext);
        rootItem.setOrientation(LinearLayout.VERTICAL);

        CardView cardView = new CardView(mContext);
        cardView.setCardElevation(0f);
        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        cardView.setRadius(mParams.dialogParams.radius);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的下距离
        layoutParams.bottomMargin = mParams.itemsParams.bottomMargin;
        cardView.setLayoutParams(layoutParams);

        rootItem.addView(cardView);

        LinearLayout rootCardViewByLinearLayout = buildLinearLayout();
        cardView.addView(rootCardViewByLinearLayout);

        mRoot = rootItem;
    }

    @Override
    public ButtonView buildButton() {
        ItemsButton itemsButton = new ItemsButton(mContext, mParams.dialogParams
                , mParams.negativeParams, mParams.positiveParams, mParams.neutralParams
                , mParams.createButtonListener);
        mRoot.addView(itemsButton);
        return itemsButton;
    }

}
