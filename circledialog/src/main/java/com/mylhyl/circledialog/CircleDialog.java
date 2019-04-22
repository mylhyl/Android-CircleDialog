package com.mylhyl.circledialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;

import com.mylhyl.circledialog.callback.CircleItemViewBinder;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.callback.ConfigLottie;
import com.mylhyl.circledialog.callback.ConfigPopup;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.callback.ConfigSubTitle;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.engine.ImageLoadEngine;
import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CloseParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.LottieParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnCreateBodyViewListener;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;
import com.mylhyl.circledialog.view.listener.OnCreateLottieListener;
import com.mylhyl.circledialog.view.listener.OnCreateProgressListener;
import com.mylhyl.circledialog.view.listener.OnCreateTextListener;
import com.mylhyl.circledialog.view.listener.OnCreateTitleListener;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;
import com.mylhyl.circledialog.view.listener.OnLvItemClickListener;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

import java.util.List;

/**
 * Created by hupei on 2017/3/29.
 */

public final class CircleDialog {
    private BaseCircleDialog mBaseCircleDialog;

    private CircleDialog() {
    }

    public void refresh() {
        if (mBaseCircleDialog == null) return;
        Dialog dialog = mBaseCircleDialog.getDialog();
        if (dialog == null || !dialog.isShowing()) return;
        mBaseCircleDialog.refreshView();
    }

    public BaseCircleDialog create(CircleParams params) {
        mBaseCircleDialog = BaseCircleDialog.newAbsCircleDialog(params);
        return mBaseCircleDialog;
    }

    public void show(FragmentManager manager) {
        mBaseCircleDialog.show(manager, "circleDialog");
    }

    public static class Builder {
        private CircleDialog mCircleDialog;
        private CircleParams mCircleParams;

        public Builder() {
            mCircleParams = new CircleParams();
            mCircleParams.dialogParams = new DialogParams();
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

        public Builder bottomFull() {
            mCircleParams.dialogParams.gravity = Gravity.BOTTOM;
            mCircleParams.dialogParams.radius = 0;
            mCircleParams.dialogParams.width = 1f;
            mCircleParams.dialogParams.yOff = 0;
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

        public Builder setTitleIcon(@DrawableRes int icon) {
            newTitleParams();
            mCircleParams.titleParams.icon = icon;
            return this;
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

        public Builder setOnCreateTitleListener(@NonNull OnCreateTitleListener listener) {
            mCircleParams.createTitleListener = listener;
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
            if (mCircleParams.textParams == null) {
                mCircleParams.textParams = new TextParams();
            }
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

        public Builder setOnCreateTextListener(OnCreateTextListener listener) {
            mCircleParams.createTextListener = listener;
            return this;
        }

        public Builder setItems(@NonNull Object items, OnLvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = true;
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            mCircleParams.itemListener = listener;
            return this;
        }

        private void newItemsParams() {
            if (mCircleParams.itemsParams == null) {
                mCircleParams.itemsParams = new ItemsParams();
            }
        }

        public Builder setItems(@NonNull BaseAdapter adapter
                , OnLvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = true;
            ItemsParams params = mCircleParams.itemsParams;
            params.adapter = adapter;
            mCircleParams.itemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull Object items, @NonNull OnRvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            mCircleParams.rvItemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull Object items, RecyclerView.LayoutManager layoutManager
                , @NonNull OnRvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            params.layoutManager = layoutManager;
            mCircleParams.rvItemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull RecyclerView.Adapter adapter
                , @NonNull RecyclerView.LayoutManager layoutManager) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.layoutManager = layoutManager;
            params.adapterRv = adapter;
            return this;
        }

        public Builder setItems(@NonNull RecyclerView.Adapter adapter
                , @NonNull RecyclerView.LayoutManager layoutManager
                , @NonNull RecyclerView.ItemDecoration itemDecoration) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.layoutManager = layoutManager;
            params.itemDecoration = itemDecoration;
            params.adapterRv = adapter;
            return this;
        }

        public Builder setItemsViewBinder(CircleItemViewBinder viewBinder) {
            newItemsParams();
            mCircleParams.itemsParams.viewBinder = viewBinder;
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
            if (mCircleParams.progressParams == null) {
                mCircleParams.progressParams = new ProgressParams();
            }
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

        /**
         * 设置自定义等待框视图
         *
         * @param bodyViewId resLayoutId
         * @param listener   listener
         * @return Builder
         */
        public Builder setBodyView(@LayoutRes int bodyViewId, OnCreateBodyViewListener listener) {
            mCircleParams.bodyViewId = bodyViewId;
            mCircleParams.createBodyViewListener = listener;
            return this;
        }

        public Builder setOnCreateProgressListener(OnCreateProgressListener listener) {
            mCircleParams.createProgressListener = listener;
            return this;
        }

        public Builder setInputText(@NonNull String text) {
            newInputParams();
            mCircleParams.inputParams.text = text;
            return this;
        }

        private void newInputParams() {
            if (mCircleParams.inputParams == null) {
                mCircleParams.inputParams = new InputParams();
            }
        }

        /**
         * 是否自动显示键盘，默认不显示
         *
         * @param show true=显示；false=隐藏
         * @return Builder
         */
        public Builder setInputShowKeyboard(boolean show) {
            newInputParams();
            mCircleParams.inputParams.showSoftKeyboard = show;
            return this;
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
         * 输入框最大字符数量
         *
         * @param maxLen 字符数量
         * @return Builder
         */
        public Builder setInputCounter(int maxLen) {
            newInputParams();
            mCircleParams.inputParams.maxLen = maxLen;
            return this;
        }

        /**
         * 输入框最大字符数量颜色
         *
         * @param color 色值
         * @return Builder
         */
        public Builder setInputCounterColor(@ColorInt int color) {
            newInputParams();
            mCircleParams.inputParams.counterColor = color;
            return this;
        }

        /**
         * 输入框最大字符数量
         *
         * @param maxLen   字符数量
         * @param listener 字符计数器改变事件
         * @return Builder
         */
        public Builder setInputCounter(int maxLen, OnInputCounterChangeListener listener) {
            newInputParams();
            mCircleParams.inputParams.maxLen = maxLen;
            mCircleParams.inputCounterChangeListener = listener;
            return this;
        }

        /**
         * 是否禁止输入表情，默认开启
         *
         * @param disable true=禁止；false=开启
         * @return this Builder
         */
        public Builder setInputEmoji(boolean disable) {
            newInputParams();
            mCircleParams.inputParams.isEmojiInput = disable;
            return this;
        }

        public Builder configInput(@NonNull ConfigInput configInput) {
            newInputParams();
            configInput.onConfig(mCircleParams.inputParams);
            return this;
        }

        /**
         * 输入框的确定按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setPositiveInput(@NonNull String text, OnInputClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.text = text;
            mCircleParams.inputListener = listener;
            return this;
        }

        private void newPositiveParams() {
            if (mCircleParams.positiveParams == null)
                mCircleParams.positiveParams = new ButtonParams();
        }

        public Builder setOnCreateInputListener(OnCreateInputListener listener) {
            mCircleParams.createInputListener = listener;
            return this;
        }

        public Builder setLottieAnimation(String animationFileName) {
            newLottieParams();
            mCircleParams.lottieParams.animationFileName = animationFileName;
            return this;
        }

        private void newLottieParams() {
            if (mCircleParams.lottieParams == null) {
                mCircleParams.lottieParams = new LottieParams();
            }
        }

        public Builder setLottieAnimation(int animationResId) {
            newLottieParams();
            mCircleParams.lottieParams.animationResId = animationResId;
            return this;
        }

        public Builder playLottieAnimation() {
            newLottieParams();
            mCircleParams.lottieParams.autoPlay = true;
            return this;
        }

        public Builder setLottieLoop(boolean loop) {
            newLottieParams();
            mCircleParams.lottieParams.loop = loop;
            return this;
        }

        public Builder setLottieText(String text) {
            newLottieParams();
            mCircleParams.lottieParams.text = text;
            return this;
        }

        public Builder setLottieSize(int width, int height) {
            newLottieParams();
            mCircleParams.lottieParams.lottieWidth = width;
            mCircleParams.lottieParams.lottieHeight = height;
            return this;
        }

        public Builder configLottie(@NonNull ConfigLottie configLottie) {
            newLottieParams();
            configLottie.onConfig(mCircleParams.lottieParams);
            return this;
        }

        public Builder setOnCreateLottieListener(OnCreateLottieListener listener) {
            mCircleParams.createLottieListener = listener;
            return this;
        }

        /**
         * 确定按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setPositive(@NonNull String text, View.OnClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.text = text;
            mCircleParams.clickPositiveListener = listener;
            return this;
        }

        /**
         * 配置确定按钮
         *
         * @param configButton configButton
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
         * @param text     按钮文本
         * @param listener 事件
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
            if (mCircleParams.negativeParams == null) {
                mCircleParams.negativeParams = new ButtonParams();
                mCircleParams.negativeParams.textColor = CircleColor.FOOTER_BUTTON_TEXT_NEGATIVE;
            }
        }

        /**
         * 配置取消按钮
         *
         * @param configButton configButton
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
         * @param text     按钮文本
         * @param listener 事件
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
         * @param configButton configButton
         * @return this Builder
         */
        public Builder configNeutral(@NonNull ConfigButton configButton) {
            newNeutralParams();
            configButton.onConfig(mCircleParams.neutralParams);
            return this;
        }

        public Builder setOnCreateButtonListener(OnCreateButtonListener listener) {
            mCircleParams.createButtonListener = listener;
            return this;
        }

        public Builder setPopup(View anchorView, @PopupParams.TriangleGravity int triangleGravity) {
            newPopupParams();
            PopupParams params = mCircleParams.popupParams;
            params.anchorView = anchorView;
            params.triangleGravity = triangleGravity;
            return this;
        }

        private void newPopupParams() {
            if (mCircleParams.popupParams == null) {
                mCircleParams.popupParams = new PopupParams();
            }
        }

        public Builder configPopup(@NonNull ConfigPopup configPopup) {
            newPopupParams();
            configPopup.onConfig(mCircleParams.popupParams);
            return this;
        }

        public Builder setPopupTriangleSize(int width, int height) {
            newPopupParams();
            mCircleParams.popupParams.triangleSize = new int[]{width, height};
            return this;
        }

        /**
         * 显示三角 默认显示
         *
         * @param show false隐藏
         * @return this Builder
         */
        public Builder setPopupTriangleShow(boolean show) {
            newPopupParams();
            mCircleParams.popupParams.triangleShow = show;
            return this;
        }

        public Builder setPopupItems(@NonNull Object items, OnRvItemClickListener listener) {
            newPopupParams();
            mCircleParams.popupParams.items = items;
            mCircleParams.rvItemListener = listener;
            return this;
        }

        public Builder setPopupItems(@NonNull Object items, RecyclerView.LayoutManager layoutManager
                , OnRvItemClickListener listener) {
            newPopupParams();
            PopupParams params = mCircleParams.popupParams;
            params.items = items;
            params.layoutManager = layoutManager;
            mCircleParams.rvItemListener = listener;
            return this;
        }

        public Builder setPopupItems(@NonNull RecyclerView.Adapter adapter
                , RecyclerView.LayoutManager layoutManager) {
            newPopupParams();
            PopupParams params = mCircleParams.popupParams;
            params.layoutManager = layoutManager;
            params.adapterRv = adapter;
            return this;
        }

        public Builder setPopupItems(@NonNull RecyclerView.Adapter adapter
                , RecyclerView.LayoutManager layoutManager
                , RecyclerView.ItemDecoration itemDecoration) {
            newPopupParams();
            PopupParams params = mCircleParams.popupParams;
            params.layoutManager = layoutManager;
            params.itemDecoration = itemDecoration;
            params.adapterRv = adapter;
            return this;
        }

        public Builder setCloseResId(@DrawableRes int closeResId) {
            setCloseResId(closeResId, 0);
            return this;
        }

        public Builder setCloseResId(@DrawableRes int closeResId, int closeSize) {
            newCloseParams();
            mCircleParams.closeParams.closeResId = closeResId;
            mCircleParams.closeParams.closeSize = closeSize;
            return this;
        }

        private void newCloseParams() {
            if (mCircleParams.closeParams == null) {
                mCircleParams.closeParams = new CloseParams();
            }
        }

        public Builder setClosePadding(int[] closePadding) {
            newCloseParams();
            mCircleParams.closeParams.closePadding = closePadding;
            return this;
        }

        public Builder setCloseGravity(@CloseParams.CloseGravity int closeGravity) {
            newCloseParams();
            mCircleParams.closeParams.closeGravity = closeGravity;
            return this;
        }

        public Builder setCloseConnector(int width, int height) {
            newCloseParams();
            mCircleParams.closeParams.connectorWidth = width;
            mCircleParams.closeParams.connectorHeight = height;
            return this;
        }

        public Builder setCloseConnector(int width, int height, int color) {
            newCloseParams();
            mCircleParams.closeParams.connectorWidth = width;
            mCircleParams.closeParams.connectorHeight = height;
            mCircleParams.closeParams.connectorColor = color;
            return this;
        }

        public Builder setAdResId(@DrawableRes int resId, OnAdItemClickListener listener) {
            newAdParams();
            return setAdResId(new int[]{resId}, listener);
        }

        private void newAdParams() {
            if (mCircleParams.adParams == null) {
                mCircleParams.adParams = new AdParams();
            }
        }

        public Builder setAdResId(@DrawableRes int[] resIds, OnAdItemClickListener listener) {
            newAdParams();
            mCircleParams.adParams.resIds = resIds;
            mCircleParams.adItemClickListener = listener;
            return this;
        }

        /**
         * 设置广告框的单个图片url
         *
         * @param url      广告图片url路径
         * @param listener 图片的点击监听器
         * @return Builder
         */
        public Builder setAdUrl(String url, OnAdItemClickListener listener) {
            return setAdUrl(new String[]{url}, listener);
        }

        /**
         * 设置广告框的多图片url
         *
         * @param urls     广告图片url路径数组
         * @param listener 图片的点击监听器
         * @return Builder
         */
        public Builder setAdUrl(String[] urls, OnAdItemClickListener listener) {
            newAdParams();
            mCircleParams.adParams.urls = urls;
            mCircleParams.adItemClickListener = listener;
            return this;
        }

        public Builder setAdUrl(List<String> urls, OnAdItemClickListener listener) {
            return setAdUrl(urls.toArray(new String[urls.size()]), listener);
        }

        /**
         * 广告框的指示器，内置样式，也可调用 {@link #setAdIndicatorPoint(int)}自定义
         *
         * @param show true 显示
         * @return Builder
         */
        public Builder setAdIndicator(boolean show) {
            newAdParams();
            mCircleParams.adParams.isShowIndicator = show;
            return this;
        }

        /**
         * 广告框指示器资源文件
         *
         * @param resId
         * @return Builder
         */
        public Builder setAdIndicatorPoint(@DrawableRes int resId) {
            newAdParams();
            mCircleParams.adParams.pointDrawableResId = resId;
            return this;
        }

        public Builder setImageLoadEngine(ImageLoadEngine loadImageListener) {
            mCircleParams.imageLoadEngine = loadImageListener;
            return this;
        }

        public BaseCircleDialog show(FragmentManager manager) {
            BaseCircleDialog dialogFragment = create();
            mCircleDialog.show(manager);
            return dialogFragment;
        }

        public BaseCircleDialog create() {
            if (mCircleDialog == null) {
                mCircleDialog = new CircleDialog();
            }
            return mCircleDialog.create(mCircleParams);
        }

        public void refresh() {
            if (mCircleDialog != null) {
                mCircleDialog.refresh();
            }
        }
    }
}
