package com.mylhyl.circledialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.mylhyl.circledialog.view.BuildViewImpl;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.ItemsView;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

/**
 * Created by hupei on 2017/3/29.
 */

public class Controller {
    /**
     * The identifier for the positive button.
     */
    public static final int BUTTON_POSITIVE = -2;
    /**
     * The identifier for the negative button.
     */
    public static final int BUTTON_NEGATIVE = -3;
    /**
     * The identifier for the neutral button.
     */
    public static final int BUTTON_NEUTRAL = -4;
    private static final int MSG_DISMISS_DIALOG = -1;
    private Context mContext;
    private CircleParams mParams;
    private BuildView mCreateView;
    private ButtonHandler mHandler;
    private BaseCircleDialog mDialog;

    public Controller(Context context, CircleParams params, BaseCircleDialog mDialog) {
        this.mContext = context;
        this.mParams = params;
        this.mDialog = mDialog;
        mHandler = new ButtonHandler();
        mCreateView = new BuildViewImpl(mContext, mParams);
    }

    public View createView() {
        applyRoot();
        applyHeader();
        applyBody();
        return getView();
    }

    private void applyRoot() {
        mCreateView.buildRoot();
    }

    private void applyHeader() {
        if (mParams.titleParams != null)
            mCreateView.buildTitle();
    }

    private void applyBody() {
        //自定义内容视图
        if (mParams.bodyViewId != 0) {
            View bodyView = mCreateView.buildCustomBodyView();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
            if (mParams.createBodyViewListener != null)
                mParams.createBodyViewListener.onCreateBodyView(bodyView);
        }
        //文本
        else if (mParams.textParams != null) {
            mCreateView.buildText();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
        }
        //列表
        else if (mParams.itemsParams != null) {
            final ItemsView itemsView = mCreateView.buildItems();
            if (mParams.itemListener != null) {
                itemsView.regOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mHandler.obtainMessage(position, itemsView).sendToTarget();
                        if (!mParams.itemsParams.isManualClose)
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                    }
                });
            } else if (mParams.rvItemListener != null) {
                itemsView.regOnItemClickListener(new OnRvItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mHandler.obtainMessage(position, itemsView).sendToTarget();
                        if (!mParams.itemsParams.isManualClose)
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                    }
                });
            }
            final ButtonView itemsButton = mCreateView.buildItemsButton();
            applyButton(itemsButton, null);
        }
        //进度条
        else if (mParams.progressParams != null) {
            mCreateView.buildProgress();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
        }
        //输入框
        else if (mParams.inputParams != null) {
            InputView inputView = mCreateView.buildInput();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, (View) inputView);
        }
        //lottie动画框
        else if (mParams.lottieParams != null) {
            mCreateView.buildLottie();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
        }
    }

    private View getView() {
        return mCreateView.getView();
    }

    private void applyButton(final ButtonView viewButton, final View viewClick) {

        viewButton.regNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(BUTTON_NEGATIVE, viewClick == null
                        ? viewButton : viewClick).sendToTarget();
                mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                        .sendToTarget();
            }
        });
        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(BUTTON_POSITIVE, viewClick == null
                        ? viewButton : viewClick).sendToTarget();
                if (mParams.inputParams == null || !mParams.inputParams.isManualClose) {
                    mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                }
            }
        });
        viewButton.regNeutralListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(BUTTON_NEUTRAL, viewClick == null
                        ? viewButton : viewClick).sendToTarget();
                mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                        .sendToTarget();
            }
        });
    }

    public void refreshView() {
        mCreateView.refreshText();
        mCreateView.refreshItems();
        mCreateView.refreshProgress();
        mCreateView.refreshMultipleButtonText();
        //刷新时带动画
        if (mParams.dialogParams.refreshAnimation != 0 && getView() != null)
            getView().post(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(mContext, mParams
                            .dialogParams
                            .refreshAnimation);
                    if (animation != null) getView().startAnimation(animation);
                }
            });
    }

    /**
     * Interface used to allow the creator of a dialog to run some code when an
     * item on the dialog is clicked..
     */
    public interface OnClickListener {
        /**
         * dialog中可以点击的空间需要继承的接口，通过这个接口调用各自的监听事件
         *
         * @param view  实现了OnClickListener的view
         * @param which 点击事件对应的id，如果是列表中的item 则是对应的下标
         */
        void onClick(View view, int which);
    }

    public static class ButtonHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BUTTON_POSITIVE:
                case BUTTON_NEGATIVE:
                case BUTTON_NEUTRAL:
                    ((OnClickListener) msg.obj).onClick((View) msg.obj, msg.what);
                    break;

                case MSG_DISMISS_DIALOG:
                    ((BaseCircleDialog) msg.obj).dismiss();
                    break;
                default:
                    ((OnClickListener) msg.obj).onClick((View) msg.obj, msg.what);
                    break;
            }
        }
    }
}
