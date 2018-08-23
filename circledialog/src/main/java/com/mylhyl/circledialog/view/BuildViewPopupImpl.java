package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.res.drawable.TriangleArrowDrawable;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * Created by hupei on 2018/8/15.
 */

public final class BuildViewPopupImpl extends BuildViewAbs {
    private static final float ARROW_WEIGHT = 0.1f;
    private ItemsView mItemsView;
    private View mAnchorView;
    private int[] mScreenSize;
    private int mStatusBarHeight;
    private Controller.OnDialogLocationListener mResizeSizeListener;

    public BuildViewPopupImpl(Context context, CircleParams params, Controller.OnDialogLocationListener listener
            , int[] screenSize, int statusBarHeight) {
        super(context, params);
        this.mResizeSizeListener = listener;
        this.mScreenSize = screenSize;
        this.mStatusBarHeight = statusBarHeight;
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
        final int arrowGravity = popupParams.arrowGravity;
        //对话框位置
        mParams.dialogParams.gravity = arrowDirection | arrowGravity;
        mAnchorView = popupParams.anchor;

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
        if (arrowDirection == PopupParams.DIRECTION_LEFT || arrowDirection == PopupParams.DIRECTION_RIGHT) {
            cardView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
        }
        mItemsView = new BodyRecyclerView(mContext, mParams.popupParams
                , mParams.dialogParams, mParams.rvItemListener);
        final View itemsViewView = mItemsView.getView();
        cardView.addView(itemsViewView);

        if (arrowDirection == PopupParams.DIRECTION_LEFT || arrowDirection == PopupParams.DIRECTION_TOP) {
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
                mParams.dialogParams.absoluteWidth = mRoot.getWidth();
                int arrowViewSize = (int) (mParams.dialogParams.absoluteWidth * ARROW_WEIGHT);
                LayoutParams arrowViewLayoutParams = (LayoutParams) arrowView.getLayoutParams();
                arrowViewLayoutParams.width = arrowViewSize;
                arrowViewLayoutParams.height = arrowViewSize;

                if (bottom != 0 && oldBottom != 0 && bottom == oldBottom) {
                    popupParams.arrowOffSet = arrowViewSize;
                    if (arrowDirection == PopupParams.DIRECTION_LEFT || arrowDirection == PopupParams.DIRECTION_RIGHT) {
                        int topMargin = popupParams.arrowOffSet;
                        if (popupParams.arrowGravity == PopupParams.GRAVITY_CENTER_VERTICAL) {
                            popupParams.arrowOffSet = (mRoot.getHeight() / 2);
                            topMargin = popupParams.arrowOffSet - arrowViewSize / 2;
                        } else if (popupParams.arrowGravity == PopupParams.GRAVITY_BOTTOM) {
                            topMargin = mRoot.getHeight() - popupParams.arrowOffSet * 2;
                        }
                        arrowViewLayoutParams.topMargin = topMargin;
                    } else {
                        int leftMargin = popupParams.arrowOffSet;
                        if (popupParams.arrowGravity == PopupParams.GRAVITY_CENTER_HORIZONTAL) {
                            popupParams.arrowOffSet = (mRoot.getWidth() / 2);
                            leftMargin = popupParams.arrowOffSet - arrowViewSize / 2;
                        } else if (popupParams.arrowGravity == PopupParams.GRAVITY_RIGHT) {
                            leftMargin = mRoot.getWidth() - popupParams.arrowOffSet * 2;
                        }
                        arrowViewLayoutParams.leftMargin = leftMargin;
                    }
                    mRoot.removeOnLayoutChangeListener(this);
                    resizeDialogSize(mParams.dialogParams, arrowDirection, arrowGravity
                            , arrowViewSize, popupParams.arrowOffSet);
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

    void resizeDialogSize(DialogParams dialogParams, int arrowDirection, int arrowGravity
            , int arrowViewSize, int arrowOffSet) {
        View view = mAnchorView;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int screenWidth = mScreenSize[0];

        int dialogX = arrowDirection == PopupParams.DIRECTION_TOP || arrowDirection == PopupParams.DIRECTION_BOTTOM
                ? view.getWidth() / 2 : view.getWidth();
        if (arrowGravity == PopupParams.GRAVITY_LEFT) {
            dialogX += location[0] - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == PopupParams.GRAVITY_RIGHT) {
            dialogX = screenWidth - location[0] - dialogX - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == PopupParams.GRAVITY_CENTER_HORIZONTAL) {
            dialogX = -1 * (screenWidth / 2 - location[0]) + dialogX;
        } else {
            dialogX += 0;
        }
        dialogParams.xOff = dialogX;

        int screenHeight = mScreenSize[1];
        int dialogY;
        if (arrowGravity == PopupParams.GRAVITY_TOP) {
            dialogY = location[1] - mStatusBarHeight + view.getHeight() / 2 - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == PopupParams.GRAVITY_BOTTOM) {
            dialogY = screenHeight - location[1] - view.getHeight() / 2 - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == PopupParams.GRAVITY_CENTER_VERTICAL) {
            dialogY = -1 * (screenHeight / 2 - location[1]) - mStatusBarHeight / 2 + view.getHeight() / 2;
        } else {
            if (arrowDirection == PopupParams.DIRECTION_TOP
                    && (arrowGravity == PopupParams.GRAVITY_LEFT || arrowGravity == PopupParams.GRAVITY_RIGHT
                    || arrowGravity == PopupParams.GRAVITY_CENTER_HORIZONTAL)) {
                dialogY = location[1] - mStatusBarHeight + view.getHeight();
            } else {
                dialogY = screenHeight - location[1];
            }
        }
        dialogParams.yOff = dialogY;

        if (mResizeSizeListener != null) {
            mResizeSizeListener.dialogAtLocation(dialogParams.xOff, dialogParams.yOff);
        }
    }
}
