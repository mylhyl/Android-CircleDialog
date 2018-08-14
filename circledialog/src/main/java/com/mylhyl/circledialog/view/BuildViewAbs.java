package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.BuildView;
import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.view.listener.ButtonView;

/**
 * Created by hupei on 2017/3/29.
 */

abstract class BuildViewAbs implements BuildView {
    protected Context mContext;
    protected CircleParams mParams;
    protected ViewGroup mRoot;
    protected LinearLayout mRootCardViewByLinearLayout;
    private ButtonView mButtonView;

    public BuildViewAbs(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
    }

    protected final void buildTitleView(ViewGroup viewGroup) {
        if (mParams.titleParams != null) {
            TitleView titleView = new TitleView(mContext, mParams);
            viewGroup.addView(titleView);
        }
    }

    protected void buildRootView() {
        CardView cardView = new CardView(mContext);
        cardView.setCardElevation(0f);
        cardView.setCardBackgroundColor(Color.TRANSPARENT);
        cardView.setRadius(mParams.dialogParams.radius);

        mRootCardViewByLinearLayout = new LinearLayout(mContext);
        mRootCardViewByLinearLayout.setOrientation(LinearLayout.VERTICAL);
        cardView.addView(mRootCardViewByLinearLayout);

        mRoot = cardView;
    }

    @Override
    public final View getRootView() {
        return mRoot;
    }

    @Override
    public ButtonView buildButton() {
        if (mButtonView == null) {
            mButtonView = new MultipleButton(mContext, mParams);
            if (!mButtonView.isEmpty()) {
                DividerView dividerView = new DividerView(mContext, LinearLayout.HORIZONTAL);
                mRootCardViewByLinearLayout.addView(dividerView);
            }
        }
        if (mButtonView != null) {
            mRootCardViewByLinearLayout.addView(mButtonView.getView());
        }
        return mButtonView;
    }

    @Override
    public final void refreshButton() {
        if (mButtonView != null) {
            mButtonView.refreshText();
        }
    }
}
