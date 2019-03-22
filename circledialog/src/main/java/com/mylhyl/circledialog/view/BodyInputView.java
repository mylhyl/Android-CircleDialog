package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.EmojiFilter;
import com.mylhyl.circledialog.MaxLengthEnWatcher;
import com.mylhyl.circledialog.MaxLengthWatcher;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.drawable.InputDrawable;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;

import static com.mylhyl.circledialog.Controller.BUTTON_POSITIVE;
import static com.mylhyl.circledialog.res.values.CircleDimen.INPUT_COUNTER__TEXT_SIZE;

/**
 * Created by hupei on 2017/3/31.
 */

final class BodyInputView extends ScaleRelativeLayout implements Controller.OnClickListener, InputView {
    private ScaleEditText mEditText;
    private ScaleTextView mTvCounter;
    private CircleParams params;

    public BodyInputView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        this.params = params;
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        final InputParams inputParams = params.inputParams;
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
        mEditText.setId(android.R.id.input);
        int inputType = inputParams.inputType;
        if (inputType != InputType.TYPE_NULL) {
            mEditText.setInputType(inputParams.inputType);
        }
        mEditText.setHint(inputParams.hintText);
        mEditText.setHintTextColor(inputParams.hintTextColor);
        mEditText.setTextSize(inputParams.textSize);
        mEditText.setTextColor(inputParams.textColor);
        mEditText.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int height = mEditText.getMeasuredHeight();
                        if (inputParams.inputHeight > height) {
                            mEditText.setHeight(inputParams.inputHeight);
                        }
                    }
                });
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

        if (inputParams.maxLen > 0) {
            LayoutParams layoutParamsCounter = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            //右下
            layoutParamsCounter.addRule(ALIGN_RIGHT, android.R.id.input);
            layoutParamsCounter.addRule(ALIGN_BOTTOM, android.R.id.input);
            if (inputParams.counterMargins != null) {
                layoutParamsCounter.setMargins(0, 0
                        , inputParams.counterMargins[0]
                        , inputParams.counterMargins[1]);
            }
            mTvCounter = new ScaleTextView(context);
            mTvCounter.setTextSize(INPUT_COUNTER__TEXT_SIZE);
            mTvCounter.setTextColor(inputParams.counterColor);

            if (inputParams.isCounterAllEn) {
                mEditText.addTextChangedListener(new MaxLengthEnWatcher(inputParams.maxLen
                        , mEditText, mTvCounter, params));
            } else {
                mEditText.addTextChangedListener(new MaxLengthWatcher(inputParams.maxLen
                        , mEditText, mTvCounter, params));
            }
            addView(mTvCounter, layoutParamsCounter);
        }

        if (inputParams.isEmojiInput) {
            mEditText.setFilters(new InputFilter[]{new EmojiFilter()});
        }

        OnCreateInputListener createInputListener = params.createInputListener;
        if (createInputListener != null) {
            createInputListener.onCreateText(this, mEditText, mTvCounter);
        }
    }


    @Override
    public void onClick(View view, int which) {
        if (view instanceof InputView && which == BUTTON_POSITIVE) {
            InputView inputView = (InputView) view;
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
