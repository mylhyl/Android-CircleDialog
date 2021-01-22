package com.mylhyl.circledialog.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylhyl.circledialog.AbsBaseCircleDialog;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * Created by hupei on 2020/11/11.
 * <p>
 * 登录框
 */
public class DialogLogin extends AbsBaseCircleDialog {

    public static DialogLogin getInstance() {
        DialogLogin dialogFragment = new DialogLogin();
        dialogFragment.setRadius(10);
        dialogFragment.setBackgroundColor(CircleColor.DIALOG_BACKGROUND);
        return dialogFragment;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.dialog_login, container, false);
    }

}
