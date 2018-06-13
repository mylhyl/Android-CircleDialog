package com.mylhyl.circledialog;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;

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
    public AdapterView.OnItemClickListener itemListener;
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
    public InputParams inputParams;
    public ButtonParams neutralParams;
    public View progressView;

    public CircleParams() {
    }

    protected CircleParams(Parcel in) {
        this.clickPositiveListener = (View.OnClickListener) in.readValue(View.OnClickListener.class.getClassLoader());
        this.clickNeutralListener = (View.OnClickListener) in.readValue(View.OnClickListener.class.getClassLoader());
        this.clickNegativeListener = (View.OnClickListener) in.readValue(View.OnClickListener.class.getClassLoader());
        this.inputListener = (OnInputClickListener) in.readValue(OnInputClickListener.class.getClassLoader());
        this.rvItemListener = (OnRvItemClickListener) in.readValue(OnRvItemClickListener.class.getClassLoader());
        this.itemListener = (AdapterView.OnItemClickListener) in.readValue(AdapterView.OnItemClickListener.class.getClassLoader());
        this.dismissListener = (DialogInterface.OnDismissListener) in.readValue(DialogInterface.OnDismissListener.class.getClassLoader());
        this.cancelListener = (DialogInterface.OnCancelListener) in.readValue(DialogInterface.OnCancelListener.class.getClassLoader());
        this.showListener = (DialogInterface.OnShowListener) in.readValue(DialogInterface.OnShowListener.class.getClassLoader());
        this.dialogParams = in.readParcelable(DialogParams.class.getClassLoader());
        this.titleParams = in.readParcelable(TitleParams.class.getClassLoader());
        this.subTitleParams = in.readParcelable(SubTitleParams.class.getClassLoader());
        this.textParams = in.readParcelable(TextParams.class.getClassLoader());
        this.negativeParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.positiveParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.itemsParams = in.readParcelable(ItemsParams.class.getClassLoader());
        this.progressParams = in.readParcelable(ProgressParams.class.getClassLoader());
        this.inputParams = in.readParcelable(InputParams.class.getClassLoader());
        this.neutralParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.progressView = (View) in.readValue(View.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.clickPositiveListener);
        dest.writeValue(this.clickNeutralListener);
        dest.writeValue(this.clickNegativeListener);
        dest.writeValue(this.inputListener);
        dest.writeValue(this.rvItemListener);
        dest.writeValue(this.itemListener);
        dest.writeValue(this.dismissListener);
        dest.writeValue(this.cancelListener);
        dest.writeValue(this.showListener);
        dest.writeParcelable(this.dialogParams, flags);
        dest.writeParcelable(this.titleParams, flags);
        dest.writeParcelable(this.subTitleParams, flags);
        dest.writeParcelable(this.textParams, flags);
        dest.writeParcelable(this.negativeParams, flags);
        dest.writeParcelable(this.positiveParams, flags);
        dest.writeParcelable(this.itemsParams, flags);
        dest.writeParcelable(this.progressParams, flags);
        dest.writeParcelable(this.inputParams, flags);
        dest.writeParcelable(this.neutralParams, flags);
        dest.writeValue(this.progressView);
    }
}
