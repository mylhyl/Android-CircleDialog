package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.res.drawable.TriangleArrowDrawable;
import com.mylhyl.circledialog.scale.ScaleLayoutConfig;

/**
 * Created by hupei on 2018/8/9 20:59.
 */
class BodyPopupRecyclerView extends BodyItemsRecyclerView {

    public BodyPopupRecyclerView(Context context, CircleParams params) {
        super(context);
        ScaleLayoutConfig.init(context.getApplicationContext());
        this.mParams = params;
        init(params);
    }

    @Override
    public void init(CircleParams params) {
        PopupParams popupParams = params.popupParams;
        int arrowGravity = popupParams.arrowGravity;
        int backgroundColor = popupParams.backgroundColor != 0
                ? popupParams.backgroundColor : params.dialogParams.backgroundColor;
        TriangleArrowDrawable arrowDrawable = new TriangleArrowDrawable(arrowGravity, backgroundColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(arrowDrawable);
        } else {
            setBackgroundDrawable(arrowDrawable);
        }
        createLayoutManager(popupParams);
        createItemDecoration(popupParams);
        createAdapter(mContext, popupParams, params.dialogParams);
    }

    @Override
    public View getView() {
        return this;
    }
}
