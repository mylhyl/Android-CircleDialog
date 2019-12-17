package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.internal.Controller;
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

    @Override
    public void regOnCloseClickListener(OnClickListener listener) {
        mImageCloseView.setOnClickListener(listener);
    }

    @Override
    public View getView() {
        return this;
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        if (mCloseParams.closePadding != null && mCloseParams.closePadding.length == 4)
            setPadding(Controller.dp2px(getContext(), mCloseParams.closePadding[0]),
                    Controller.dp2px(getContext(), mCloseParams.closePadding[1]),
                    Controller.dp2px(getContext(), mCloseParams.closePadding[2]),
                    Controller.dp2px(getContext(), mCloseParams.closePadding[3]));
        //关闭按钮
        mImageCloseView = new ImageView(getContext());
        LayoutParams layoutParamsClose = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (mCloseParams.closeSize != 0) {
            layoutParamsClose.width = layoutParamsClose.height = Controller.dp2px(getContext(), mCloseParams.closeSize);
        }
        if (mCloseParams.closeResId != 0) {
            mImageCloseView.setImageResource(mCloseParams.closeResId);
        }
        mImageCloseView.setLayoutParams(layoutParamsClose);
        mImageCloseView.setAdjustViewBounds(true);

        if (mCloseParams.connectorWidth > 0) {
            DividerView dividerView = new DividerView(getContext());
            dividerView.setBgColor(mCloseParams.connectorColor);
            addView(dividerView, new LayoutParams(Controller.dp2px(getContext(), mCloseParams.connectorWidth),
                    Controller.dp2px(getContext(), mCloseParams.connectorHeight)));
        }
        //位置
        if (mCloseParams.closeGravity == CloseParams.CLOSE_TOP_LEFT
                || mCloseParams.closeGravity == CloseParams.CLOSE_TOP_CENTER
                || mCloseParams.closeGravity == CloseParams.CLOSE_TOP_RIGHT) {
            addView(mImageCloseView, 0);
        } else {
            addView(mImageCloseView);
        }
    }
}
