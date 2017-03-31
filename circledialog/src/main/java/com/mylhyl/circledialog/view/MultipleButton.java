package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
class MultipleButton extends ScaleLinearLayout {
    private ButtonParams mPositiveParams;
    private ScaleTextView mPositiveButton;

    public MultipleButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        setOrientation(HORIZONTAL);

        final ButtonParams negativeParams = params.getNegativeParams();
        mPositiveParams = params.getPositiveParams();

        int radius = params.getDialogParams().radius;

        //取消按钮
        ScaleTextView mNegativeButton = new ScaleTextView(getContext());
        mNegativeButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT, 1));
        mNegativeButton.setClickable(true);
        mNegativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                negativeParams.dismiss();
                if (negativeParams.listener != null)
                    negativeParams.listener.onClick(v);
            }
        });
        mNegativeButton.setText(negativeParams.text);
        mNegativeButton.setTextSize(negativeParams.textSize);
        mNegativeButton.setTextColor(negativeParams.textColor);
        mNegativeButton.setHeight(negativeParams.height);

        int backgroundNegative = negativeParams.backgroundColor != 0 ? negativeParams.backgroundColor : CircleColor
                .bgDialog;//如果取消按钮没有背景色，则使用默认色
        mNegativeButton.setBackground(new SelectorBtn(backgroundNegative, 0, 0, 0, radius));//按钮左下方为圆角
        addView(mNegativeButton);

        //添加二人按钮之间的分隔线
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);

        //确定按钮
        mPositiveButton = new ScaleTextView(getContext());
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT, 1));
        mPositiveButton.setClickable(true);

        mPositiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPositiveParams.dismiss();
                if (mPositiveParams.listener != null)
                    mPositiveParams.listener.onClick(v);
            }
        });

        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setTextColor(mPositiveParams.textColor);
        mPositiveButton.setHeight(mPositiveParams.height);

        int backgroundPositive = mPositiveParams.backgroundColor != 0 ? mPositiveParams.backgroundColor : CircleColor
                .bgDialog;//如果取消按钮没有背景色，则使用默认色
        mPositiveButton.setBackground(new SelectorBtn(backgroundPositive, 0, 0, radius, 0));//取消按钮右下方为圆角
        addView(mPositiveButton);
    }
}
