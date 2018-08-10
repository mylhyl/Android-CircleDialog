package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.res.drawable.TriangleArrowDrawable;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

/**
 * Created by hupei on 2018/8/9 20:59.
 */
class BodyPopupRecyclerView extends BodyItemsRecyclerView {
    private PopupParams mPopupParams;

    public BodyPopupRecyclerView(Context context, PopupParams popupParams, DialogParams dialogParams
            , OnRvItemClickListener listener) {
        super(context);
        this.mPopupParams = popupParams;
        init(context, popupParams, dialogParams, listener);
    }

    @Override
    protected void configBackground() {
        int arrowGravity = mPopupParams.arrowGravity;
        int backgroundColor = mPopupParams.backgroundColor != 0
                ? mPopupParams.backgroundColor : mDialogParams.backgroundColor;
        TriangleArrowDrawable arrowDrawable = new TriangleArrowDrawable(arrowGravity, backgroundColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(arrowDrawable);
        } else {
            setBackgroundDrawable(arrowDrawable);
        }
    }

    @Override
    public View getView() {
        return this;
    }
}
