package com.mylhyl.circledialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mylhyl.circledialog.callback.CircleItemViewBinder;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.callback.ConfigLottie;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.callback.ConfigSubTitle;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.CloseParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.LottieParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.view.listener.CountDownTimerObserver;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnAdPageChangeListener;
import com.mylhyl.circledialog.view.listener.OnBindBodyViewCallback;
import com.mylhyl.circledialog.view.listener.OnButtonClickListener;
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
import com.mylhyl.circledialog.view.listener.OnShowListener;

import java.util.List;

/**
 * Created by hupei on 2017/3/29.
 */

public final class CircleDialog {
    private BaseCircleDialog mBaseCircleDialog;

    private CircleDialog() {
    }

    private void refresh() {
        if (check()) {
            return;
        }
        mBaseCircleDialog.refreshView();
    }

    private void dismiss() {
        if (check()) {
            return;
        }
        mBaseCircleDialog.dialogDismiss();
    }

    private void show(FragmentManager manager) {
        mBaseCircleDialog.show(manager);
    }

    private BaseCircleDialog create(CircleParams params) {
        return mBaseCircleDialog = BaseCircleDialog.newAbsCircleDialog(params);
    }

    private boolean check() {
        if (mBaseCircleDialog == null) {
            return true;
        }
        Dialog dialog = mBaseCircleDialog.getDialog();
        if (dialog == null || !dialog.isShowing()) {
            return true;
        }
        return false;
    }

    public static final class Builder {
        private CircleDialog mCircleDialog;
        private CircleParams mCircleParams;

        public Builder() {
            mCircleParams = new CircleParams();
            mCircleParams.dialogParams = new DialogParams();
        }

        // ******************************* 对话框的相关属性 *******************************

        /**
         * 是否手动关闭对话框，默认按钮事件响应后自动关闭
         *
         * @param manualClose true将不会自动关闭，调用{@link DialogFragment#dismissAllowingStateLoss()
         *                    dismissAllowingStateLoss()} or {@link #dismiss()}关闭对话框
         * @return this Builder
         */
        public Builder setManualClose(boolean manualClose) {
            mCircleParams.dialogParams.manualClose = manualClose;
            return this;
        }

        /**
         * 对话框位置
         *
         * @param gravity 位置 {@link android.view.Gravity}
         * @return this Builder
         */
        public Builder setGravity(int gravity) {
            mCircleParams.dialogParams.gravity = gravity;
            return this;
        }

        /**
         * 对话框是否允许点击外部关闭
         *
         * @param cancel true 允许
         * @return this Builder
         */
        public Builder setCanceledOnTouchOutside(boolean cancel) {
            mCircleParams.dialogParams.canceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * 对话框是否允许返回键关闭
         *
         * @param cancel true 允许
         * @return this Builder
         */
        public Builder setCancelable(boolean cancel) {
            mCircleParams.dialogParams.cancelable = cancel;
            return this;
        }

        /**
         * 对话框宽度
         *
         * @param width 0.0 - 1.0
         * @return this Builder
         */
        public Builder setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
            mCircleParams.dialogParams.width = width;
            return this;
        }

        /**
         * 对话框最大高度
         *
         * @param maxHeight 范围值：0.0 - 1.0 之间
         * @return this Builder
         */
        public Builder setMaxHeight(@FloatRange(from = 0.0, to = 1.0) float maxHeight) {
            mCircleParams.dialogParams.maxHeight = maxHeight;
            return this;
        }

        /**
         * 对话框圆角
         *
         * @param radius 半径
         * @return this Builder
         */
        public Builder setRadius(int radius) {
            mCircleParams.dialogParams.radius = radius;
            return this;
        }

        /**
         * 对话框y 坐标偏移
         *
         * @param yOff y 坐标偏移量，默认-1
         * @return this Builder
         */
        public Builder setYoff(int yOff) {
            mCircleParams.dialogParams.yOff = yOff;
            return this;
        }

        /**
         * 对话框在底部且是全屏宽
         *
         * @return this Builder
         */
        public Builder bottomFull() {
            mCircleParams.dialogParams.gravity = Gravity.BOTTOM;
            mCircleParams.dialogParams.radius = 0;
            mCircleParams.dialogParams.width = 1f;
            mCircleParams.dialogParams.yOff = 0;
            return this;
        }

        /**
         * 对话框字体
         *
         * @param typeface
         * @return this Builder
         */
        public Builder setTypeface(Typeface typeface) {
            mCircleParams.dialogParams.typeface = typeface;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener listener) {
            mCircleParams.circleListeners.dismissListener = listener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener listener) {
            mCircleParams.circleListeners.cancelListener = listener;
            return this;
        }

        public Builder setOnShowListener(OnShowListener listener) {
            mCircleParams.circleListeners.showListener = listener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener listener) {
            mCircleParams.circleListeners.keyListener = listener;
            return this;
        }

        public Builder configDialog(@NonNull ConfigDialog configDialog) {
            configDialog.onConfig(mCircleParams.dialogParams);
            return this;
        }

        // ******************************* 标题的相关属性 *******************************

        public Builder setTitle(@NonNull String text) {
            newTitleParams();
            mCircleParams.titleParams.text = text;
            return this;
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
            mCircleParams.circleListeners.createTitleListener = listener;
            return this;
        }

        // ******************************* 副标题的相关属性 *******************************

        public Builder setSubTitle(@NonNull String text) {
            newSubTitleParams();
            mCircleParams.subTitleParams.text = text;
            return this;
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

        // ******************************* 纯文本内容的相关属性 *******************************

        public Builder setText(@NonNull String text) {
            newTextParams();
            mCircleParams.textParams.text = text;
            return this;
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
            mCircleParams.circleListeners.createTextListener = listener;
            return this;
        }

        // ******************************* listView列表内容框的相关属性 *******************************

        public Builder setItems(@NonNull Object items, OnLvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = true;
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            mCircleParams.circleListeners.itemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull BaseAdapter adapter, OnLvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = true;
            ItemsParams params = mCircleParams.itemsParams;
            params.adapter = adapter;
            mCircleParams.circleListeners.itemListener = listener;
            return this;
        }

        // ******************************* RecyclerView列表内容的相关属性 *******************************

        public Builder setItems(@NonNull Object items, @NonNull OnRvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            mCircleParams.circleListeners.rvItemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull Object items, RecyclerView.LayoutManager layoutManager,
                @NonNull OnRvItemClickListener listener) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.items = items;
            params.layoutManager = layoutManager;
            mCircleParams.circleListeners.rvItemListener = listener;
            return this;
        }

        public Builder setItems(@NonNull RecyclerView.Adapter adapter,
                @NonNull RecyclerView.LayoutManager layoutManager) {
            newItemsParams();
            mCircleParams.itemListViewType = false;
            ItemsParams params = mCircleParams.itemsParams;
            params.layoutManager = layoutManager;
            params.adapterRv = adapter;
            return this;
        }

        public Builder setItems(@NonNull RecyclerView.Adapter adapter,
                @NonNull RecyclerView.LayoutManager layoutManager,
                @NonNull RecyclerView.ItemDecoration itemDecoration) {
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

        // ******************************* 进度框内容的相关属性 *******************************

        /**
         * 设置进度条文本
         *
         * @param text 进度条文本，style = 水平样式时，支持String.format() 例如：已经下载%s
         * @return this Builder
         */
        public Builder setProgressText(@NonNull String text) {
            return setProgressText(text, "");
        }

        /**
         * 设置进度条文本
         *
         * @param text        进度条文本，style = 水平样式时，支持String.format() 例如：已经下载%s
         * @param timeoutText 超时文本 {@link #setPositive(String, OnButtonClickListener)}
         * @return this Builder
         */
        public Builder setProgressText(@NonNull String text, String timeoutText) {
            newProgressParams();
            mCircleParams.progressParams.text = text;
            mCircleParams.progressParams.timeoutText = timeoutText;
            return this;
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

        /**
         * 设置圆形样式的颜色
         *
         * @param color RGB颜色值
         * @return this Builder
         * @since 5.0.5
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public Builder setProgressColor(@ColorInt int color) {
            newProgressParams();
            mCircleParams.progressParams.indeterminateColor = color;
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

        public Builder setOnCreateProgressListener(OnCreateProgressListener listener) {
            mCircleParams.circleListeners.createProgressListener = listener;
            return this;
        }

        // ******************************* 自定义内容框的相关属性 *******************************

        /**
         * 设置自定义内容视图
         *
         * @param bodyViewId resLayoutId
         * @param listener   listener
         * @return this Builder
         */
        public Builder setBodyView(@LayoutRes int bodyViewId, OnCreateBodyViewListener listener) {
            mCircleParams.bodyViewId = bodyViewId;
            mCircleParams.circleListeners.createBodyViewListener = listener;
            return this;
        }

        /**
         * 设置自定义内容视图
         *
         * @param bodyView View
         * @param listener listener
         * @return this Builder
         * @since 4.0.2
         */
        public Builder setBodyView(View bodyView, OnCreateBodyViewListener listener) {
            mCircleParams.bodyView = bodyView;
            mCircleParams.circleListeners.createBodyViewListener = listener;
            return this;
        }

        /**
         * 设置自定义内容视图
         *
         * @param bodyViewId resLayoutId
         * @return this Builder
         */
        public Builder setBodyView(@LayoutRes int bodyViewId) {
            mCircleParams.bodyViewId = bodyViewId;
            return this;
        }

        /**
         * 设置自定义内容视图
         *
         * @param bodyView View
         * @return this Builder
         * @since 4.0.2
         */
        public Builder setBodyView(View bodyView) {
            mCircleParams.bodyView = bodyView;
            return this;
        }

        /**
         * 自定义框的确定按钮
         *
         * @param text     按钮文本
         * @param callback 自定义body view回调
         * @return this Builder
         */
        public Builder setPositiveBody(@NonNull String text, OnBindBodyViewCallback callback) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.text = text;
            mCircleParams.circleListeners.bindBodyViewCallback = callback;
            return this;
        }

        // ******************************* 输入框的相关属性 *******************************

        public Builder setInputText(@NonNull String text) {
            newInputParams();
            mCircleParams.inputParams.text = text;
            return this;
        }

        /**
         * 是否自动显示键盘，默认不显示
         *
         * @param show true=显示；false=隐藏
         * @return this Builder
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
         * 输入框最大字符长度，默认按字节方式计算长度
         *
         * @param maxLen 字符长度
         * @return this Builder
         * @see InputParams#maxLengthType
         */
        public Builder setInputCounter(int maxLen) {
            return setInputCounter(maxLen, null);
        }

        /**
         * 输入框最大字符长度，默认按字节方式计算长度
         *
         * @param maxLen   字符长度
         * @param listener 字符长度计数器监听器
         * @return this Builder
         * @see InputParams#maxLengthType
         */
        public Builder setInputCounter(int maxLen, OnInputCounterChangeListener listener) {
            newInputParams();
            mCircleParams.inputParams.maxLen = maxLen;
            mCircleParams.circleListeners.inputCounterChangeListener = listener;
            return this;
        }

        /**
         * 输入框最大字符数量颜色
         *
         * @param color 色值
         * @return this Builder
         */
        public Builder setInputCounterColor(@ColorInt int color) {
            newInputParams();
            mCircleParams.inputParams.counterColor = color;
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
            mCircleParams.circleListeners.inputListener = listener;
            return this;
        }

        public Builder configInput(@NonNull ConfigInput configInput) {
            newInputParams();
            configInput.onConfig(mCircleParams.inputParams);
            return this;
        }

        public Builder setOnCreateInputListener(OnCreateInputListener listener) {
            mCircleParams.circleListeners.createInputListener = listener;
            return this;
        }

        // ******************************* Lottie动画框架的相关属性 *******************************

        /**
         * 设置动画资源<br>
         * 如果有目录，调用{@link #configLottie(ConfigLottie)} 方法，
         * 配置{@link LottieParams#imageAssetsFolder imageAssetsFolder属性}
         *
         * @param animationFileName
         * @return this Builder
         */
        public Builder setLottieAnimation(String animationFileName) {
            newLottieParams();
            mCircleParams.lottieParams.animationFileName = animationFileName;
            return this;
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
            mCircleParams.circleListeners.createLottieListener = listener;
            return this;
        }

        // ******************************* 关闭按钮的相关属性 *******************************

        public Builder setCloseResId(@DrawableRes int closeResId) {
            setCloseResId(closeResId, 0);
            return this;
        }

        /**
         * 关闭按钮图标资源文件id和大小
         *
         * @param closeResId 资源文件resId
         * @param closeSize  大小 dp
         * @return this Builder
         */
        public Builder setCloseResId(@DrawableRes int closeResId, int closeSize) {
            newCloseParams();
            mCircleParams.closeParams.closeResId = closeResId;
            mCircleParams.closeParams.closeSize = closeSize;
            return this;
        }

        /**
         * 关闭按钮图标内间距
         *
         * @param closePadding left, top, right, bottom
         * @return this Builder
         */
        public Builder setClosePadding(int[] closePadding) {
            newCloseParams();
            mCircleParams.closeParams.closePadding = closePadding;
            return this;
        }

        /**
         * 关闭按钮的位置
         *
         * @param closeGravity 位置 {@link CloseParams#CLOSE_TOP_LEFT 左上} or {@link CloseParams#CLOSE_TOP_RIGHT 右上}
         *                     or {@link CloseParams#CLOSE_TOP_CENTER 上中} or {@link CloseParams#CLOSE_BOTTOM_LEFT 左下}
         *                     or {@link CloseParams#CLOSE_BOTTOM_RIGHT 右下} or
         *                     {@link CloseParams#CLOSE_BOTTOM_CENTER 下中}
         * @return this Builder
         */
        public Builder setCloseGravity(@CloseParams.CloseGravity int closeGravity) {
            newCloseParams();
            mCircleParams.closeParams.closeGravity = closeGravity;
            return this;
        }

        /**
         * 关闭按钮与对话框之间的连接线大小，只有大于0才显示，默认是0
         *
         * @param width  dp
         * @param height dp
         * @return this Builder
         */
        public Builder setCloseConnector(int width, int height) {
            newCloseParams();
            mCircleParams.closeParams.connectorWidth = width;
            mCircleParams.closeParams.connectorHeight = height;
            return this;
        }

        /**
         * 关闭按钮与对话框之间的连接线大小，只有大于0才显示，默认是0
         *
         * @param width  width
         * @param height height
         * @param color  线的颜色值 rgb，默认 0xFFFFFFFF
         * @return this Builder
         */
        public Builder setCloseConnector(int width, int height, @ColorInt int color) {
            newCloseParams();
            mCircleParams.closeParams.connectorWidth = width;
            mCircleParams.closeParams.connectorHeight = height;
            mCircleParams.closeParams.connectorColor = color;
            return this;
        }

        // ******************************* 广告框的相关属性 *******************************

        public Builder setAdResId(@DrawableRes int resId, OnAdItemClickListener listener) {
            newAdParams();
            return setAdResId(new int[]{resId}, listener);
        }

        public Builder setAdResId(@DrawableRes int[] resIds, OnAdItemClickListener listener) {
            newAdParams();
            mCircleParams.adParams.resIds = resIds;
            mCircleParams.circleListeners.adItemClickListener = listener;
            return this;
        }

        /**
         * 设置广告框的单个图片url
         *
         * @param url      广告图片url路径
         * @param listener 图片的点击监听器
         * @return this Builder
         */
        public Builder setAdUrl(String url, OnAdItemClickListener listener) {
            return setAdUrl(new String[]{url}, listener);
        }

        /**
         * 设置广告框的多图片url
         *
         * @param urls     广告图片url路径数组
         * @param listener 图片的点击监听器
         * @return this Builder
         */
        public Builder setAdUrl(String[] urls, OnAdItemClickListener listener) {
            newAdParams();
            mCircleParams.adParams.urls = urls;
            mCircleParams.circleListeners.adItemClickListener = listener;
            return this;
        }

        public Builder setAdUrl(List<String> urls, OnAdItemClickListener listener) {
            return setAdUrl(urls.toArray(new String[urls.size()]), listener);
        }

        /**
         * 广告框的指示器，内置样式，也可调用 {@link #setAdIndicatorPoint(int)}自定义
         *
         * @param show true 显示
         * @return this Builder
         */
        public Builder setAdIndicator(boolean show) {
            newAdParams();
            mCircleParams.adParams.isShowIndicator = show;
            return this;
        }

        /**
         * 广告框指示器资源文件
         *
         * @param resId resId
         * @return this Builder
         */
        public Builder setAdIndicatorPoint(@DrawableRes int resId) {
            newAdParams();
            mCircleParams.adParams.pointDrawableResId = resId;
            return this;
        }

        public Builder setAdPageChangeListener(OnAdPageChangeListener listener) {
            mCircleParams.circleListeners.adPageChangeListener = listener;
            return this;
        }

        // ******************************* 底部按钮的相关属性 *******************************

        /**
         * 确定按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setPositive(@NonNull String text, OnButtonClickListener listener) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.text = text;
            mCircleParams.circleListeners.clickPositiveListener = listener;
            return this;
        }

        /**
         * 确定按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         * @deprecated
         */
        public Builder setPositive(@NonNull String text, final View.OnClickListener listener) {
            return setPositive(text, new OnButtonClickListener() {
                @Override
                public boolean onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    return true;
                }
            });

        }

        /**
         * 确定超时按钮
         *
         * @param countDownTime     倒计时时长
         * @param countDownInterval 倒计时间隔
         * @param observer          计时器观察者对象
         * @return this Builder
         */
        public Builder setPositiveTime(long countDownTime, long countDownInterval, CountDownTimerObserver observer) {
            return setPositiveTime(countDownTime, countDownInterval, "", observer);
        }

        /**
         * 确定超时按钮
         *
         * @param countDownTime     倒计时时长
         * @param countDownInterval 倒计时间隔
         * @param countDownText     倒计时结束后的文本（支持占位符），如果为空则是：text + COUNT_DOWN_TEXT_FORMAT
         * @param observer          计时器观察者对象
         * @return this Builder
         */
        public Builder setPositiveTime(long countDownTime, long countDownInterval, String countDownText,
                CountDownTimerObserver observer) {
            newPositiveParams();
            ButtonParams params = mCircleParams.positiveParams;
            params.countDownTime = countDownTime;
            params.countDownInterval = countDownInterval;
            params.countDownText = countDownText;
            mCircleParams.circleListeners.countDownTimerObserver = observer;
            return this;
        }

        /**
         * 是否禁用确定按钮
         *
         * @param disable true禁用
         * @return
         */
        public Builder setPositiveDisable(@NonNull boolean disable) {
            newPositiveParams();
            mCircleParams.positiveParams.disable = disable;
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
        public Builder setNegative(@NonNull String text, OnButtonClickListener listener) {
            newNegativeParams();
            ButtonParams params = mCircleParams.negativeParams;
            params.text = text;
            mCircleParams.circleListeners.clickNegativeListener = listener;
            return this;
        }

        /**
         * 取消按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         * @deprecated
         */
        public Builder setNegative(@NonNull String text, @Nullable final View.OnClickListener listener) {
            return setNegative(text, new OnButtonClickListener() {
                @Override
                public boolean onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    return true;
                }
            });
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
        public Builder setNeutral(@NonNull String text, OnButtonClickListener listener) {
            newNeutralParams();
            ButtonParams params = mCircleParams.neutralParams;
            params.text = text;
            mCircleParams.circleListeners.clickNeutralListener = listener;
            return this;
        }

        /**
         * 中间按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         * @deprecated
         */
        public Builder setNeutral(@NonNull String text, final View.OnClickListener listener) {
            return setNeutral(text, new OnButtonClickListener() {
                @Override
                public boolean onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v);
                    }
                    return true;
                }
            });
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
            mCircleParams.circleListeners.createButtonListener = listener;
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

        /**
         * 刷新对话框
         */
        public void refresh() {
            if (mCircleDialog != null) {
                mCircleDialog.refresh();
            }
        }

        /**
         * 关闭对话框
         */
        public void dismiss() {
            if (mCircleDialog != null) {
                mCircleDialog.dismiss();
            }
        }

        private void newTitleParams() {
            if (mCircleParams.titleParams == null)
                mCircleParams.titleParams = new TitleParams();
        }

        private void newSubTitleParams() {
            if (mCircleParams.subTitleParams == null)
                mCircleParams.subTitleParams = new SubTitleParams();
        }

        private void newTextParams() {
            if (mCircleParams.textParams == null) {
                mCircleParams.textParams = new TextParams();
            }
        }

        private void newItemsParams() {
            if (mCircleParams.itemsParams == null) {
                mCircleParams.itemsParams = new ItemsParams();
            }
        }

        private void newProgressParams() {
            if (mCircleParams.progressParams == null) {
                mCircleParams.progressParams = new ProgressParams();
            }
        }

        private void newInputParams() {
            if (mCircleParams.inputParams == null) {
                mCircleParams.inputParams = new InputParams();
            }
        }

        private void newPositiveParams() {
            if (mCircleParams.positiveParams == null)
                mCircleParams.positiveParams = new ButtonParams();
        }

        private void newLottieParams() {
            if (mCircleParams.lottieParams == null) {
                mCircleParams.lottieParams = new LottieParams();
            }
        }

        private void newNegativeParams() {
            if (mCircleParams.negativeParams == null) {
                mCircleParams.negativeParams = new ButtonParams();
                mCircleParams.negativeParams.textColor = CircleColor.FOOTER_BUTTON_TEXT_NEGATIVE;
            }
        }

        private void newNeutralParams() {
            if (mCircleParams.neutralParams == null)
                mCircleParams.neutralParams = new ButtonParams();
        }

        private void newCloseParams() {
            if (mCircleParams.closeParams == null) {
                mCircleParams.closeParams = new CloseParams();
            }
        }

        private void newAdParams() {
            if (mCircleParams.adParams == null) {
                mCircleParams.adParams = new AdParams();
            }
        }

    }

}
