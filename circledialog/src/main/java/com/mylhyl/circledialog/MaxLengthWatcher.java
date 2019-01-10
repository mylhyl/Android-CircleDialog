package com.mylhyl.circledialog;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;

/**
 * 中文2字符
 * Created by hupei on 2018/11/1 11:19.
 */
public class MaxLengthWatcher implements TextWatcher {
    private int mMaxLen;
    private EditText mEditText;
    private TextView mTvCounter;
    private OnInputCounterChangeListener mOnInputCounterChangeListener;

    public MaxLengthWatcher(int maxLen, EditText editText, TextView textView
            , OnInputCounterChangeListener listener) {
        this.mMaxLen = maxLen;
        this.mEditText = editText;
        this.mTvCounter = textView;
        this.mOnInputCounterChangeListener = listener;
        if (mEditText != null) {
            String defText = mEditText.getText().toString();
            int currentLen = maxLen - chineseLength(defText);
            if (mOnInputCounterChangeListener != null) {
                String counterText = mOnInputCounterChangeListener.onCounterChange(maxLen
                        , currentLen);
                mTvCounter.setText(counterText == null ? "" : counterText);
            } else {
                mTvCounter.setText(String.valueOf(currentLen));
            }
        }
    }

    private int chineseLength(String str) {
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

    private boolean isChinese(String str) {
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
