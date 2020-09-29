package com.mylhyl.circledialog.internal;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;

/**
 * Created by hupei on 2020/9/29 19:11.
 * <p>
 * 字节
 */
public class MaxLengthByteWatcher implements TextWatcher {
    private int mMaxLen;
    private EditText mEditText;
    private TextView mTvCounter;
    private OnInputCounterChangeListener mOnInputCounterChangeListener;

    public MaxLengthByteWatcher(int maxLen, EditText editText, TextView textView,
                                OnInputCounterChangeListener listener) {
        this.mMaxLen = maxLen;
        this.mEditText = editText;
        this.mTvCounter = textView;
        this.mOnInputCounterChangeListener = listener;
        if (mEditText == null) {
            return;
        }
        String defText = mEditText.getText().toString();
        int currentLen = maxLen - chineseLength(defText);
        if (mOnInputCounterChangeListener != null) {
            String counterText = mOnInputCounterChangeListener.onCounterChange(maxLen, currentLen);
            mTvCounter.setText(counterText == null ? "" : counterText);
        } else {
            mTvCounter.setText(String.valueOf(currentLen));
        }
    }

    private int chineseLength(String str) {
        int valueLength = 0;
        if (TextUtils.isEmpty(str)) {
            return valueLength;
        }
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 字节长度
            valueLength += temp.getBytes().length;
        }
        return valueLength;
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
