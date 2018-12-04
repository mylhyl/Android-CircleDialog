package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.EmojiFilter;
import com.mylhyl.circledialog.MaxLengthWatcher;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.res.drawable.InputDrawable;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;

import static com.mylhyl.circledialog.res.values.CircleDimen.INPUT_COUNTER__TEXT_SIZE;

/**
 * Created by hupei on 2017/3/31.
 */

final class BodyInputView extends RelativeLayout implements InputView {
    private EditText mEditText;
    private TextView mTvCounter;

    public BodyInputView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        int rlPaddingTop = params.titleParams == null ? params.subTitleParams == null
                ? CircleDimen.TITLE_PADDING[1] : params.subTitleParams.padding[1]
                : params.titleParams.padding[1];
        setPadding(0, rlPaddingTop, 0, 0);

        DialogParams dialogParams = params.dialogParams;
        final InputParams inputParams = params.inputParams;

        //如果标题没有背景色，则使用默认色
        int backgroundColor = inputParams.backgroundColor != 0
                ? inputParams.backgroundColor : dialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

        mEditText = new EditText(context);
        mEditText.setId(android.R.id.input);
        int inputType = inputParams.inputType;
        if (inputType != InputType.TYPE_NULL) {
            mEditText.setInputType(inputParams.inputType);
        }
        mEditText.setHint(inputParams.hintText);
        mEditText.setHintTextColor(inputParams.hintTextColor);
        mEditText.setTextSize(inputParams.textSize);
        mEditText.setTextColor(inputParams.textColor);
        mEditText.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int height = v.getHeight();
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
            mEditText.setPadding(padding[0], padding[1], padding[2], padding[3]);
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
            mTvCounter = new TextView(context);
            mTvCounter.setTextSize(INPUT_COUNTER__TEXT_SIZE);
            mTvCounter.setTextColor(inputParams.counterColor);

            mEditText.addTextChangedListener(new MaxLengthWatcher(inputParams.maxLen
                    , mEditText, mTvCounter, params));

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
    public EditText getInput() {
        return mEditText;
    }

    @Override
    public View getView() {
        return this;
    }

}
