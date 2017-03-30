package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.auto.AutoUtils;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * 对话框单个按钮的视图
 * Created by hupei on 2017/3/30.
 */
class SingleButton extends AutoTextView {

    public SingleButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        ButtonParams negativeParams = params.getNegativeParams();
        final ButtonParams buttonParams = negativeParams != null ? negativeParams : params.getPositiveParams();

        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonParams.dismiss();
                if (buttonParams.listener != null)
                    buttonParams.listener.onClick(v);
            }
        });
        setText(buttonParams.text);
        setTextSize(buttonParams.textSize);
        setTextColor(buttonParams.textColor);
        setHeight(buttonParams.height);

        //如果取消按钮没有背景色，则使用默认色
        int backgroundColor = buttonParams.backgroundColor != 0 ? buttonParams.backgroundColor : CircleColor
                .bgDialog;
        int radius = params.getDialogParams().radius;
        setBackground(new SelectorBtn(backgroundColor, 0, 0, radius, radius));
    }
}
