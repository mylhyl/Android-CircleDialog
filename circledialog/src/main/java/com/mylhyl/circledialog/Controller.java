package com.mylhyl.circledialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.mylhyl.circledialog.view.BodyInputView;
import com.mylhyl.circledialog.view.BodyItemsView;
import com.mylhyl.circledialog.view.BuildViewImpl;
import com.mylhyl.circledialog.view.ItemsButton;
import com.mylhyl.circledialog.view.MultipleButton;
import com.mylhyl.circledialog.view.SingleButton;

/**
 * Created by hupei on 2017/3/29.
 */

public class Controller {
    private Context mContext;
    private CircleParams mParams;
    private BuildView mCreateView;
    private ButtonHandler mHandler;

    private static final int MSG_DISMISS_DIALOG = -1;
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

    private BaseCircleDialog mDialog;

    public static class ButtonHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BUTTON_POSITIVE:
                case BUTTON_NEGATIVE:
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

    public void refreshView() {
        mCreateView.refreshText();
        mCreateView.refreshItems();
        mCreateView.refreshProgress();
        mCreateView.refreshMultipleButtonText();
        mCreateView.refreshSingleButtonText();
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

    private void applyRoot() {
        mCreateView.buildRoot();
    }

    private void applyHeader() {
        if (mParams.titleParams != null)
            mCreateView.buildTitle();
    }

    private void applyBody() {
        //文本
        if (mParams.textParams != null) {
            mCreateView.buildText();
            applyButton();
        }
        //列表
        else if (mParams.itemsParams != null) {
            final BodyItemsView bodyItemsView = mCreateView.buildItems();
            bodyItemsView.regOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mHandler.obtainMessage(position, bodyItemsView).sendToTarget();
                    mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                            .sendToTarget();
                }
            });
            //有确定或者有取消按钮
            if (mParams.positiveParams != null || mParams.negativeParams != null) {
                final ItemsButton itemsButton = mCreateView.buildItemsButton();
                itemsButton.regOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHandler.obtainMessage(mParams.positiveParams != null ? BUTTON_POSITIVE : BUTTON_NEGATIVE, itemsButton).sendToTarget();
                        mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                                .sendToTarget();
                    }
                });
            }
        }
        //进度条
        else if (mParams.progressParams != null) {
            mCreateView.buildProgress();
            applyButton();
        }
        //输入框
        else if (mParams.inputParams != null) {
            final BodyInputView bodyInputView = mCreateView.buildInput();
            //有确定并且有取消按钮
            if (mParams.positiveParams != null && mParams.negativeParams != null) {
                final MultipleButton multipleButton = mCreateView.buildMultipleButton();
                multipleButton.regNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHandler.obtainMessage(BUTTON_NEGATIVE, bodyInputView).sendToTarget();
                        mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                                .sendToTarget();
                    }
                });
                multipleButton.regPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(bodyInputView.getInput().getText())) {
                            mHandler.obtainMessage(BUTTON_POSITIVE, bodyInputView).sendToTarget();
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                                    .sendToTarget();
                        }

                    }
                });
            }//有确定或者有取消按钮
            else if (mParams.positiveParams != null || mParams.negativeParams != null) {
                final SingleButton singleButton = mCreateView.buildSingleButton();
                singleButton.regOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(bodyInputView.getInput().getText())) {
                            mHandler.obtainMessage(mParams.positiveParams != null ? BUTTON_POSITIVE : BUTTON_NEGATIVE, bodyInputView).sendToTarget();
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                                    .sendToTarget();
                        }
                    }
                });
            }
        }
    }

    private void applyButton() {
        //有确定并且有取消按钮
        if (mParams.positiveParams != null && mParams.negativeParams != null) {
            final MultipleButton multipleButton = mCreateView.buildMultipleButton();
            multipleButton.regNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.obtainMessage(BUTTON_NEGATIVE, multipleButton).sendToTarget();
                    mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                            .sendToTarget();
                }
            });
            multipleButton.regPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.obtainMessage(BUTTON_POSITIVE, multipleButton).sendToTarget();
                    mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                            .sendToTarget();
                }
            });
        }//有确定或者有取消按钮
        else if (mParams.positiveParams != null || mParams.negativeParams != null) {
            final SingleButton singleButton = mCreateView.buildSingleButton();
            singleButton.regOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.obtainMessage(mParams.positiveParams != null ? BUTTON_POSITIVE : BUTTON_NEGATIVE, singleButton).sendToTarget();
                    mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                            .sendToTarget();
                }
            });
        }
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

    private View getView() {
        return mCreateView.getView();
    }
}
