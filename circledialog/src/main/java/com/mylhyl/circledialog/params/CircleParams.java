package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import com.mylhyl.circledialog.AbsCircleDialog;

import java.io.Serializable;

/**
 * Created by hupei on 2017/3/30.
 */

public final class CircleParams implements Parcelable {
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
