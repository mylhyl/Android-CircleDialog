package com.mylhyl.circledialog;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.callback.ConfigSubTitle;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

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
            if (mDialog.getDialog() != null && mDialog.getDialog().isShowing()) {
                mDialog.refreshView();
            }
        }
        return mDialog;
    }

    @Deprecated
    public void show(FragmentActivity activity) {
        if (activity == null)
            throw new NullPointerException("please call constructor Builder(FragmentActivity)");
        mDialog.show(activity.getSupportFragmentManager(), "circleDialog");
    }

    public void show(FragmentManager manager) {
        mDialog.show(manager, "circleDialog");
    }

    public static class Builder {
        private FragmentActivity mActivity;
        private CircleDialog mCircleDialog;
        private CircleParams mCircleParams;

        public Builder() {
            init();
        }

        private void init() {
            mCircleParams = new CircleParams();
            mCircleParams.dialogParams = new DialogParams();
        }

        @Deprecated
        public Builder(@NonNull FragmentActivity activity) {
            this.mActivity = activity;
            init();
        }

        /**
         * 设置对话框位置
         *
         * @param gravity 位置
         * @return this Builder
         */
        public Builder setGravity(int gravity) {
            mCircleParams.dialogParams.gravity = gravity;
            return this;
        }

        /**
         * 设置对话框点击外部关闭
         *
         * @param cancel true允许
         * @return this Builder
         */
        public Builder setCanceledOnTouchOutside(boolean cancel) {
            mCircleParams.dialogParams.canceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * 设置对话框返回键关闭
         *
         * @param cancel true允许
         * @return this Builder
         */
        public Builder setCancelable(boolean cancel) {
            mCircleParams.dialogParams.cancelable = cancel;
            return this;
        }

        /**
         * 设置对话框宽度
         *
         * @param width 0.0 - 1.0
         * @return this Builder
         */
        public Builder setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
            mCircleParams.dialogParams.width = width;
            return this;
        }

        /**
         * 设置对话框最大高度
         *
         * @param maxHeight 0.0 - 1.0
         * @return this Builder
         */
        public Builder setMaxHeight(@FloatRange(from = 0.0, to = 1.0) float maxHeight) {
            mCircleParams.dialogParams.maxHeight = maxHeight;
            return this;
        }

        /**
         * 设置对话框圆角
         *
         * @param radius 半径
         * @return this Builder
         */
        public Builder setRadius(int radius) {
            mCircleParams.dialogParams.radius = radius;
            return this;
        }

        /**
         * 设置对话框y 坐标偏移
         *
         * @param yOff y 坐标偏移量，默认-1
         * @return this Builder
         */
        public Builder setYoff(int yOff) {
            mCircleParams.dialogParams.yOff = yOff;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener listener) {
            mCircleParams.dismissListener = listener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener listener) {
            mCircleParams.cancelListener = listener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener listener) {
            mCircleParams.showListener = listener;
            return this;
        }

        public Builder configDialog(@NonNull ConfigDialog configDialog) {
            configDialog.onConfig(mCircleParams.dialogParams);
            return this;
        }

        public Builder setTitle(@NonNull String text) {
            newTitleParams();
            mCircleParams.titleParams.text = text;
            return this;
        }

        private void newTitleParams() {
            if (mCircleParams.titleParams == null)
                mCircleParams.titleParams = new TitleParams();
        }

        public Builder setTitleColor(@ColorInt int color) {
            newTitleParams();
            mCircleParams.titleParams.textColor = color;
            return this;
        }

        public Builder configTitle(@NonNull ConfigTitle configTitle) {
            newTitleParams();
            configTitle.onConfig(mCircleParams.titleParams);
            return this;
        }

        public Builder setSubTitle(@NonNull String text) {
            newSubTitleParams();
            mCircleParams.subTitleParams.text = text;
            return this;
        }

        private void newSubTitleParams() {
            if (mCircleParams.subTitleParams == null)
                mCircleParams.subTitleParams = new SubTitleParams();
        }

        public Builder setSubTitleColor(@ColorInt int color) {
            newSubTitleParams();
            mCircleParams.subTitleParams.textColor = color;
            return this;
        }

        public Builder configSubTitle(@NonNull ConfigSubTitle configSubTitle) {
            newSubTitleParams();
            configSubTitle.onConfig(mCircleParams.subTitleParams);
            return this;
        }

        public Builder setText(@NonNull String text) {
            newTextParams();
            mCircleParams.textParams.text = text;
            return this;
        }

        private void newTextParams() {
            //判断是否已经设置过
            if (mCircleParams.dialogParams.gravity == Gravity.NO_GRAVITY)
                mCircleParams.dialogParams.gravity = Gravity.CENTER;
            if (mCircleParams.textParams == null)
                mCircleParams.textParams = new TextParams();
        }

        public Builder setTextColor(@ColorInt int color) {
            newTextParams();
            mCircleParams.textParams.textColor = color;
            return this;
        }

        public Builder configText(@NonNull ConfigText configText) {
            newTextParams();
            configText.onConfig(mCircleParams.textParams);
            return this;
        }

        public Builder setItems(@NonNull Object items, AdapterView.OnItemClickListener listener) {
            newItemsParams();
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            mCircleParams.itemListener = listener;
            return this;
        }

        private void newItemsParams() {
            //设置列表特殊的参数
            DialogParams dialogParams = mCircleParams.dialogParams;
            //判断是否已经设置过
            if (dialogParams.gravity == Gravity.NO_GRAVITY)
                dialogParams.gravity = Gravity.BOTTOM;//默认底部显示
            //判断是否已经设置过
            if (dialogParams.yOff == -1)
                dialogParams.yOff = 20;//底部与屏幕的距离

            if (mCircleParams.itemsParams == null)
                mCircleParams.itemsParams = new ItemsParams();
        }

        public Builder setItems(@NonNull BaseAdapter adapter
                , AdapterView.OnItemClickListener listener) {
            newItemsParams();
            ItemsParams params = mCircleParams.itemsParams;
            params.adapter = adapter;
            mCircleParams.itemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull Object items, OnRvItemClickListener listener) {
            newItemsParams();
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            mCircleParams.rvItemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull Object items, RecyclerView.LayoutManager layoutManager
                , OnRvItemClickListener listener) {
            newItemsParams();
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            params.layoutManager = layoutManager;
            mCircleParams.rvItemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull RecyclerView.Adapter adapter
                , RecyclerView.LayoutManager layoutManager) {
            newItemsParams();
            ItemsParams params = mCircleParams.itemsParams;
            params.layoutManager = layoutManager;
            params.adapterRv = adapter;
            return this;
        }

        public Builder setItems(@NonNull RecyclerView.Adapter adapter
                , RecyclerView.LayoutManager layoutManager
                , RecyclerView.ItemDecoration itemDecoration) {
            newItemsParams();
            ItemsParams params = mCircleParams.itemsParams;
            params.layoutManager = layoutManager;
            params.itemDecoration = itemDecoration;
            params.adapterRv = adapter;
            return this;
        }

        /**
         * 设置items否触发自动关闭对话框，默认自动
         *
         * @param manualClose true=手动；false=自动
         * @return this Builder
         */
        public Builder setItemsManualClose(boolean manualClose) {
            newItemsParams();
            mCircleParams.itemsParams.isManualClose = manualClose;
            return this;
        }

        public Builder configItems(@NonNull ConfigItems configItems) {
            newItemsParams();
            configItems.onConfig(mCircleParams.itemsParams);
            return this;
        }

        /**
         * 设置进度条文本
         *
         * @param text 进度条文本，style = 水平样式时，支持String.format() 例如：已经下载%s
         * @return this Builder
         */
        public Builder setProgressText(@NonNull String text) {
            newProgressParams();
            mCircleParams.progressParams.text = text;
            return this;
        }

        private void newProgressParams() {
            //判断是否已经设置过
            if (mCircleParams.dialogParams.gravity == Gravity.NO_GRAVITY)
                mCircleParams.dialogParams.gravity = Gravity.CENTER;
            if (mCircleParams.progressParams == null)
                mCircleParams.progressParams = new ProgressParams();
        }

        /**
         * 设置进度条样式
         *
         * @param style {@link ProgressParams#STYLE_HORIZONTAL 水平样式} or
         *              {@link ProgressParams#STYLE_SPINNER}
         * @return this Builder
         */
        public Builder setProgressStyle(int style) {
            newProgressParams();
            mCircleParams.progressParams.style = style;
            return this;
        }

        public Builder setProgress(int max, int progress) {
            newProgressParams();
            ProgressParams progressParams = mCircleParams.progressParams;
            progressParams.max = max;
            progressParams.progress = progress;
            return this;
        }

        public Builder setProgressDrawable(@DrawableRes int progressDrawableId) {
            newProgressParams();
            mCircleParams.progressParams.progressDrawableId = progressDrawableId;
            return this;
        }

        public Builder setProgressHeight(int height) {
            newProgressParams();
            mCircleParams.progressParams.progressHeight = height;
            return this;
        }

        public Builder configProgress(@NonNull ConfigProgress configProgress) {
            newProgressParams();
            configProgress.onConfig(mCircleParams.progressParams);
            return this;
        }

        public Builder setInputText(@NonNull String text) {
            newInputParams();
            mCircleParams.inputParams.text = text;
            return this;
        }

        private void newInputParams() {
            //判断是否已经设置过
            if (mCircleParams.dialogParams.gravity == Gravity.NO_GRAVITY)
                mCircleParams.dialogParams.gravity = Gravity.CENTER;
            if (mCircleParams.inputParams == null)
                mCircleParams.inputParams = new InputParams();
        }

        public Builder setInputText(@NonNull String text, @NonNull String hint) {
            newInputParams();
            mCircleParams.inputParams.text = text;
            mCircleParams.inputParams.hintText = hint;
            return this;
        }

        public Builder setInputHint(@NonNull String hint) {
            newInputParams();
            mCircleParams.inputParams.hintText = hint;
            return this;
        }

        public Builder setInputHeight(int height) {
            newInputParams();
            mCircleParams.inputParams.inputHeight = height;
            return this;
        }

        /**
         * 设置是否触发自动关闭对话框，默认自动
         *
         * @param manualClose true=手动；false=自动
         * @return this Builder
         */
        public Builder setInputManualClose(boolean manualClose) {
            newInputParams();
            mCircleParams.inputParams.isManualClose = manualClose;
            return this;
        }

        public Builder configInput(@NonNull ConfigInput configInput) {
            newInputParams();
            configInput.onConfig(mCircleParams.inputParams);
            return this;
        }

        /**
         * 确定按钮
         *
         * @param text
         * @param listener
         * @return this Builder
         */
        public Builder setPositive(@NonNull String text, View.OnClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.text = text;
            mCircleParams.clickPositiveListener = listener;
            return this;
        }

        private void newPositiveParams() {
            if (mCircleParams.positiveParams == null)
                mCircleParams.positiveParams = new ButtonParams();
        }

        /**
         * 输入框的确定按钮
         *
         * @param text
         * @param listener
         * @return this Builder
         */
        public Builder setPositiveInput(@NonNull String text, OnInputClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.text = text;
            mCircleParams.inputListener = listener;
            return this;
        }

        /**
         * 配置确定按钮
         *
         * @param configButton
         * @return this Builder
         */
        public Builder configPositive(@NonNull ConfigButton configButton) {
            newPositiveParams();
            configButton.onConfig(mCircleParams.positiveParams);
            return this;
        }

        /**
         * 取消按钮
         *
         * @param text
         * @param listener
         * @return this Builder
         */
        public Builder setNegative(@NonNull String text, View.OnClickListener listener) {
            newNegativeParams();
            ButtonParams params = mCircleParams.negativeParams;
            params.text = text;
            mCircleParams.clickNegativeListener = listener;
            return this;
        }

        private void newNegativeParams() {
            if (mCircleParams.negativeParams == null)
                mCircleParams.negativeParams = new ButtonParams();
        }

        /**
         * 配置取消按钮
         *
         * @param configButton
         * @return this Builder
         */
        public Builder configNegative(@NonNull ConfigButton configButton) {
            newNegativeParams();
            configButton.onConfig(mCircleParams.negativeParams);
            return this;
        }

        /**
         * 中间按钮
         *
         * @param text
         * @param listener
         * @return this Builder
         */
        public Builder setNeutral(@NonNull String text, View.OnClickListener listener) {
            newNeutralParams();
            ButtonParams params = mCircleParams.neutralParams;
            params.text = text;
            mCircleParams.clickNeutralListener = listener;
            return this;
        }

        private void newNeutralParams() {
            if (mCircleParams.neutralParams == null)
                mCircleParams.neutralParams = new ButtonParams();
        }

        /**
         * 配置中间按钮
         *
         * @param configButton
         * @return this Builder
         */
        public Builder configNeutral(@NonNull ConfigButton configButton) {
            newNeutralParams();
            configButton.onConfig(mCircleParams.neutralParams);
            return this;
        }

        @Deprecated
        public DialogFragment show() {
            DialogFragment dialogFragment = create();
            mCircleDialog.show(mActivity);
            return dialogFragment;
        }

        public DialogFragment create() {
            if (mCircleDialog == null)
                mCircleDialog = new CircleDialog();
            return mCircleDialog.create(mCircleParams);
        }

        public DialogFragment show(FragmentManager manager) {
            DialogFragment dialogFragment = create();
            mCircleDialog.show(manager);
            return dialogFragment;
        }
    }
}
