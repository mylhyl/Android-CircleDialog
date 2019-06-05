package com.mylhyl.circledialog.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;

/**
 * 对话框纯文本视图
 * Created by hupei on 2017/3/30.
 */
final class BodyTextView extends AppCompatTextView {
    private TextParams mTextParams;

    public BodyTextView(Context context, CircleParams circleParams) {
        super(context);
        init(circleParams);
    }

    public void refreshText() {
        if (mTextParams != null) {
            setText(mTextParams.text);
        }
    }

    private void init(CircleParams circleParams) {
        mTextParams = circleParams.textParams;
        DialogParams dialogParams = circleParams.dialogParams;

        if (mTextParams == null) {
            mTextParams = new TextParams();
            mTextParams.height = 0;
            mTextParams.padding = null;
        }
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        setGravity(mTextParams.gravity);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = mTextParams.backgroundColor != 0
                ? mTextParams.backgroundColor : dialogParams.backgroundColor;
        new BodyViewHelper(circleParams).handleBackground(this, backgroundColor);

        setMovementMethod(ScrollingMovementMethod.getInstance());

        setMinHeight(mTextParams.height);
        setTextColor(mTextParams.textColor);
        setTextSize(mTextParams.textSize);
        setText(mTextParams.text);
        setTypeface(getTypeface(), mTextParams.styleText);

        int[] padding = mTextParams.padding;
        if (padding != null) {
            setPadding(Controller.dp2px(getContext(), padding[0]), Controller.dp2px(getContext(), padding[1])
                    , Controller.dp2px(getContext(), padding[2]), Controller.dp2px(getContext(), padding[3]));
        }

        if (circleParams.createTextListener != null) {
            circleParams.createTextListener.onCreateText(this);
        }
    }


}
