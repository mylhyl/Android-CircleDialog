package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.mylhyl.circledialog.internal.BuildView;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.params.CloseParams;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.CloseView;

/**
 * view的层次结构
 * <pre>
 * CardView
 *    ╚--LinearLayout
 *          ╚--TitleView
 *          ╚--BodyView
 *          ╚--ButtonView
 * </pre>
 * Created by hupei on 2017/3/29.
 */

abstract class AbsBuildView implements BuildView {
    private static final double COS_45 = Math.cos(Math.toRadians(45));
    protected Context mContext;
    protected CircleParams mParams;
    protected ViewGroup mRoot;
    private LinearLayout mRootCardViewByLinearLayout;
    private TitleView mTitleView;
    private ButtonView mButtonView;

    public AbsBuildView(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
    }

    protected final View layoutInflaterFrom(int resource) {
        return LayoutInflater.from(mContext).inflate(resource, mRootCardViewByLinearLayout, false);
    }

    protected final void addViewByBody(View child) {
        ViewParent parent = child.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(child);
        }
        mRootCardViewByLinearLayout.addView(child);
    }

    @Override
    public void buildRootView() {
        createLinearLayout();
        if (Controller.SDK_LOLLIPOP) {
            CardView cardView = createCardView();
            cardView.addView(mRootCardViewByLinearLayout);
            if (mParams.closeParams == null) {
                mRoot = cardView;
            } else {
                LinearLayout rootLayout = new LinearLayout(mContext);
                rootLayout.setBackgroundColor(Color.TRANSPARENT);
                rootLayout.setOrientation(LinearLayout.VERTICAL);
                rootLayout.addView(cardView);
                mRoot = rootLayout;
            }
        } else {
            mRoot = mRootCardViewByLinearLayout;
        }
    }

    @Override
    public void buildTitleView() {
        if (mParams.titleParams != null) {
            mTitleView = new TitleView(mContext, mParams);
            mRootCardViewByLinearLayout.addView(mTitleView);
        }
    }

    @Override
    public ButtonView buildButton() {
        mButtonView = new ConfirmButton(mContext, mParams);
        if (!mButtonView.isEmpty()) {
            DividerView dividerView = new DividerView(mContext, LinearLayout.HORIZONTAL);
            mRootCardViewByLinearLayout.addView(dividerView);
        }
        mRootCardViewByLinearLayout.addView(mButtonView.getView());
        return mButtonView;
    }

    @Override
    public CloseView buildCloseImgView() {
        CloseParams closeParams = mParams.closeParams;
        CloseImgView closeView = new CloseImgView(mContext, closeParams);
        LinearLayout.LayoutParams layoutParamsClose = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //位置
        if (closeParams.closeGravity == CloseParams.CLOSE_TOP_LEFT
                || closeParams.closeGravity == CloseParams.CLOSE_BOTTOM_LEFT) {
            layoutParamsClose.gravity = Gravity.LEFT;
        } else if (closeParams.closeGravity == CloseParams.CLOSE_TOP_CENTER
                || closeParams.closeGravity == CloseParams.CLOSE_BOTTOM_CENTER) {
            layoutParamsClose.gravity = Gravity.CENTER_HORIZONTAL;
        } else {
            layoutParamsClose.gravity = Gravity.RIGHT;
        }
        closeView.setLayoutParams(layoutParamsClose);
        if (closeParams.closeGravity == CloseParams.CLOSE_TOP_LEFT
                || closeParams.closeGravity == CloseParams.CLOSE_TOP_CENTER
                || closeParams.closeGravity == CloseParams.CLOSE_TOP_RIGHT) {
            mRoot.addView(closeView, 0);
        } else {
            mRoot.addView(closeView);
        }
        return closeView;
    }

    @Override
    public final View getRootView() {
        return mRoot;
    }

    @Override
    public void refreshTitle() {
        if (mTitleView != null) {
            mTitleView.refreshText();
        }
    }

    @Override
    public final void refreshButton() {
        if (mButtonView != null) {
            mButtonView.refreshText();
        }
    }

    protected CardView createCardView() {
        int radius = Controller.dp2px(mContext, mParams.dialogParams.radius);

        CardView cardView = new CardView(mContext);
        cardView.setCardElevation(0f);

        if (Controller.SDK_LOLLIPOP) {
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
        } else {
            cardView.setPreventCornerOverlap(false);
            cardView.setCardBackgroundColor(mParams.dialogParams.backgroundColor);
            cardView.setUseCompatPadding(true);
            int contentPadding = (int) Math.ceil(radius - COS_45 * radius);
            cardView.setContentPadding(contentPadding, contentPadding, contentPadding, contentPadding);
        }
        cardView.setRadius(radius);
        return cardView;
    }

    protected LinearLayout createLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return mRootCardViewByLinearLayout = linearLayout;
    }
}
