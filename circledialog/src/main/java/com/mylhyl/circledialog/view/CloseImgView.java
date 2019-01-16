package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.params.CloseParams;
import com.mylhyl.circledialog.view.listener.CloseView;

/**
 * Created by hupei on 2019/1/16 14:11.
 */
public class CloseImgView extends LinearLayout implements CloseView {
    private CloseParams mCloseParams;
    private ImageView mImageCloseView;

    public CloseImgView(Context context, CloseParams closeParams) {
        super(context);
        this.mCloseParams = closeParams;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        //关闭按钮
        mImageCloseView = new ImageView(getContext());
        LayoutParams layoutParamsClose = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        if (mCloseParams.closeSize != 0) {
            layoutParamsClose.width = layoutParamsClose.height = mCloseParams.closeSize;
        }
        if (mCloseParams.closeMargins != null && mCloseParams.closeMargins.length == 4)
            layoutParamsClose.setMargins(mCloseParams.closeMargins[0], mCloseParams.closeMargins[1]
                    , mCloseParams.closeMargins[2], mCloseParams.closeMargins[3]);
        if (mCloseParams.closeResId != 0) {
            mImageCloseView.setImageResource(mCloseParams.closeResId);
        }
        mImageCloseView.setAdjustViewBounds(true);
        addView(mImageCloseView, layoutParamsClose);
        //位置
//        if (mCloseParams.closeGravity == CloseParams.CLOSE_TOP_LEFT
//                || mCloseParams.closeGravity == CloseParams.CLOSE_BOTTOM_LEFT) {
//            layoutParamsClose.gravity = Gravity.LEFT;
//        } else if (mCloseParams.closeGravity == CloseParams.CLOSE_TOP_CENTER
//                || mCloseParams.closeGravity == CloseParams.CLOSE_BOTTOM_CENTER) {
//            layoutParamsClose.gravity = Gravity.CENTER_HORIZONTAL;
//        } else {
//            layoutParamsClose.gravity = Gravity.RIGHT;
//        }
    }

    @Override
    public void regOnCloseClickListener(OnClickListener listener) {
        mImageCloseView.setOnClickListener(listener);
    }

    @Override
    public View getView() {
        return this;
    }
}
