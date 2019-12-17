package com.mylhyl.circledialog.internal;

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
    public PopupParams popupParams;
    public boolean itemListViewType;//true=ListView; false=RecyclerView
    public CloseParams closeParams;
    public AdParams adParams;

    public View bodyView;
    public ImageLoadEngine imageLoadEngine;
    public CircleListeners circleListeners = new CircleListeners();

    public CircleParams() {

    }

    protected CircleParams(Parcel in) {
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
        this.popupParams = in.readParcelable(PopupParams.class.getClassLoader());
        this.itemListViewType = in.readByte() != 0;
        this.closeParams = in.readParcelable(CloseParams.class.getClassLoader());
        this.adParams = in.readParcelable(AdParams.class.getClassLoader());
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
