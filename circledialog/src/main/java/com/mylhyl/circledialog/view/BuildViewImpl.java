package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.BuildView;
import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.scale.ScaleUtils;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BuildViewImpl implements BuildView {
    private Context mContext;
    private CircleParams mParams;
    private LinearLayout mRoot;
    private BodyTextView mBodyTextView;
    private CardView mItemsRootView;
    private ScaleLinearLayout mItemsContentView;
    private ItemsView mItemsView;
    private BodyProgressView mBodyProgressView;
    private ButtonView mMultipleButton;

    public BuildViewImpl(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
        this.mRoot = new ScaleLinearLayout(mContext);
        this.mRoot.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void buildRootView() {

    }

    @Override
    public void buildRootItemsView() {

    }

    @Override
    public void buildTitleViewForRoot() {
        TitleView titleView = new TitleView(mContext, mParams);
        mRoot.addView(titleView);
    }

    @Override
    public View buildCustomBodyView() {
        View customBodyView = LayoutInflater.from(mContext).inflate(mParams.bodyViewId, mRoot, false);
        mRoot.addView(customBodyView);
        return customBodyView;
    }

    @Override
    public void buildTextView() {
        if (mBodyTextView == null) {
            mBodyTextView = new BodyTextView(mContext, mParams);
            mRoot.addView(mBodyTextView);
        }
    }

    @Override
    public void refreshTextView() {
        if (mBodyTextView != null) mBodyTextView.refreshText();
    }

    @Override
    public void buildItemsContentView() {
        CardView cardView = new CardView(mContext);
        cardView.setCardElevation(0f);
        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        cardView.setRadius(mParams.dialogParams.radius);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的下距离
        layoutParams.bottomMargin = ScaleUtils.scaleValue(mParams.itemsParams.bottomMargin);
        cardView.setLayoutParams(layoutParams);
        mRoot.addView(cardView);

        if (mItemsContentView == null) {
            mItemsContentView = new ScaleLinearLayout(mContext);
            mItemsContentView.setOrientation(LinearLayout.VERTICAL);
            cardView.addView(mItemsContentView);

            TitleView titleView = new TitleView(mContext, mParams);
            mItemsContentView.addView(titleView);

        }
    }

    @Override
    public ItemsView buildItemsListView() {
        if (mItemsView == null) mItemsView = new BodyItemsListView(mContext, mParams);
        mItemsContentView.addView(mItemsView.getView());
        return mItemsView;
    }

    @Override
    public ItemsView buildItemsRecyclerView() {
        if (mItemsView == null) mItemsView = new BodyItemsRecyclerView(mContext, mParams);
        ItemsParams itemsParams = mParams.itemsParams;
        if (itemsParams != null && itemsParams.itemDecoration == null
                && itemsParams.layoutManager != null && itemsParams.dividerHeight > 0
                && itemsParams.layoutManager instanceof LinearLayoutManager
                && ((LinearLayoutManager) itemsParams.layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
            DividerView dividerView = new DividerView(mContext, LinearLayout.HORIZONTAL, itemsParams.dividerHeight);
            mItemsContentView.addView(dividerView);
        }
        mItemsContentView.addView(mItemsView.getView());
        return mItemsView;
    }

    @Override
    public ButtonView buildItemsButton() {
        ItemsButton itemsButton = new ItemsButton(mContext, mParams);
        mRoot.addView(itemsButton);
        return itemsButton;
    }

    @Override
    public void refreshItems() {
        if (mItemsView != null) mItemsView.refreshItems();
    }

    @Override
    public void buildProgress() {
        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams);
            mRoot.addView(mBodyProgressView);
        }
    }

    @Override
    public void buildLottie() {
        BodyLottieView bodyLottieView = new BodyLottieView(mContext, mParams);
        mRoot.addView(bodyLottieView);
    }

    @Override
    public void refreshProgress() {
        if (mBodyProgressView != null) mBodyProgressView.refreshProgress();
    }

    @Override
    public InputView buildInput() {
        BodyInputView bodyInputView = new BodyInputView(mContext, mParams);
        mRoot.addView(bodyInputView.getView());
        return bodyInputView;
    }

    @Override
    public ButtonView buildMultipleButton() {
        if (mMultipleButton == null) {
            mMultipleButton = new MultipleButton(mContext, mParams);
            if (!mMultipleButton.isEmpty()) {
                DividerView dividerView = new DividerView(mContext, LinearLayout.HORIZONTAL);
                mRoot.addView(dividerView);
            }
        }
        if (mMultipleButton != null)
            mRoot.addView(mMultipleButton.getView());
        return mMultipleButton;
    }

    @Override
    public void refreshMultipleButtonText() {
        if (mMultipleButton != null) mMultipleButton.refreshText();
    }

    @Override
    public View getRootView() {
        return mRoot;
    }
}
