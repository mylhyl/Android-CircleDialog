package com.mylhyl.circledialog;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

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

/**
 * Created by hupei on 2017/3/30.
 */

public class CircleParams implements Parcelable {

    public static final Creator<CircleParams> CREATOR = new Creator<CircleParams>() {
        @Override
        public CircleParams createFromParcel(Parcel source) {
            return new CircleParams(source);
        }

        @Override
        public CircleParams[] newArray(int size) {
            return new CircleParams[size];
        }
    };
    /**
     * 确定按钮点击事件
     */
    public View.OnClickListener clickPositiveListener;
    /**
     * 中间按钮点击事件
     */
    public View.OnClickListener clickNeutralListener;
    /**
     * 取消按钮点击事件
     */
    public View.OnClickListener clickNegativeListener;
    /**
     * 输入框确定事件
     */
    public OnInputClickListener inputListener;
    /**
     * RecyclerView Item点击事件
     */
    public OnRvItemClickListener rvItemListener;
    /**
     * item 点击事件
     */
    public OnLvItemClickListener itemListener;
    /**
     * dialog 关闭事件
     */
    public DialogInterface.OnDismissListener dismissListener;
    /**
     * dialog 取消事件
     */
    public DialogInterface.OnCancelListener cancelListener;
    /**
     * dialog 显示事件
     */
    public DialogInterface.OnShowListener showListener;
    public DialogParams dialogParams;
    public TitleParams titleParams;
    public SubTitleParams subTitleParams;
    public TextParams textParams;
    public ButtonParams negativeParams;
    public ButtonParams positiveParams;
    public ItemsParams itemsParams;
    public ProgressParams progressParams;
    public LottieParams lottieParams;
    public InputParams inputParams;
    public ButtonParams neutralParams;
    public int bodyViewId;
    public OnCreateBodyViewListener createBodyViewListener;
    public OnCreateProgressListener createProgressListener;
    public OnCreateLottieListener createLottieListener;
    public OnCreateTitleListener createTitleListener;
    public OnCreateTextListener createTextListener;
    public OnCreateInputListener createInputListener;
    public OnCreateButtonListener createButtonListener;
    public OnInputCounterChangeListener inputCounterChangeListener;
    public PopupParams popupParams;
    public boolean itemListViewType;//true=ListView; false=RecyclerView
    public CloseParams closeParams;
    public AdParams adParams;
    public ImageLoadEngine imageLoadEngine;
    public OnAdItemClickListener adItemClickListener;

    public CircleParams() {
    }

    protected CircleParams(Parcel in) {
        this.clickPositiveListener = in.readParcelable(View.OnClickListener.class.getClassLoader());
        this.clickNeutralListener = in.readParcelable(View.OnClickListener.class.getClassLoader());
        this.clickNegativeListener = in.readParcelable(View.OnClickListener.class.getClassLoader());
        this.inputListener = in.readParcelable(OnInputClickListener.class.getClassLoader());
        this.rvItemListener = in.readParcelable(OnRvItemClickListener.class.getClassLoader());
        this.itemListener = in.readParcelable(OnLvItemClickListener.class.getClassLoader());
        this.dismissListener = in.readParcelable(DialogInterface.OnDismissListener.class.getClassLoader());
        this.cancelListener = in.readParcelable(DialogInterface.OnCancelListener.class.getClassLoader());
        this.showListener = in.readParcelable(DialogInterface.OnShowListener.class.getClassLoader());
        this.dialogParams = in.readParcelable(DialogParams.class.getClassLoader());
        this.titleParams = in.readParcelable(TitleParams.class.getClassLoader());
        this.subTitleParams = in.readParcelable(SubTitleParams.class.getClassLoader());
        this.textParams = in.readParcelable(TextParams.class.getClassLoader());
        this.negativeParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.positiveParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.itemsParams = in.readParcelable(ItemsParams.class.getClassLoader());
        this.progressParams = in.readParcelable(ProgressParams.class.getClassLoader());
        this.lottieParams = in.readParcelable(LottieParams.class.getClassLoader());
        this.inputParams = in.readParcelable(InputParams.class.getClassLoader());
        this.neutralParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.bodyViewId = in.readInt();
        this.createBodyViewListener = in.readParcelable(OnCreateBodyViewListener.class.getClassLoader());
        this.createProgressListener = in.readParcelable(OnCreateProgressListener.class.getClassLoader());
        this.createLottieListener = in.readParcelable(OnCreateLottieListener.class.getClassLoader());
        this.createTitleListener = in.readParcelable(OnCreateTitleListener.class.getClassLoader());
        this.createTextListener = in.readParcelable(OnCreateTextListener.class.getClassLoader());
        this.createInputListener = in.readParcelable(OnCreateInputListener.class.getClassLoader());
        this.createButtonListener = in.readParcelable(OnCreateButtonListener.class.getClassLoader());
        this.inputCounterChangeListener = in.readParcelable(OnInputCounterChangeListener.class.getClassLoader());
        this.popupParams = in.readParcelable(PopupParams.class.getClassLoader());
        this.itemListViewType = in.readByte() != 0;
        this.closeParams = in.readParcelable(CloseParams.class.getClassLoader());
        this.adParams = in.readParcelable(AdParams.class.getClassLoader());
        this.imageLoadEngine = in.readParcelable(ImageLoadEngine.class.getClassLoader());
        this.adItemClickListener = in.readParcelable(OnAdItemClickListener.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dialogParams, flags);
        dest.writeParcelable(this.titleParams, flags);
        dest.writeParcelable(this.subTitleParams, flags);
        dest.writeParcelable(this.textParams, flags);
        dest.writeParcelable(this.negativeParams, flags);
        dest.writeParcelable(this.positiveParams, flags);
        dest.writeParcelable(this.itemsParams, flags);
        dest.writeParcelable(this.progressParams, flags);
        dest.writeParcelable(this.lottieParams, flags);
        dest.writeParcelable(this.inputParams, flags);
        dest.writeParcelable(this.neutralParams, flags);
        dest.writeInt(this.bodyViewId);
        dest.writeParcelable(this.popupParams, flags);
        dest.writeByte(this.itemListViewType ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.closeParams, flags);
        dest.writeParcelable(this.adParams, flags);
    }
}
