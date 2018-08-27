package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.res.drawable.InputDrawable;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;

import static com.mylhyl.circledialog.Controller.BUTTON_POSITIVE;
import static com.mylhyl.circledialog.res.values.CircleDimen.INPUT_COUNTER__TEXT_SIZE;

/**
 * Created by hupei on 2017/3/31.
 */

final class BodyInputView extends RelativeLayout implements Controller.OnClickListener, InputView {
    private ScaleEditText mEditText;
    private ScaleTextView mTvCounter;
    private CircleParams params;
    private Controller.OnDialogInternalListener mDialogInternalListener;

    public BodyInputView(Context context, Controller.OnDialogInternalListener dialogInternalListener
            , CircleParams params) {
        super(context);
        this.mDialogInternalListener = dialogInternalListener;
        init(context, params);
    }

    private void init(Context context, CircleParams params) {
        this.params = params;
        DialogParams dialogParams = params.dialogParams;
        final InputParams inputParams = params.inputParams;

        //如果标题没有背景色，则使用默认色
        int backgroundColor = inputParams.backgroundColor != 0
                ? inputParams.backgroundColor : dialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

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

            mEditText.addTextChangedListener(new MaxLengthWatcher(inputParams.maxLen
                    , mEditText, mTvCounter, params));

            addView(mTvCounter, layoutParamsCounter);
        }

        if (inputParams.isEmojiInput) {
            mEditText.addTextChangedListener(new EmojiWatcher(mEditText));
        }

        OnCreateInputListener createInputListener = params.createInputListener;
        if (createInputListener != null) {
            createInputListener.onCreateText(this, mEditText, mTvCounter);
        }
    }

    static int chineseLength(String str) {
        int valueLength = 0;
        if (!TextUtils.isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (isChinese(temp)) {
                    // 中文字符长度为2
                    valueLength += 2;
                } else {
                    // 其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    static boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!TextUtils.isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    @Override
    public void onClick(View view, int which) {
        if (view instanceof InputView && which == BUTTON_POSITIVE) {
            InputView inputView = (InputView) view;
            String text = inputView.getInput().getText().toString();
            if (params.inputListener != null) {
                boolean b = params.inputListener.onClick(text, inputView.getInput());
                if (b) {
                    mDialogInternalListener.onDismiss();
                }
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

    static class MaxLengthWatcher implements TextWatcher {
        private int mMaxLen;
        private EditText mEditText;
        private TextView mTvCounter;
        private CircleParams mParams;

        public MaxLengthWatcher(int maxLen, EditText editText, TextView textView, CircleParams params) {
            this.mMaxLen = maxLen;
            this.mEditText = editText;
            this.mTvCounter = textView;
            this.mParams = params;
            if (mEditText != null) {
                String defText = mEditText.getText().toString();
                int currentLen = maxLen - chineseLength(defText);
                if (mParams.inputCounterChangeListener != null) {
                    String counterText = mParams.inputCounterChangeListener
                            .onCounterChange(maxLen, currentLen);
                    mTvCounter.setText(counterText == null ? "" : counterText);
                } else {
                    mTvCounter.setText(String.valueOf(currentLen));
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int editStart = mEditText.getSelectionStart();
            int editEnd = mEditText.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            mEditText.removeTextChangedListener(this);
            if (!TextUtils.isEmpty(editable)) {
                //循环删除多出的字符
                while (chineseLength(editable.toString()) > mMaxLen) {
                    editable.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }
            int currentLen = mMaxLen - chineseLength(editable.toString());
            if (mParams.inputCounterChangeListener != null) {
                String counterText = mParams.inputCounterChangeListener
                        .onCounterChange(mMaxLen, currentLen);
                mTvCounter.setText(counterText == null ? "" : counterText);
            } else {
                mTvCounter.setText(String.valueOf(currentLen));
            }

            mEditText.setSelection(editStart);
            // 恢复监听器
            mEditText.addTextChangedListener(this);
        }
    }

    static class EmojiWatcher implements TextWatcher {
        //监听改变的文本框
        private EditText mEditText;

        public EmojiWatcher(EditText editText) {
            this.mEditText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int index = mEditText.getSelectionStart() - 1;
            mEditText.removeTextChangedListener(this);
            if (index > 0) {
                if (isEmojiCharacter(mEditText.getText().charAt(index))) {
                    Editable edit = mEditText.getText();
                    edit.delete(start, start + count);
                }
            }
            mEditText.addTextChangedListener(this);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
