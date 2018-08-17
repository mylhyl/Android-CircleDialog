package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.res.drawable.TriangleArrowDrawable;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * Created by hupei on 2018/8/15.
 */

public final class BuildViewPopupImpl extends BuildViewAbs {
    private static final float ARROW_WEIGHT = 0.1f;
    private ItemsView mItemsView;
    private DisplayMetrics dm;

    public BuildViewPopupImpl(Context context, CircleParams params, DisplayMetrics dm) {
        super(context, params);
        this.dm = dm;
    }

    @Override
    public ItemsView getBodyView() {
        return mItemsView;
    }

    @Override
    public void buildBodyView() {
        mParams.dialogParams.absoluteWidth = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupParams popupParams = mParams.popupParams;
        final int arrowDirection = popupParams.arrowDirection;

        LinearLayout rootLinearLayout = buildLinearLayout();
        //箭头在左右情况，布局改为水平
        if (arrowDirection == PopupParams.DIRECTION_LEFT || arrowDirection == PopupParams.DIRECTION_RIGHT) {
            rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        mRoot = rootLinearLayout;

        final View arrowView = new View(mContext);
        int backgroundColor = popupParams.backgroundColor != 0
                ? popupParams.backgroundColor : mParams.dialogParams.backgroundColor;
        Drawable arrowDrawable = new TriangleArrowDrawable(arrowDirection, backgroundColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            arrowView.setBackground(arrowDrawable);
        } else {
            arrowView.setBackgroundDrawable(arrowDrawable);
        }

        CardView cardView = buildCardView();
        mItemsView = new BodyRecyclerView(mContext, mParams.popupParams
                , mParams.dialogParams, mParams.rvItemListener);
        final View itemsViewView = mItemsView.getView();
        cardView.addView(itemsViewView);

        if (arrowDirection == PopupParams.DIRECTION_LEFT
                || arrowDirection == PopupParams.DIRECTION_TOP) {
            mRoot.addView(arrowView);
            mRoot.addView(cardView);
        } else {
            mRoot.addView(cardView);
            mRoot.addView(arrowView);
        }

        mRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom
                    , int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mParams.dialogParams.absoluteWidth = itemsViewView.getWidth();
                int arrowViewWidth = (int) (itemsViewView.getWidth() * ARROW_WEIGHT);
                LayoutParams arrowViewLayoutParams = (LayoutParams) arrowView.getLayoutParams();
                if (arrowViewLayoutParams == null) {
                    arrowViewLayoutParams = new LayoutParams(arrowViewWidth, arrowViewWidth);
                } else {
                    arrowViewLayoutParams.width = arrowViewWidth;
                    arrowViewLayoutParams.height = arrowViewWidth;
                }
                if (bottom != 0 && oldBottom != 0
                        && bottom == oldBottom) {
                    if (popupParams.arrowGravity == PopupParams.GRAVITY_CENTER) {
                        arrowViewLayoutParams.leftMargin = (mParams.dialogParams.absoluteWidth / 2) - (arrowViewWidth / 2);
                    } else if (popupParams.arrowGravity == PopupParams.GRAVITY_RIGHT) {
                        arrowViewLayoutParams.leftMargin = (int) (mParams.dialogParams.absoluteWidth * (1 - ARROW_WEIGHT)) - arrowViewWidth;
                    } else {
                        arrowViewLayoutParams.leftMargin = arrowViewWidth;
                    }
                    itemsViewView.removeOnLayoutChangeListener(this);
                }
                arrowView.setLayoutParams(arrowViewLayoutParams);
            }
        });
    }

    @Override
    public void refreshContent() {
        if (mItemsView != null) {
            mItemsView.refreshItems();
        }
    }
}
