package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
class MultipleButton extends ScaleLinearLayout {
    private ButtonParams mNegativeParams;
    private ButtonParams mPositiveParams;
    private ScaleTextView mNegativeButton;
    private ScaleTextView mPositiveButton;

    public MultipleButton(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {
        setOrientation(HORIZONTAL);

        mNegativeParams = params.negativeParams;
        mPositiveParams = params.positiveParams;

        int radius = params.dialogParams.radius;

        //取消按钮
        createNegative(radius);

        //添加二人按钮之间的分隔线
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);

        //确定按钮
        createPositive(radius);
    }

    private void createNegative(int radius) {
        mNegativeButton = new ScaleTextView(getContext());
        mNegativeButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setTextColor(mNegativeParams.textColor);
        mNegativeButton.setHeight(mNegativeParams.height);

        //如果取消按钮没有背景色，则使用默认色
        int backgroundNegative = mNegativeParams.backgroundColor != 0 ? mNegativeParams
                .backgroundColor : CircleColor.bgDialog;
        //按钮左下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNegativeButton.setBackground(new SelectorBtn(backgroundNegative, 0, 0, 0, radius));
        } else {
            mNegativeButton.setBackgroundDrawable(new SelectorBtn(backgroundNegative, 0, 0, 0,
                    radius));
        }

        regNegativeListener();

        addView(mNegativeButton);
    }

    private void createPositive(int radius) {

        mPositiveButton = new ScaleTextView(getContext());
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup
                        .LayoutParams.WRAP_CONTENT, 1));

        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setTextColor(mPositiveParams.textColor);
        mPositiveButton.setHeight(mPositiveParams.height);

        //如果取消按钮没有背景色，则使用默认色
        int backgroundPositive = mPositiveParams.backgroundColor != 0 ? mPositiveParams
                .backgroundColor : CircleColor
                .bgDialog;

        //取消按钮右下方为圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mPositiveButton.setBackground(new SelectorBtn(backgroundPositive, 0, 0, radius, 0));
        } else {
            mPositiveButton.setBackgroundDrawable(new SelectorBtn(backgroundPositive, 0, 0,
                    radius, 0));
        }

        regPositiveListener();

        addView(mPositiveButton);
    }

    private void regNegativeListener() {
        mNegativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mNegativeParams.dismiss();
                if (mNegativeParams.listener != null) mNegativeParams.listener.onClick(v);
            }
        });
    }

    private void regPositiveListener() {
        mPositiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPositiveParams.dismiss();
                if (mPositiveParams.listener != null) mPositiveParams.listener.onClick(v);
            }
        });
    }

    public void regOnInputClickListener(final EditText input) {
        mPositiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (!TextUtils.isEmpty(text))
                    mPositiveParams.dismiss();
                if (mPositiveParams.inputListener != null)
                    mPositiveParams.inputListener.onClick(text, v);
            }
        });
    }
}
