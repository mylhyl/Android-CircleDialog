package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.res.drawable.TriangleArrowDrawable;
import com.mylhyl.circledialog.view.listener.ItemsView;

import static com.mylhyl.circledialog.params.PopupParams.ARROW_BOTTOM_CENTER;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_BOTTOM_LEFT;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_BOTTOM_RIGHT;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_LEFT_BOTTOM;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_LEFT_CENTER;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_LEFT_TOP;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_RIGHT_BOTTOM;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_RIGHT_CENTER;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_RIGHT_TOP;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_TOP_CENTER;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_TOP_LEFT;
import static com.mylhyl.circledialog.params.PopupParams.ARROW_TOP_RIGHT;

/**
 * Created by hupei on 2018/8/15.
 */

public final class BuildViewPopupImpl extends BuildViewAbs {

    private static final int GRAVITY_TOP_CENTER = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
    private static final int GRAVITY_BOTTOM_CENTER = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    private static final int GRAVITY_LEFT_TOP = Gravity.LEFT | Gravity.TOP;
    private static final int GRAVITY_LEFT_BOTTOM = Gravity.LEFT | Gravity.BOTTOM;
    private static final int GRAVITY_LEFT_CENTER = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private static final int GRAVITY_RIGHT_TOP = Gravity.RIGHT | Gravity.TOP;
    private static final int GRAVITY_RIGHT_BOTTOM = Gravity.RIGHT | Gravity.BOTTOM;
    private static final int GRAVITY_RIGHT_CENTER = Gravity.RIGHT | Gravity.CENTER_VERTICAL;

    private static final float ARROW_WEIGHT = 0.1f;
    private ItemsView mItemsView;
    private int mArrowDirection;
    private int mArrowGravity;
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
        if (mParams.dialogParams.width > 0f && mParams.dialogParams.width < 1f
                && mParams.dialogParams.absoluteWidth == 0)
            mParams.dialogParams.absoluteWidth = LayoutParams.WRAP_CONTENT;

        final PopupParams popupParams = mParams.popupParams;
        mAnchorView = popupParams.anchor;

        switch (popupParams.arrowGravity) {
            case ARROW_LEFT_TOP:
                mArrowDirection = Gravity.LEFT;
                mArrowGravity = Gravity.TOP;
                mParams.dialogParams.gravity = GRAVITY_LEFT_TOP;
                break;
            case ARROW_TOP_LEFT:
                mArrowDirection = Gravity.TOP;
                mArrowGravity = Gravity.LEFT;
                mParams.dialogParams.gravity = GRAVITY_LEFT_TOP;
                break;
            case ARROW_LEFT_BOTTOM:
                mArrowDirection = Gravity.LEFT;
                mArrowGravity = Gravity.BOTTOM;
                mParams.dialogParams.gravity = GRAVITY_LEFT_BOTTOM;
                break;
            case ARROW_BOTTOM_LEFT:
                mArrowDirection = Gravity.BOTTOM;
                mArrowGravity = Gravity.LEFT;
                mParams.dialogParams.gravity = GRAVITY_LEFT_BOTTOM;
                break;
            case ARROW_LEFT_CENTER:
                mArrowDirection = Gravity.LEFT;
                mArrowGravity = Gravity.CENTER_VERTICAL;
                mParams.dialogParams.gravity = GRAVITY_LEFT_CENTER;
                break;
            case ARROW_RIGHT_TOP:
                mArrowDirection = Gravity.RIGHT;
                mArrowGravity = Gravity.TOP;
                mParams.dialogParams.gravity = GRAVITY_RIGHT_TOP;
                break;
            case ARROW_TOP_RIGHT:
                mArrowDirection = Gravity.TOP;
                mArrowGravity = Gravity.RIGHT;
                mParams.dialogParams.gravity = GRAVITY_RIGHT_TOP;
                break;
            case ARROW_RIGHT_BOTTOM:
                mArrowDirection = Gravity.RIGHT;
                mArrowGravity = Gravity.BOTTOM;
                mParams.dialogParams.gravity = GRAVITY_RIGHT_BOTTOM;
                break;
            case ARROW_BOTTOM_RIGHT:
                mArrowDirection = Gravity.BOTTOM;
                mArrowGravity = Gravity.RIGHT;
                mParams.dialogParams.gravity = GRAVITY_RIGHT_BOTTOM;
                break;
            case ARROW_RIGHT_CENTER:
                mArrowDirection = Gravity.RIGHT;
                mArrowGravity = Gravity.CENTER_VERTICAL;
                mParams.dialogParams.gravity = GRAVITY_RIGHT_CENTER;
                break;
            case ARROW_TOP_CENTER:
                mArrowDirection = Gravity.TOP;
                mArrowGravity = Gravity.CENTER_HORIZONTAL;
                mParams.dialogParams.gravity = GRAVITY_TOP_CENTER;
                break;
            case ARROW_BOTTOM_CENTER:
                mArrowDirection = Gravity.BOTTOM;
                mArrowGravity = Gravity.CENTER_HORIZONTAL;
                mParams.dialogParams.gravity = GRAVITY_BOTTOM_CENTER;
                break;
        }

        LinearLayout rootLinearLayout = buildLinearLayout();
        //箭头在左右情况，布局改为水平
        if (mArrowDirection == Gravity.LEFT || mArrowDirection == Gravity.RIGHT) {
            rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }

        mRoot = rootLinearLayout;

        final View arrowView = new View(mContext);
        int backgroundColor = popupParams.backgroundColor != 0
                ? popupParams.backgroundColor : mParams.dialogParams.backgroundColor;
        Drawable arrowDrawable = new TriangleArrowDrawable(mArrowDirection, backgroundColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            arrowView.setBackground(arrowDrawable);
        } else {
            arrowView.setBackgroundDrawable(arrowDrawable);
        }

        CardView cardView = buildCardView();
        if (mArrowDirection == Gravity.LEFT || mArrowDirection == Gravity.RIGHT) {
            cardView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
        }
        mItemsView = new BodyRecyclerView(mContext, mParams.popupParams
                , mParams.dialogParams, mParams.rvItemListener);
        final View itemsViewView = mItemsView.getView();
        cardView.addView(itemsViewView);

        if (mArrowDirection == Gravity.LEFT || mArrowDirection == Gravity.TOP) {
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

                if (mParams.dialogParams.absoluteWidth == LayoutParams.WRAP_CONTENT
                        || mParams.dialogParams.absoluteWidth != LayoutParams.MATCH_PARENT)
                    mParams.dialogParams.absoluteWidth = mRoot.getWidth();

                int arrowViewSize = (int) (mParams.dialogParams.absoluteWidth * ARROW_WEIGHT);
                LayoutParams arrowViewLayoutParams = (LayoutParams) arrowView.getLayoutParams();
                arrowViewLayoutParams.width = arrowViewSize;
                arrowViewLayoutParams.height = arrowViewSize;

                if (bottom != 0 && oldBottom != 0 && bottom == oldBottom) {
                    popupParams.arrowOffSet = arrowViewSize;
                    if (mArrowDirection == Gravity.LEFT || mArrowDirection == Gravity.RIGHT) {
                        int topMargin = popupParams.arrowOffSet;
                        if (mArrowGravity == Gravity.CENTER_VERTICAL) {
                            popupParams.arrowOffSet = (mRoot.getHeight() / 2);
                            topMargin = popupParams.arrowOffSet - arrowViewSize / 2;
                        } else if (mArrowGravity == Gravity.BOTTOM) {
                            topMargin = mRoot.getHeight() - popupParams.arrowOffSet * 2;
                        }
                        arrowViewLayoutParams.topMargin = topMargin;
                    } else {
                        int leftMargin = popupParams.arrowOffSet;
                        if (mArrowGravity == Gravity.CENTER_HORIZONTAL) {
                            popupParams.arrowOffSet = (mRoot.getWidth() / 2);
                            leftMargin = popupParams.arrowOffSet - arrowViewSize / 2;
                        } else if (mArrowGravity == Gravity.RIGHT) {
                            leftMargin = mRoot.getWidth() - popupParams.arrowOffSet * 2;
                        }
                        arrowViewLayoutParams.leftMargin = leftMargin;
                    }
                    mRoot.removeOnLayoutChangeListener(this);
                    resizeDialogSize(mParams.dialogParams, mArrowDirection, mArrowGravity
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

        int dialogX = arrowDirection == Gravity.TOP || arrowDirection == Gravity.BOTTOM
                ? view.getWidth() / 2 : view.getWidth();
        if (arrowGravity == Gravity.LEFT) {
            dialogX += location[0] - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == Gravity.RIGHT) {
            dialogX = screenWidth - location[0] - dialogX - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == Gravity.CENTER_HORIZONTAL) {
            dialogX = -1 * (screenWidth / 2 - location[0]) + dialogX;
        } else {
            dialogX += 0;
        }
        dialogParams.xOff = dialogX;

        int screenHeight = mScreenSize[1];
        int dialogY;
        if (arrowGravity == Gravity.TOP) {
            dialogY = location[1] - mStatusBarHeight + view.getHeight() / 2 - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == Gravity.BOTTOM) {
            dialogY = screenHeight - location[1] - view.getHeight() / 2 - arrowViewSize / 2 - arrowOffSet;
        } else if (arrowGravity == Gravity.CENTER_VERTICAL) {
            dialogY = -1 * (screenHeight / 2 - location[1]) - mStatusBarHeight / 2 + view.getHeight() / 2;
        } else {
            if (arrowDirection == Gravity.TOP
                    && (arrowGravity == Gravity.LEFT || arrowGravity == Gravity.RIGHT
                    || arrowGravity == Gravity.CENTER_HORIZONTAL)) {
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

