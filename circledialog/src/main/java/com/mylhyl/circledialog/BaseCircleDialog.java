package com.mylhyl.circledialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.params.DialogParams;

import java.lang.reflect.Field;

/**
 * Created by hupei on 2017/3/29.
 */

public final class BaseCircleDialog extends AbsBaseCircleDialog implements DialogInterface.OnShowListener,
        Controller.OnDialogInternalListener {

    private static final String CIRCLE_DIALOG = "circleDialog";
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mParams = savedInstanceState.getParcelable(SAVED_PARAMS);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mParams != null) {
            if (mParams.circleListeners.dismissListener != null) {
                mParams.circleListeners.dismissListener.onDismiss(dialog);
            }
            if (mParams.circleListeners.cancelListener != null) {
                mParams.circleListeners.cancelListener.onCancel(dialog);
            }
        }
        //mParams = null;
        mController = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_PARAMS, mParams);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DialogParams dialogParams = mParams.dialogParams;
        setGravity(dialogParams.gravity);
        setCanceledOnTouchOutside(dialogParams.canceledOnTouchOutside);
        setCancelable(dialogParams.cancelable);
        setWidth(dialogParams.width);
        setMaxHeight(dialogParams.maxHeight);
        int[] padding = dialogParams.mPadding;
        if (padding != null) {
            setPadding(padding[0], padding[1], padding[2], padding[3]);
        }
        setAnimations(dialogParams.animStyle);
        setDimEnabled(dialogParams.isDimEnabled);
        setDimAmount(dialogParams.dimAmount);
        setRadius(dialogParams.radius);
        setAlpha(dialogParams.alpha);
        setX(dialogParams.xOff);
        setY(dialogParams.yOff);
        if (mParams != null && mParams.inputParams != null &&
                mParams.inputParams.showSoftKeyboard && mController != null) {
            setSoftInputMode();
        }
        setSystemUiVisibility(dialogParams.systemUiVisibility);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        mController = new Controller(context.getApplicationContext(), mParams, this);
        return mController.createView();
    }

    public void show(FragmentManager manager) {
        show(manager, CIRCLE_DIALOG);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            Class<?> superclass = getClass().getSuperclass().getSuperclass();
            Field dismissedField = superclass.getDeclaredField("mDismissed");
            dismissedField.setAccessible(true);
            dismissedField.set(this, false);

            Field shownByMeField = superclass.getDeclaredField("mShownByMe");
            shownByMeField.setAccessible(true);
            shownByMeField.set(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        if (isVisible() && isAdded()) {
            transaction.remove(this).commitAllowingStateLoss();
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(this, tag);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        dialogDismiss();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        if (mParams == null) {
            return;
        }
        dialog.setOnShowListener(this);
        dialog.setOnKeyListener(mParams.circleListeners.keyListener);
        if (mParams.circleListeners.createBodyViewListener != null) {
            mParams.circleListeners.createBodyViewListener.onCreateBodyView(mController.getViewHolder());
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mParams == null) {
            return;
        }
        if (mParams.circleListeners.showListener != null) {
            mParams.circleListeners.showListener.onShow(dialog, mController.getViewHolder());
        }
        if (mParams.popupParams != null && mParams.dialogParams.width != 0) {
            resizeSize();
        }
    }

    void resizeSize() {
        Dialog dialog = getDialog();
        if (dialog == null) return;
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        int avg = getSystemBarConfig().getScreenWidth() / 3;
        final float absoluteWidth = mParams.dialogParams.width;
        wlp.width = absoluteWidth > avg ? (int) absoluteWidth : avg;
        window.setAttributes(wlp);
    }

    public void refreshView() {
        if (mController != null) {
            mController.refreshView();
        }
    }

    @Override
    public void dialogAtLocation(int x, int y) {
        Dialog dialog = getDialog();
        if (dialog == null) return;

        setX(x);
        setY(y);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.x = x;
        wlp.y = y;
        window.setAttributes(wlp);
    }

    @Override
    public void dialogDismiss() {
        dismissAllowingStateLoss();
    }

    @Override
    public int[] getScreenSize() {
        return getSystemBarConfig().getScreenSize();
    }

    @Override
    public int getStatusBarHeight() {
        return getSystemBarConfig().getStatusBarHeight();
    }
}
