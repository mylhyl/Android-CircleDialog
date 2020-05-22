package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * view的层次结构
 * <pre>
 *      ╚--LinearLayout
 *      ╚--CardView
 *          ╚--LinearLayout
 *              ╚--TitleView
 *              ╚--BodyView
 *      ╚--ButtonView
 * </pre>
 * Created by hupei on 2017/3/29.
 */

abstract class AbsBuildViewItems extends AbsBuildView {
    protected ItemsView mItemsView;

    public AbsBuildViewItems(Context context, CircleParams params) {
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
    public void buildRootView() {
        LinearLayout rootLayout = new LinearLayout(mContext);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        CardView cardView = createCardView();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的下距离
        layoutParams.bottomMargin = Controller.dp2px(mContext, mParams.itemsParams.bottomMargin);
        cardView.setLayoutParams(layoutParams);

        rootLayout.addView(cardView);

        LinearLayout rootCardViewByLinearLayout = createLinearLayout();
        cardView.addView(rootCardViewByLinearLayout);

        mRoot = rootLayout;
    }

    @Override
    public ButtonView buildButton() {
        ItemsButton itemsButton = new ItemsButton(mContext, mParams);
        mRoot.addView(itemsButton);
        return itemsButton;
    }

}
