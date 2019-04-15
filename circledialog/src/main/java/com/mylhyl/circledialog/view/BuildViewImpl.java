package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.BuildView;
import com.mylhyl.circledialog.CircleParams;
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
    }

    @Override
    public View buildRoot() {
        if (mRoot == null) {
            mRoot = new ScaleLinearLayout(mContext);
            mRoot.setOrientation(LinearLayout.VERTICAL);
        }
        return mRoot;
    }

    @Override
    public View buildTitle() {
        if (mTitleView == null) {
            mTitleView = new TitleView(mContext, mParams);
            mRoot.addView(mTitleView);
        }
        return mTitleView;
    }

    @Override
    public View refreshTitle() {
        if (mTitleView != null) {
            mTitleView.refreshTitle();
        }
        return mTitleView;
    }

    @Override
    public View buildCustomBodyView() {
        if (mCustomBodyView == null) {
            View bodyView = LayoutInflater.from(mContext).inflate(mParams.bodyViewId, mRoot, false);
            this.mCustomBodyView = bodyView;
            mRoot.addView(mCustomBodyView);
        }
        return mCustomBodyView;
    }

    @Override
    public View buildText() {
        if (mBodyTextView == null) {
            mBodyTextView = new BodyTextView(mContext, mParams);
            mRoot.addView(mBodyTextView);
        }
        return mBodyTextView;
    }

    @Override
    public View refreshText() {
        if (mBodyTextView != null) mBodyTextView.refreshText();
        return mBodyTextView;
    }

    @Override
    public ItemsView buildItems() {
        if (mItemsView == null) {
            if (mParams.rvItemListener != null || mParams.itemsParams.adapterRv != null)
                mItemsView = new BodyItemsRvView(mContext, mParams);
            else
                mItemsView = new BodyItemsView(mContext, mParams);
            mRoot.addView(mItemsView.getView());
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
    public ItemsView refreshItems() {
        if (mItemsView != null) mItemsView.refreshItems();
        return mItemsView;
    }

    @Override
    public View buildProgress() {
        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams);
            mRoot.addView(mBodyProgressView);
        }
        return mBodyProgressView;
    }

    @Override
    public void buildLottie() {
        if (mBodyLottieView == null) {
            mBodyLottieView = new BodyLottieView(mContext, mParams);
            mRoot.addView(mBodyLottieView);
        }
    }


    @Override
    public View refreshProgress() {
        if (mBodyProgressView != null) mBodyProgressView.refreshProgress();
        return mBodyProgressView;
    }

    @Override
    public InputView buildInput() {
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
            mRoot.addView(mMultipleButton.getView());
        }
        return mMultipleButton;
    }

    @Override
    public ButtonView refreshMultipleButtonText() {
        if (mMultipleButton != null)
            mMultipleButton.refreshText();
        return mMultipleButton;
    }

    @Override
    public View getView() {
        return mRoot;
    }

    @Override
    public InputView getInputView() {
        return mBodyInputView;
    }
}
