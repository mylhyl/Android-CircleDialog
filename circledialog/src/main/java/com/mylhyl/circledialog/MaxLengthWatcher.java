package com.mylhyl.circledialog;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hupei on 2018/11/1 14:16.
 */
public class MaxLengthWatcher implements TextWatcher {
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
            if (params.inputCounterChangeListener != null) {
                String counterText = params.inputCounterChangeListener
                        .onCounterChange(maxLen, currentLen);
                mTvCounter.setText(counterText == null ? "" : counterText);
            } else {
                mTvCounter.setText(String.valueOf(currentLen));
            }
        }
    }

    public static int chineseLength(String str) {
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

    public static boolean isChinese(String str) {
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
