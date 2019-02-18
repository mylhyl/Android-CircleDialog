package com.mylhyl.circledialog.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.view.listener.OnCreateTextListener;

/**
 * 对话框纯文本视图
 * Created by hupei on 2017/3/30.
 */
final class BodyTextView extends AppCompatTextView {
    private DialogParams mDialogParams;
    private TextParams mTextParams;
    private OnCreateTextListener mOnCreateTextListener;

    public BodyTextView(Context context, DialogParams dialogParams, TextParams textParams
            , OnCreateTextListener onCreateTextListener) {
        super(context);
        mDialogParams = dialogParams;
        mTextParams = textParams;
        mOnCreateTextListener = onCreateTextListener;
        init();
    }

    private void init() {
        if (mTextParams == null) {
            mTextParams = new TextParams();
            mTextParams.height = 0;
            mTextParams.padding = null;
        }
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        setGravity(mTextParams.gravity);
        setMovementMethod(ScrollingMovementMethod.getInstance());
        //如果标题没有背景色，则使用默认色
        int backgroundColor = mTextParams.backgroundColor != 0
                ? mTextParams.backgroundColor : mDialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

        setMinHeight(mTextParams.height);
        setTextColor(mTextParams.textColor);
        setTextSize(mTextParams.textSize);
        setText(mTextParams.text);
        setTypeface(getTypeface(), mTextParams.styleText);

        int[] padding = mTextParams.padding;
        if (padding != null) setPadding(padding[0], padding[1], padding[2], padding[3]);

        if (mOnCreateTextListener != null) {
            mOnCreateTextListener.onCreateText(this);
        }
    }

    public void refreshText() {
        if (mTextParams == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                setText(mTextParams.text);
            }
        });
    }
}
