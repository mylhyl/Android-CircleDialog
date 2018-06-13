package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.drawable.InputDrawable;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;

import static com.mylhyl.circledialog.Controller.BUTTON_POSITIVE;

/**
 * Created by hupei on 2017/3/31.
 */

final class BodyInputView extends ScaleLinearLayout implements Controller.OnClickListener, InputView {
    private ScaleEditText mEditText;
    private CircleParams params;

    public BodyInputView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        this.params = params;
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        InputParams inputParams = params.inputParams;
        ButtonParams negativeParams = params.negativeParams;
        ButtonParams positiveParams = params.positiveParams;

        //如果标题没有背景色，则使用默认色
        int backgroundColor = inputParams.backgroundColor != 0
                ? inputParams.backgroundColor : dialogParams.backgroundColor;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, 0, 0, dialogParams
                        .radius, dialogParams.radius));
            }
        }
        //没标题有按钮则顶部圆角
        else if (titleParams == null && (negativeParams != null || positiveParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams
                        .radius, 0, 0));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius,
                        dialogParams.radius, 0, 0));
            }
        }
        //没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius));
            }
        }
        //有标题有按钮则不用考虑圆角
        else setBackgroundColor(backgroundColor);

        mEditText = new ScaleEditText(context);
        int inputType = inputParams.inputType;
        if (inputType != InputType.TYPE_NULL) {
            mEditText.setInputType(inputParams.inputType);
        }
        mEditText.setHint(inputParams.hintText);
        mEditText.setHintTextColor(inputParams.hintTextColor);
        mEditText.setTextSize(inputParams.textSize);
        mEditText.setTextColor(inputParams.textColor);
        mEditText.setHeight(inputParams.inputHeight);
        mEditText.setGravity(inputParams.gravity);
        if (!TextUtils.isEmpty(inputParams.text)) {
            mEditText.setText(inputParams.text);
            mEditText.setSelection(inputParams.text.length());
        }

        int backgroundResourceId = inputParams.inputBackgroundResourceId;
        if (backgroundResourceId == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mEditText.setBackground(new InputDrawable(inputParams.strokeWidth, inputParams
                        .strokeColor, inputParams.inputBackgroundColor));
            } else {
                mEditText.setBackgroundDrawable(new InputDrawable(inputParams.strokeWidth,
                        inputParams.strokeColor, inputParams.inputBackgroundColor));
            }
        } else mEditText.setBackgroundResource(backgroundResourceId);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        int[] margins = inputParams.margins;
        if (margins != null) {
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        }
        int[] padding = inputParams.padding;
        if (padding != null)
            mEditText.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);
        mEditText.setTypeface(mEditText.getTypeface(), inputParams.styleText);
        addView(mEditText, layoutParams);

        OnCreateInputListener createInputListener = params.createInputListener;
        if (createInputListener != null) {
            createInputListener.onCreateText(mEditText);
        }
    }

    @Override
    public void onClick(View view, int which) {
        if (view instanceof BodyInputView && which == BUTTON_POSITIVE) {
            BodyInputView inputView = (BodyInputView) view;
            String text = inputView.getInput().getText().toString();
            if (params.inputListener != null) {
                params.inputListener.onClick(text, inputView.getInput());

            }
        }

    }

    @Override
    public EditText getInput() {
        return mEditText;
    }

    @Override
    public View getView() {
        return this;
    }
}
