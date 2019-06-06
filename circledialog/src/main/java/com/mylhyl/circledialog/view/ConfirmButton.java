package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;

import com.mylhyl.circledialog.BackgroundHelper;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
final class ConfirmButton extends AbsButton {

    public ConfirmButton(Context context, DialogParams dialogParams, ButtonParams negativeParams,
                         ButtonParams positiveParams, ButtonParams neutralParams,
                         OnCreateButtonListener createButtonListener) {
        super(context, dialogParams, negativeParams, positiveParams, neutralParams, createButtonListener);
    }

    @Override
    protected void initView() {
        setOrientation(HORIZONTAL);
    }

    @Override
    protected void setNegativeButtonBackground(View view, int backgroundColor) {
        BackgroundHelper.INSTANCE.handleNegativeButtonBackground(view, backgroundColor);
    }

    @Override
    protected void setNeutralButtonBackground(View view, int backgroundColor) {
        BackgroundHelper.INSTANCE.handleNeutralButtonBackground(view, backgroundColor);
    }

    @Override
    protected void setPositiveButtonBackground(View view, int backgroundColor) {
        BackgroundHelper.INSTANCE.handlePositiveButtonBackground(view, backgroundColor);
    }

}
