package com.mylhyl.circledialog;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

/**
 * Created by hupei on 2017/3/29.
 */

public final class CircleDialog {
    private AbsCircleDialog mDialog;

    private CircleDialog() {
    }

    public DialogFragment create(CircleParams params) {
        if (mDialog == null)
            mDialog = AbsCircleDialog.newAbsCircleDialog(params);
        else {
            if (mDialog != null && mDialog.getDialog() != null && mDialog.getDialog().isShowing()) {
                mDialog.refreshView();
            }
        }
        return mDialog;
    }

    public void show(FragmentActivity activity) {
        mDialog.show(activity.getSupportFragmentManager(), "circleDialog");
    }

    public static class Builder {
        private FragmentActivity mActivity;
        private CircleDialog mCircleDialog;
        private CircleParams mCircleParams;


        public Builder(@NonNull FragmentActivity activity) {
            this.mActivity = activity;
            mCircleParams = new CircleParams();
            mCircleParams.setDialogParams(new DialogParams());
        }

        /**
         * 设置对话框位置
         *
         * @param gravity 位置
         * @return builder
         */
        public Builder setGravity(int gravity) {
            mCircleParams.getDialogParams().gravity = gravity;
            return this;
        }

        /**
         * 设置对话框点击外部关闭
         *
         * @param cancel true允许
         * @return Builder
         */
        public Builder setCanceledOnTouchOutside(boolean cancel) {
            mCircleParams.getDialogParams().canceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * 设置对话框返回键关闭
         *
         * @param cancel true允许
         * @return Builder
         */
        public Builder setCancelable(boolean cancel) {
            mCircleParams.getDialogParams().cancelable = cancel;
            return this;
        }

        /**
         * 设置对话框宽度
         *
         * @param width 0.0 - 1.0
         * @return Builder
         */
        public Builder setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
            mCircleParams.getDialogParams().width = width;
            return this;
        }

        /**
         * 设置对话框圆角
         *
         * @param radius 半径
         * @return Builder
         */
        public Builder setRadius(int radius) {
            mCircleParams.getDialogParams().radius = radius;
            return this;
        }

        public Builder configDialog(@NonNull ConfigDialog configDialog) {
            configDialog.onConfig(mCircleParams.getDialogParams());
            return this;
        }

        public Builder setTitle(@NonNull String text) {
            newTitleParams();
            mCircleParams.getTitleParams().text = text;
            return this;
        }

        public Builder setTitleColor(@ColorInt int color) {
            newTitleParams();
            mCircleParams.getTitleParams().textColor = color;
            return this;
        }

        public Builder configTitle(@NonNull ConfigTitle configTitle) {
            newTitleParams();
            configTitle.onConfig(mCircleParams.getTitleParams());
            return this;
        }

        private void newTitleParams() {
            if (mCircleParams.getTitleParams() == null)
                mCircleParams.setTitleParams(new TitleParams());
        }

        public Builder setText(@NonNull String text) {
            newTextParams();
            mCircleParams.getTextParams().text = text;
            return this;
        }

        public Builder setTextColor(@ColorInt int color) {
            newTextParams();
            mCircleParams.getTextParams().textColor = color;
            return this;
        }

        public Builder configText(@NonNull ConfigText configText) {
            newTextParams();
            configText.onConfig(mCircleParams.getTextParams());
            return this;
        }

        private void newTextParams() {
            //判断是否已经设置过
            if (mCircleParams.getDialogParams().gravity == Gravity.NO_GRAVITY)
                mCircleParams.getDialogParams().gravity = Gravity.CENTER;
            if (mCircleParams.getTextParams() == null)
                mCircleParams.setTextParams(new TextParams());
        }

        public Builder setItems(@NonNull Object items, AdapterView.OnItemClickListener listener) {
            newItemsParams();
            ItemsParams params = mCircleParams.getItemsParams();
            params.items = items;
            params.listener = listener;
            return this;
        }


        public Builder configItems(@NonNull ConfigItems configItems) {
            newItemsParams();
            configItems.onConfig(mCircleParams.getItemsParams());
            return this;
        }

        private void newItemsParams() {
            //设置列表特殊的参数
            DialogParams dialogParams = mCircleParams.getDialogParams();
            //判断是否已经设置过
            if (dialogParams.gravity == Gravity.NO_GRAVITY)
                dialogParams.gravity = Gravity.BOTTOM;//默认底部显示
            //判断是否已经设置过
            if (dialogParams.yOff == 0)
                dialogParams.yOff = 20;//底部与屏幕的距离

            if (mCircleParams.getItemsParams() == null)
                mCircleParams.setItemsParams(new ItemsParams() {
                    @Override
                    public void dismiss() {
                        onDismiss();
                    }
                });
        }

        /**
         * 设置进度条文本
         *
         * @param text 进度条文本，style = 水平样式时，支持String.format() 例如：已经下载%s
         * @return
         */
        public Builder setProgressText(@NonNull String text) {
            newProgressParams();
            mCircleParams.getProgressParams().text = text;
            return this;
        }

        /**
         * 设置进度条样式
         *
         * @param style {@link ProgressParams#STYLE_HORIZONTAL 水平样式} or {@link ProgressParams#STYLE_SPINNER}
         * @return
         */
        public Builder setProgressStyle(int style) {
            newProgressParams();
            mCircleParams.getProgressParams().style = style;
            return this;
        }

        public Builder setProgress(int max, int progress) {
            newProgressParams();
            ProgressParams progressParams = mCircleParams.getProgressParams();
            progressParams.max = max;
            progressParams.progress = progress;
            return this;
        }

        public Builder setProgressDrawable(@DrawableRes int progressDrawableId) {
            newProgressParams();
            mCircleParams.getProgressParams().progressDrawableId = progressDrawableId;
            return this;
        }

        public Builder setProgressHeight(int height) {
            newProgressParams();
            mCircleParams.getProgressParams().progressHeight = height;
            return this;
        }

        public Builder configProgress(@NonNull ConfigProgress configProgress) {
            newProgressParams();
            configProgress.onConfig(mCircleParams.getProgressParams());
            return this;
        }

        private void newProgressParams() {
            //判断是否已经设置过
            if (mCircleParams.getDialogParams().gravity == Gravity.NO_GRAVITY)
                mCircleParams.getDialogParams().gravity = Gravity.CENTER;
            if (mCircleParams.getProgressParams() == null)
                mCircleParams.setProgressParams(new ProgressParams());
        }

        public Builder setInputHint(@NonNull String text) {
            newInputParams();
            mCircleParams.getInputParams().hintText = text;
            return this;
        }

        public Builder setInputHeight(int height) {
            newInputParams();
            mCircleParams.getInputParams().inputHeight = height;
            return this;
        }

        public Builder configInput(@NonNull ConfigInput configInput) {
            newInputParams();
            configInput.onConfig(mCircleParams.getInputParams());
            return this;
        }

        private void newInputParams() {
            //判断是否已经设置过
            if (mCircleParams.getDialogParams().gravity == Gravity.NO_GRAVITY)
                mCircleParams.getDialogParams().gravity = Gravity.CENTER;
            if (mCircleParams.getInputParams() == null)
                mCircleParams.setInputParams(new InputParams());
        }

        public Builder setPositive(@NonNull String text, View.OnClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.getPositiveParams();
            params.text = text;
            params.listener = listener;
            return this;
        }

        public Builder setPositiveInput(@NonNull String text, OnInputClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.getPositiveParams();
            params.text = text;
            params.inputListener = listener;
            return this;
        }

        public Builder configPositive(@NonNull ConfigButton configButton) {
            newPositiveParams();
            configButton.onConfig(mCircleParams.getPositiveParams());
            return this;
        }

        private void newPositiveParams() {
            if (mCircleParams.getPositiveParams() == null)
                mCircleParams.setPositiveParams(new ButtonParams() {
                    @Override
                    public void dismiss() {
                        onDismiss();
                    }
                });
        }

        public Builder setNegative(@NonNull String text, View.OnClickListener listener) {
            newNegativeParams();
            ButtonParams params = mCircleParams.getNegativeParams();
            params.text = text;
            params.listener = listener;
            return this;
        }


        public Builder configNegative(@NonNull ConfigButton configButton) {
            newNegativeParams();
            configButton.onConfig(mCircleParams.getNegativeParams());
            return this;
        }

        private void newNegativeParams() {
            if (mCircleParams.getNegativeParams() == null)
                mCircleParams.setNegativeParams(new ButtonParams() {
                    @Override
                    public void dismiss() {
                        onDismiss();
                    }
                });
        }

        private void onDismiss() {
            if (mCircleParams.dialogFragment != null) {
                mCircleParams.dialogFragment.dismiss();
                mActivity = null;
                mCircleParams.dialogFragment = null;
            }
        }

        public DialogFragment create() {
            if (mCircleDialog == null)
                mCircleDialog = new CircleDialog();
            return mCircleDialog.create(mCircleParams);
        }

        public DialogFragment show() {
            DialogFragment dialogFragment = create();
            mCircleDialog.show(mActivity);
            return dialogFragment;
        }
    }
}
