package com.mylhyl.circledialog.view;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.mylhyl.circledialog.internal.BackgroundHelper;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
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

    public BodyTextView(Context context, CircleParams circleParams) {
        super(context);
        init(circleParams);
    }

    private void init(CircleParams circleParams) {

        mDialogParams = circleParams.dialogParams;
        mTextParams = circleParams.textParams;
        mOnCreateTextListener = circleParams.circleListeners.createTextListener;

        if (mTextParams == null) {
            mTextParams = new TextParams();
            mTextParams.height = 0;
            mTextParams.padding = null;
        }
        setId(android.R.id.text1);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        if (mDialogParams.typeface != null) {
            setTypeface(mDialogParams.typeface);
        }
        setGravity(mTextParams.gravity);

        // 如果标题没有背景色，则使用默认色
        int backgroundColor = mTextParams.backgroundColor != 0 ? mTextParams.backgroundColor :
                mDialogParams.backgroundColor;
        BackgroundHelper.handleBodyBackground(this, backgroundColor, circleParams);

        setMovementMethod(ScrollingMovementMethod.getInstance());

        setMinHeight(mTextParams.height);
        setTextColor(mTextParams.textColor);
        setTextSize(mTextParams.textSize);
        setText(mTextParams.text);
        setTypeface(getTypeface(), mTextParams.styleText);

        int[] padding = mTextParams.padding;
        if (padding != null) {
            setPadding(Controller.dp2px(getContext(), padding[0]),
                    Controller.dp2px(getContext(), padding[1]),
                    Controller.dp2px(getContext(), padding[2]),
                    Controller.dp2px(getContext(), padding[3]));
        }

        if (mOnCreateTextListener != null) {
            mOnCreateTextListener.onCreateText(this);
        }
    }

    public void refreshText() {
        if (mTextParams != null) {
            setText(mTextParams.text);
        }
    }

}
