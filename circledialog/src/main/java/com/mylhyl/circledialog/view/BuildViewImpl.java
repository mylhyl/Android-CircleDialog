package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.BuildView;
import com.mylhyl.circledialog.CircleParams;
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
    private TitleView mTitleView;
    private BodyTextView mBodyTextView;
    private ItemsView mItemsView;
    private BodyProgressView mBodyProgressView;
    private BodyLottieView mBodyLottieView;
    private InputView mBodyInputView;
    private ItemsButton mItemsButton;
    private ButtonView mMultipleButton;
    private View mCustomBodyView;

    public BuildViewImpl(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
        this.mRoot = new ScaleLinearLayout(mContext);
        this.mRoot.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void buildTitleView() {
        if (mTitleView == null) mTitleView = new TitleView(mContext, mParams);
    }

    @Override
    public View buildCustomBodyView() {
        addTitleView();
        if (mCustomBodyView == null) {
            View bodyView = LayoutInflater.from(mContext).inflate(mParams.bodyViewId, mRoot, false);
            this.mCustomBodyView = bodyView;
            mRoot.addView(mCustomBodyView);
        }
        return mCustomBodyView;
    }

    @Override
    public void buildTextView() {
        addTitleView();
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
    public ItemsView buildItemsView() {
        if (mItemsView == null) {
            if (mParams.itemListener != null || mParams.itemsParams.adapter != null)
                mItemsView = new BodyItemsView(mContext, mParams);
            else if (mParams.rvItemListener != null || mParams.itemsParams.adapterRv != null)
                mItemsView = new BodyItemsRvView(mContext, mParams);

        }
        if (mItemsView != null) {
            CardView cardView = new CardView(mContext);
            cardView.setCardElevation(0f);
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            cardView.setRadius(mParams.dialogParams.radius);

            ScaleLinearLayout linearLayout = new ScaleLinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            if (mTitleView != null)
                linearLayout.addView(mTitleView);
            linearLayout.addView(mItemsView.getView());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            //设置列表与按钮之间的下距离
            layoutParams.bottomMargin = ScaleUtils.scaleValue(mParams.itemsParams.bottomMargin);
            cardView.setLayoutParams(layoutParams);
            cardView.addView(linearLayout);
            mRoot.addView(cardView);
        }
        return mItemsView;
    }

    @Override
    public ButtonView buildItemsButton() {
        if (mItemsButton == null) {
            mItemsButton = new ItemsButton(mContext, mParams);
            mRoot.addView(mItemsButton);
        }
        return mItemsButton;
    }

    @Override
    public void refreshItems() {
        if (mItemsView != null) mItemsView.refreshItems();
    }

    @Override
    public void buildProgress() {
        addTitleView();
        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams);
            mRoot.addView(mBodyProgressView);
        }
    }

    @Override
    public void buildLottie() {
        addTitleView();
        if (mBodyLottieView == null) {
            mBodyLottieView = new BodyLottieView(mContext, mParams);
            mRoot.addView(mBodyLottieView);
        }
    }

    @Override
    public void refreshProgress() {
        if (mBodyProgressView != null) mBodyProgressView.refreshProgress();
    }

    @Override
    public InputView buildInput() {
        addTitleView();
        if (mBodyInputView == null) {
            mBodyInputView = new BodyInputView(mContext, mParams);
            mRoot.addView(mBodyInputView.getView());
        }
        return mBodyInputView;
    }

    @Override
    public ButtonView buildMultipleButton() {
        if (mMultipleButton == null) {
            mMultipleButton = new MultipleButton(mContext, mParams);
            if (!mMultipleButton.isEmpty()) {
                DividerView dividerView = new DividerView(mContext);
                dividerView.setVertical();
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

    @Override
    public InputView getInputView() {
        return mBodyInputView;
    }

    private void addTitleView() {
        if (mRoot != null && mTitleView != null) mRoot.addView(mTitleView);
    }
}
