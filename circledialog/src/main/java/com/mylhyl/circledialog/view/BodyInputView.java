package com.mylhyl.circledialog.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.internal.BackgroundHelper;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.internal.EmojiFilter;
import com.mylhyl.circledialog.internal.MaxLengthByteWatcher;
import com.mylhyl.circledialog.internal.MaxLengthEnWatcher;
import com.mylhyl.circledialog.internal.MaxLengthWatcher;
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
 * <p>
 * 输入框
 * <ul>
 * 变更记录：
 * <li>fix: 2020/4/27 八阿哥 since 5.2.0 修复软键盘自动弹出的问题</li>
 * </ul>
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

    public BodyInputView(Context context, CircleParams circleParams) {
        super(context);
        init(circleParams);
    }


    @Override
    public EditText getInput() {
        return mEditText;
    }

    @Override
    public View getView() {
        return this;
    }

    private void init(CircleParams circleParams) {
        mDialogParams = circleParams.dialogParams;
        mTitleParams = circleParams.titleParams;
        mSubTitleParams = circleParams.subTitleParams;
        mInputParams = circleParams.inputParams;
        mOnInputCounterChangeListener = circleParams.circleListeners.inputCounterChangeListener;
        mOnCreateInputListener = circleParams.circleListeners.createInputListener;

        int rlPaddingTop = mTitleParams == null ?
                mSubTitleParams == null ? CircleDimen.TITLE_PADDING[1] : mSubTitleParams.padding[1] :
                mTitleParams.padding[1];
        setPadding(0, Controller.dp2px(getContext(), rlPaddingTop), 0, 0);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = mInputParams.backgroundColor != 0 ?
                mInputParams.backgroundColor : mDialogParams.backgroundColor;
        BackgroundHelper.handleBodyBackground(this, backgroundColor, circleParams);

        // fix: 2020/4/27 八阿哥 since 5.2.0 修复软键盘自动弹出的问题
        setFocusableInTouchMode(true);
        setFocusable(true);

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
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        int inputType = mInputParams.inputType;
        if (inputType != InputType.TYPE_NULL) {
            mEditText.setInputType(mInputParams.inputType);
        }
        if (mDialogParams.typeface != null) {
            mEditText.setTypeface(mDialogParams.typeface);
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
                            mEditText.setHeight(Controller.dp2px(getContext(), mInputParams.inputHeight));
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
            int strokeWidth = Controller.dp2px(getContext(), mInputParams.strokeWidth);
            InputDrawable inputDrawable = new InputDrawable(strokeWidth, mInputParams.strokeColor,
                    mInputParams.inputBackgroundColor);
            BackgroundHelper.handleBackground(mEditText, inputDrawable);
        } else {
            mEditText.setBackgroundResource(backgroundResourceId);
        }

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int[] margins = mInputParams.margins;
        if (margins != null) {
            layoutParams.setMargins(Controller.dp2px(getContext(), margins[0]),
                    Controller.dp2px(getContext(), margins[1]),
                    Controller.dp2px(getContext(), margins[2]),
                    Controller.dp2px(getContext(), margins[3]));
        }
        int[] padding = mInputParams.padding;
        if (padding != null) {
            mEditText.setPadding(Controller.dp2px(getContext(), padding[0]),
                    Controller.dp2px(getContext(), padding[1]),
                    Controller.dp2px(getContext(), padding[2]),
                    Controller.dp2px(getContext(), padding[3]));
        }
        mEditText.setTypeface(mEditText.getTypeface(), mInputParams.styleText);

        addView(mEditText, layoutParams);
    }

    private void createCounter() {
        if (mInputParams.maxLen <= 0) {
            return;
        }
        LayoutParams layoutParamsCounter = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //右下
        layoutParamsCounter.addRule(ALIGN_RIGHT, android.R.id.input);
        layoutParamsCounter.addRule(ALIGN_BOTTOM, android.R.id.input);
        if (mInputParams.counterMargins != null) {
            layoutParamsCounter.setMargins(0, 0,
                    Controller.dp2px(getContext(), mInputParams.counterMargins[0]),
                    Controller.dp2px(getContext(), mInputParams.counterMargins[1]));
        }
        mTvCounter = new TextView(getContext());
        if (mDialogParams.typeface != null) {
            mTvCounter.setTypeface(mDialogParams.typeface);
        }
        mTvCounter.setTextSize(INPUT_COUNTER__TEXT_SIZE);
        mTvCounter.setTextColor(mInputParams.counterColor);

        if (mInputParams.maxLengthType == 1) {
            mEditText.addTextChangedListener(new MaxLengthWatcher(mInputParams.maxLen, mEditText, mTvCounter,
                    mOnInputCounterChangeListener));
        } else if (mInputParams.maxLengthType == 2) {
            mEditText.addTextChangedListener(new MaxLengthEnWatcher(mInputParams.maxLen, mEditText, mTvCounter,
                    mOnInputCounterChangeListener));
        } else {
            mEditText.addTextChangedListener(new MaxLengthByteWatcher(mInputParams.maxLen, mEditText, mTvCounter,
                    mOnInputCounterChangeListener));
        }
        addView(mTvCounter, layoutParamsCounter);
    }

}
