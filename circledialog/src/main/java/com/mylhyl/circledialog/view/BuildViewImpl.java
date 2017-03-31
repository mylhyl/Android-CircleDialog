package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.BuildView;
import com.mylhyl.circledialog.params.CircleParams;

/**
 * Created by hupei on 2017/3/29.
 */

public class BuildViewImpl implements BuildView {
    private Context mContext;
    private CircleParams mParams;
    private LinearLayout mRoot;
    private TitleView mTitleView;
    private BodyTextView mBodyTextView;
    private BodyItemsView mBodyItemsView;
    private BodyProgressView mBodyProgressView;
    private BodyInputView mBodyInputView;
    private ItemsButton mItemsButton;
    private MultipleButton mMultipleButton;
    private SingleButton mSingleButton;

    public BuildViewImpl(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
    }

    @Override
    public void buildRoot() {
        if (mRoot == null) {
            mRoot = new ScaleLinearLayout(mContext);
            mRoot.setOrientation(LinearLayout.VERTICAL);
        }
    }

    @Override
    public void buildTitle() {
        if (mTitleView == null) {
            mTitleView = new TitleView(mContext, mParams);
            mRoot.addView(mTitleView);
        }
    }

    @Override
    public void buildText() {
        if (mBodyTextView == null) {
            mBodyTextView = new BodyTextView(mContext, mParams);
            mRoot.addView(mBodyTextView);
        }
    }

    @Override
    public void refreshText() {
        if (mBodyTextView != null) mBodyTextView.refreshText();
    }

    @Override
    public void buildItems() {
        if (mBodyItemsView == null) {
            mBodyItemsView = new BodyItemsView(mContext, mParams);
            mRoot.addView(mBodyItemsView);
        }
    }

    @Override
    public void buildItemsButton() {
        if (mItemsButton == null) {
            mItemsButton = new ItemsButton(mContext, mParams);
            mRoot.addView(mItemsButton);
        }
    }

    @Override
    public void refreshItems() {
        if (mBodyItemsView != null) mBodyItemsView.refreshItems();
    }

    @Override
    public void buildProgress() {
        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams);
            mRoot.addView(mBodyProgressView);
        }
    }


    @Override
    public void refreshProgress() {
        if (mBodyProgressView != null) mBodyProgressView.refreshProgress();
    }

    @Override
    public void buildInput() {
        if (mBodyInputView == null) {
            mBodyInputView = new BodyInputView(mContext, mParams);
            mRoot.addView(mBodyInputView);
        }
    }

    @Override
    public void buildMultipleButton() {
        if (mMultipleButton == null) {
            mMultipleButton = new MultipleButton(mContext, mParams);
            DividerView dividerView = new DividerView(mContext);
            dividerView.setVertical();
            mRoot.addView(dividerView);
            mRoot.addView(mMultipleButton);
        }
    }

    @Override
    public void buildSingleButton() {
        if (mSingleButton == null) {
            mSingleButton = new SingleButton(mContext, mParams);
            DividerView dividerViewV = new DividerView(mContext);
            dividerViewV.setVertical();
            mRoot.addView(dividerViewV);
            mRoot.addView(mSingleButton);
        }
    }

    @Override
    public void regInputListener() {
        if (mSingleButton != null && mBodyInputView != null)
            mSingleButton.regOnInputClickListener(mBodyInputView.getInput());

        if (mMultipleButton != null && mBodyInputView != null)
            mMultipleButton.regOnInputClickListener(mBodyInputView.getInput());
    }

    @Override
    public View getView() {
        return mRoot;
    }
}
