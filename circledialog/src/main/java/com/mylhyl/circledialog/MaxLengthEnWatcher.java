package com.mylhyl.circledialog;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;

/**
 * 中文1字符
 * Created by hupei on 2019/01/07 09:36.
 */
public class MaxLengthEnWatcher implements TextWatcher {
    private int mMaxLen;
    private EditText mEditText;
    private TextView mTvCounter;
    private OnInputCounterChangeListener mOnInputCounterChangeListener;

    public MaxLengthEnWatcher(int maxLen, EditText editText, TextView textView
            , OnInputCounterChangeListener listener) {
        this.mMaxLen = maxLen;
        this.mEditText = editText;
        this.mTvCounter = textView;
        this.mOnInputCounterChangeListener = listener;
        if (mEditText != null) {
            String defText = mEditText.getText().toString();
            int currentLen = maxLen - defText.length();
            if (mOnInputCounterChangeListener != null) {
                String counterText = mOnInputCounterChangeListener.onCounterChange(maxLen
                        , currentLen);
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
            while (editable.toString().length() > mMaxLen) {
                editable.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
        }
        int currentLen = mMaxLen - editable.toString().length();
        if (mOnInputCounterChangeListener != null) {
            String counterText = mOnInputCounterChangeListener.onCounterChange(mMaxLen, currentLen);
            mTvCounter.setText(counterText == null ? "" : counterText);
        } else {
            mTvCounter.setText(String.valueOf(currentLen));
        }

        mEditText.setSelection(editStart);
        // 恢复监听器
        mEditText.addTextChangedListener(this);
    }
}
