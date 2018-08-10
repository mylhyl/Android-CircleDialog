package com.mylhyl.circledialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.scale.ScaleLayoutConfig;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BaseCircleDialog extends AbsBaseCircleDialog implements DialogInterface.OnShowListener {
    private static final String SAVED_PARAMS = "circle:params";
    private CircleParams mParams;
    private Controller mController;

    public BaseCircleDialog() {
    }

    public static BaseCircleDialog newAbsCircleDialog(CircleParams params) {
        BaseCircleDialog circleDialog = new BaseCircleDialog();
        circleDialog.mParams = params;
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_PARAMS, params);
        circleDialog.setArguments(bundle);
        return circleDialog;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        mController = new Controller(getContext().getApplicationContext(), mParams, this);
        mController.createView();
        View view = mController.getView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mParams != null && mParams.inputParams != null && mParams.inputParams.showSoftKeyboard
                && mController != null) {
            setSoftInputMode();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScaleLayoutConfig.init(getContext().getApplicationContext());
        if (savedInstanceState != null) {
            mParams = savedInstanceState.getParcelable(SAVED_PARAMS);
        }
        DialogParams dialogParams = mParams.dialogParams;
        setGravity(dialogParams.gravity);
        setCanceledOnTouchOutside(dialogParams.canceledOnTouchOutside);
        setCanceledBack(dialogParams.cancelable);
        setWidth(dialogParams.width);
        setMaxHeight(dialogParams.maxHeight);
        int[] padding = dialogParams.mPadding;
        if (padding != null) {
            setPadding(padding[0], padding[1], padding[2], padding[3]);
        }
        setAnimations(dialogParams.animStyle);
        setDimEnabled(dialogParams.isDimEnabled);
        setRadius(dialogParams.radius);
        setAlpha(dialogParams.alpha);
        setX(dialogParams.xOff);
        setY(dialogParams.yOff);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mParams != null && mParams.dismissListener != null) {
            mParams.dismissListener.onDismiss(dialog);
        }
        if (mParams != null && mParams.cancelListener != null) {
            mParams.cancelListener.onCancel(dialog);
        }
        mParams = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_PARAMS, mParams);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (isAdded()) {
            transaction.remove(this).commit();
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(this, tag);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setOnShowListener(this);
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mParams != null && mParams.showListener != null) {
            mParams.showListener.onShow(dialog);
        }
    }

    public void refreshView() {
        mController.refreshView();
    }
}
