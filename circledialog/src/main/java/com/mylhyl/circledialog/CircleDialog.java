package com.mylhyl.circledialog;

import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;

/**
 * Created by hupei on 2017/3/29.
 */

public class CircleDialog {
    private AbsCircleDialog mDialog;

    private CircleDialog() {
    }

    public DialogFragment create(CircleParams params) {
        if (mDialog == null)
            mDialog = AbsCircleDialog.newAbsCircleDialog(params);
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
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            mCircleParams.getDialogParams().gravity = gravity;
            return this;
        }

        /**
         * 设置对话框点击外部关闭
         *
         * @param cancel
         * @return
         */
        public Builder setCanceledOnTouchOutside(boolean cancel) {
            mCircleParams.getDialogParams().canceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * 设置对话框返回键关闭
         *
         * @param cancel
         * @return
         */
        public Builder setCancelable(boolean cancel) {
            mCircleParams.getDialogParams().cancelable = cancel;
            return this;
        }

        /**
         * 设置对话框宽度
         *
         * @param width 0.0 - 1.0
         * @return
         */
        public Builder setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
            mCircleParams.getDialogParams().width = width;
            return this;
        }

        /**
         * 设置对话框圆角
         *
         * @param radius
         * @return
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
            mCircleParams.getDialogParams().gravity = Gravity.CENTER;
            if (mCircleParams.getTextParams() == null)
                mCircleParams.setTextParams(new TextParams());
        }


        public Builder setPositive(@NonNull String text, View.OnClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.getPositiveParams();
            params.text = text;
            params.listener = listener;
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

        public Builder setItems(@NonNull Object items, AdapterView.OnItemClickListener listener) {
            newItemsParams();
            ItemsParams params = mCircleParams.getItemsParams();
            params.items = items;
            params.listener = listener;
            return this;
        }


        public Builder configNegative(@NonNull ConfigItems configItems) {
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
            if (dialogParams.y == 0)
                dialogParams.y = 20;//底部与屏幕的距离

            if (mCircleParams.getItemsParams() == null)
                mCircleParams.setItemsParams(new ItemsParams() {
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
