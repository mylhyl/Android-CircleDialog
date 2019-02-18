package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.EmojiFilter;
import com.mylhyl.circledialog.MaxLengthEnWatcher;
import com.mylhyl.circledialog.MaxLengthWatcher;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.InputDrawable;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;
import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;

import static com.mylhyl.circledialog.res.values.CircleDimen.INPUT_COUNTER__TEXT_SIZE;

/**
 * Created by hupei on 2017/3/31.
 */

final class BodyInputView extends RelativeLayout implements InputView {
    private DialogParams mDialogParams;
    private TitleParams mTitleParams;
    private SubTitleParams mSubTitleParams;
    private InputParams mInputParams;
    private OnInputCounterChangeListener mOnInputCounterChangeListener;
    private OnCreateInputListener mOnCreateInputListener;
    private EditText mEditText;
    private TextView mTvCounter;

    public BodyInputView(Context context, DialogParams dialogParams, TitleParams titleParams
            , SubTitleParams subTitleParams, InputParams inputParams
            , OnInputCounterChangeListener onInputCounterChangeListener
            , OnCreateInputListener onCreateInputListener) {
        super(context);
        mDialogParams = dialogParams;
        mTitleParams = titleParams;
        mSubTitleParams = subTitleParams;
        mInputParams = inputParams;
        mOnInputCounterChangeListener = onInputCounterChangeListener;
        mOnCreateInputListener = onCreateInputListener;
        init();
    }

    private void init() {
        int rlPaddingTop = mTitleParams == null ? mSubTitleParams == null
                ? CircleDimen.TITLE_PADDING[1] : mSubTitleParams.padding[1]
                : mTitleParams.padding[1];
        setPadding(0, rlPaddingTop, 0, 0);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = mInputParams.backgroundColor != 0
                ? mInputParams.backgroundColor : mDialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

        createInput();

        createCounter();

        if (mInputParams.isEmojiInput) {
            mEditText.setFilters(new InputFilter[]{new EmojiFilter()});
        }

        if (mOnCreateInputListener != null) {
            mOnCreateInputListener.onCreateText(this, mEditText, mTvCounter);
        }
    }

    private void createInput() {
        mEditText = new EditText(getContext());
        mEditText.setId(android.R.id.input);
        int inputType = mInputParams.inputType;
        if (inputType != InputType.TYPE_NULL) {
            mEditText.setInputType(mInputParams.inputType);
        }
        mEditText.setHint(mInputParams.hintText);
        mEditText.setHintTextColor(mInputParams.hintTextColor);
        mEditText.setTextSize(mInputParams.textSize);
        mEditText.setTextColor(mInputParams.textColor);

        mEditText.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mEditText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int height = mEditText.getMeasuredHeight();
                        if (mInputParams.inputHeight > height) {
                            mEditText.setHeight(mInputParams.inputHeight);
                        }
                    }
                });

        mEditText.setGravity(mInputParams.gravity);
        if (!TextUtils.isEmpty(mInputParams.text)) {
            mEditText.setText(mInputParams.text);
            mEditText.setSelection(mInputParams.text.length());
        }

        int backgroundResourceId = mInputParams.inputBackgroundResourceId;
        if (backgroundResourceId == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mEditText.setBackground(new InputDrawable(mInputParams.strokeWidth, mInputParams
                        .strokeColor, mInputParams.inputBackgroundColor));
            } else {
                mEditText.setBackgroundDrawable(new InputDrawable(mInputParams.strokeWidth,
                        mInputParams.strokeColor, mInputParams.inputBackgroundColor));
            }
        } else mEditText.setBackgroundResource(backgroundResourceId);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        int[] margins = mInputParams.margins;
        if (margins != null) {
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        }
        int[] padding = mInputParams.padding;
        if (padding != null)
            mEditText.setPadding(padding[0], padding[1], padding[2], padding[3]);
        mEditText.setTypeface(mEditText.getTypeface(), mInputParams.styleText);

        addView(mEditText, layoutParams);
    }

    private void createCounter() {
        if (mInputParams.maxLen > 0) {
            LayoutParams layoutParamsCounter = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            //右下
            layoutParamsCounter.addRule(ALIGN_RIGHT, android.R.id.input);
            layoutParamsCounter.addRule(ALIGN_BOTTOM, android.R.id.input);
            if (mInputParams.counterMargins != null) {
                layoutParamsCounter.setMargins(0, 0
                        , mInputParams.counterMargins[0]
                        , mInputParams.counterMargins[1]);
            }
            mTvCounter = new TextView(getContext());
            mTvCounter.setTextSize(INPUT_COUNTER__TEXT_SIZE);
            mTvCounter.setTextColor(mInputParams.counterColor);

            if (mInputParams.isCounterAllEn) {
                mEditText.addTextChangedListener(new MaxLengthEnWatcher(mInputParams.maxLen
                        , mEditText, mTvCounter, mOnInputCounterChangeListener));
            } else {
                mEditText.addTextChangedListener(new MaxLengthWatcher(mInputParams.maxLen
                        , mEditText, mTvCounter, mOnInputCounterChangeListener));
            }
            addView(mTvCounter, layoutParamsCounter);
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
