package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;

import com.mylhyl.circledialog.internal.BackgroundHelper;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;

/**
 * 列表对话框的取消按钮视图
 * Created by hupei on 2017/3/30.
 */
final class ItemsButton extends AbsButton {

    public ItemsButton(Context context, CircleParams circleParams) {
        super(context, circleParams);
    }

    @Override
    protected void initView() {
        setOrientation(HORIZONTAL);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //设置列表与按钮之间的上距离
        int topMargin = mNegativeParams == null ? mNeutralParams == null ? mPositiveParams == null ?
                0 : mPositiveParams.topMargin : mNeutralParams.topMargin : mNegativeParams.topMargin;
        if (topMargin > 0) {
            layoutParams.topMargin = Controller.dp2px(getContext(), topMargin);
        }
        setLayoutParams(layoutParams);
    }

    @Override
    protected void setNegativeButtonBackground(View view, int backgroundColor) {
        BackgroundHelper.INSTANCE.handleItemsNegativeButtonBackground(view, backgroundColor);
    }

    @Override
    protected void setNeutralButtonBackground(View view, int backgroundColor) {
        BackgroundHelper.INSTANCE.handleItemsNeutralButtonBackground(view, backgroundColor);
    }

    @Override
    protected void setPositiveButtonBackground(View view, int backgroundColor) {
        BackgroundHelper.INSTANCE.handleItemsPositiveButtonBackground(view, backgroundColor);
    }

}
