package com.mylhyl.circledialog.view;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.drawable.InputDrawable;
import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * Created by hupei on 2017/3/31.
 */

class BodyInputView extends ScaleLinearLayout {
    private ScaleEditText mEditText;

    public BodyInputView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        DialogParams dialogParams = params.getDialogParams();
        TitleParams titleParams = params.getTitleParams();
        InputParams inputParams = params.getInputParams();
        ButtonParams negativeParams = params.getNegativeParams();
        ButtonParams positiveParams = params.getPositiveParams();

        //如果标题没有背景色，则使用默认色
        int backgroundColor = inputParams.backgroundColor != 0 ? inputParams.backgroundColor : CircleColor.bgDialog;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null)
            setBackground(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius, dialogParams.radius));
            //没标题有按钮则顶部圆角
        else if (titleParams == null && (negativeParams != null || positiveParams != null))
            setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams.radius, 0, 0));
            //没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null)
            setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
            //有标题有按钮则不用考虑圆角
        else setBackgroundColor(backgroundColor);

        mEditText = new ScaleEditText(context);
        mEditText.setHint(inputParams.hintText);
        mEditText.setHintTextColor(inputParams.hintTextColor);
        mEditText.setTextSize(inputParams.textSize);
        mEditText.setTextColor(inputParams.textColor);
        mEditText.setHeight(inputParams.inputHeight);

        int backgroundResourceId = inputParams.inputBackgroundResourceId;
        if (backgroundResourceId == 0)
            mEditText.setBackground(new InputDrawable(inputParams.strokeWidth, inputParams.strokeColor, inputParams
                    .inputBackgroundColor));
        else mEditText.setBackgroundResource(backgroundResourceId);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int[] margins = inputParams.margins;
        if (margins != null) layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);

        addView(mEditText, layoutParams);
    }

    public EditText getInput() {
        return mEditText;
    }
}
