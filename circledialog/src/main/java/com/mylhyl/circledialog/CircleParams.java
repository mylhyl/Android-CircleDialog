package com.mylhyl.circledialog;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;

import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

/**
 * Created by hupei on 2017/3/30.
 */

public class CircleParams implements Parcelable {
    public void dismiss() {
    }

    /**
     * 确定按钮点击事件
     */
    public View.OnClickListener clickPositiveListener;

    /**
     * 取消按钮点击事件
     */
    public View.OnClickListener clickNegativeListener;

    /**
     * 输入框确定事件
     */
    public OnInputClickListener inputListener;

    /**
     * item点击事件
     */
    public AdapterView.OnItemClickListener itemListener;

    public DialogFragment dialogFragment;
    public DialogParams dialogParams;
    public TitleParams titleParams;
    public TextParams textParams;
    public ButtonParams negativeParams;
    public ButtonParams positiveParams;
    public ItemsParams itemsParams;
    public ProgressParams progressParams;
    public InputParams inputParams;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dialogParams, flags);
        dest.writeParcelable(this.titleParams, flags);
        dest.writeParcelable(this.textParams, flags);
        dest.writeParcelable(this.negativeParams, flags);
        dest.writeParcelable(this.positiveParams, flags);
        dest.writeParcelable(this.itemsParams, flags);
        dest.writeParcelable(this.progressParams, flags);
        dest.writeParcelable(this.inputParams, flags);
    }

    public CircleParams() {
    }

    protected CircleParams(Parcel in) {
        this.dialogParams = in.readParcelable(DialogParams.class.getClassLoader());
        this.titleParams = in.readParcelable(TitleParams.class.getClassLoader());
        this.textParams = in.readParcelable(TextParams.class.getClassLoader());
        this.negativeParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.positiveParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.itemsParams = in.readParcelable(ItemsParams.class.getClassLoader());
        this.progressParams = in.readParcelable(ProgressParams.class.getClassLoader());
        this.inputParams = in.readParcelable(InputParams.class.getClassLoader());
    }

    public static final Parcelable.Creator<CircleParams> CREATOR = new Parcelable.Creator<CircleParams>() {
        @Override
        public CircleParams createFromParcel(Parcel source) {
            return new CircleParams(source);
        }

        @Override
        public CircleParams[] newArray(int size) {
            return new CircleParams[size];
        }
    };
}
