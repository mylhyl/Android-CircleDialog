package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;

import com.mylhyl.circledialog.internal.BackgroundHelper;
import com.mylhyl.circledialog.internal.CircleParams;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
final class ConfirmButton extends AbsButton {

    public ConfirmButton(Context context, CircleParams circleParams) {
        super(context, circleParams);
    }

    @Override
    protected void initView() {
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void setNegativeButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        BackgroundHelper.handleNegativeButtonBackground(view, backgroundColor, circleParams);
    }

    @Override
    protected void setNeutralButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        BackgroundHelper.handleNeutralButtonBackground(view, backgroundColor, circleParams);
    }

    @Override
    protected void setPositiveButtonBackground(View view, int backgroundColor, CircleParams circleParams) {
        BackgroundHelper.handlePositiveButtonBackground(view, backgroundColor, circleParams);
    }

}
