package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.view.listener.OnCreateTextListener;

/**
 * 对话框纯文本视图
 * Created by hupei on 2017/3/30.
 */
final class BodyTextView extends android.support.v7.widget.AppCompatTextView {
    private CircleParams mParams;

    public BodyTextView(Context context, CircleParams params) {
        super(context);
        this.mParams = params;
        init(params);
    }

    private void init(CircleParams params) {
        DialogParams dialogParams = params.dialogParams;
        TextParams textParams = params.textParams;

        setGravity(textParams.gravity);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = textParams.backgroundColor != 0
                ? textParams.backgroundColor : dialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

        setMinHeight(textParams.height);
        setTextColor(textParams.textColor);
        setTextSize(textParams.textSize);
        setText(textParams.text);
        setTypeface(getTypeface(), textParams.styleText);

        int[] padding = textParams.padding;
        if (padding != null) setPadding(padding[0], padding[1], padding[2], padding[3]);

        OnCreateTextListener createTextListener = params.createTextListener;
        if (createTextListener != null) {
            createTextListener.onCreateText(this);
        }
    }

    public void refreshText() {
        if (mParams.textParams == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                setText(mParams.textParams.text);
            }
        });
    }
}
