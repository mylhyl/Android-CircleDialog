package com.mylhyl.circledialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.mylhyl.circledialog.view.BuildViewConfirmImpl;
import com.mylhyl.circledialog.view.BuildViewCustomBodyImpl;
import com.mylhyl.circledialog.view.BuildViewInputImpl;
import com.mylhyl.circledialog.view.BuildViewItemsListViewImpl;
import com.mylhyl.circledialog.view.BuildViewItemsRecyclerViewImpl;
import com.mylhyl.circledialog.view.BuildViewLottieImpl;
import com.mylhyl.circledialog.view.BuildViewPopupImpl;
import com.mylhyl.circledialog.view.BuildViewProgressImpl;
import com.mylhyl.circledialog.view.listener.ButtonView;
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

    public Controller(Context context, CircleParams params, BaseCircleDialog dialog) {
        this.mContext = context;
        this.mParams = params;
        this.mDialog = dialog;
        mHandler = new ButtonHandler();
    }

    public void createView() {
        //popup
        if (mParams.popupParams != null) {
            SystemBarConfig systemBarConfig = mDialog.getSystemBarConfig();
            mCreateView = new BuildViewPopupImpl(mContext, mDialog, mParams
                    , systemBarConfig.getScreenSize(), systemBarConfig.getStatusBarHeight());
            mCreateView.buildBodyView();
            final ItemsView itemsView = mCreateView.getBodyView();
            itemsView.regOnItemClickListener(new OnRvItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mHandler.obtainMessage(position, itemsView).sendToTarget();
                    if (!mParams.popupParams.isManualClose)
                        mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                }
            });
        }
        //列表
        else if (mParams.itemsParams != null) {
            if (mParams.itemListViewType) {
                mCreateView = new BuildViewItemsListViewImpl(mContext, mParams);
                mCreateView.buildBodyView();
                final ItemsView itemsView = mCreateView.getBodyView();
                itemsView.regOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mHandler.obtainMessage(position, itemsView).sendToTarget();
                        if (!mParams.itemsParams.isManualClose)
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                    }
                });
            } else {
                mCreateView = new BuildViewItemsRecyclerViewImpl(mContext, mParams);
                mCreateView.buildBodyView();
                final ItemsView itemsView = mCreateView.getBodyView();
                itemsView.regOnItemClickListener(new OnRvItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mHandler.obtainMessage(position, itemsView).sendToTarget();
                        if (!mParams.itemsParams.isManualClose)
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                    }
                });
            }
            final ButtonView itemsButton = mCreateView.buildButton();
            applyButton(itemsButton, null);
        } else {
            //文本
            if (mParams.textParams != null) {
                mCreateView = new BuildViewConfirmImpl(mContext, mParams);
                mCreateView.buildBodyView();
                ButtonView buttonView = mCreateView.buildButton();
                applyButton(buttonView, null);
            }//进度条
            else if (mParams.progressParams != null) {
                mCreateView = new BuildViewProgressImpl(mContext, mParams);
                mCreateView.buildBodyView();
                ButtonView buttonView = mCreateView.buildButton();
                applyButton(buttonView, null);
            }
            //输入框
            else if (mParams.inputParams != null) {
                mCreateView = new BuildViewInputImpl(mContext, mDialog, mParams);
                mCreateView.buildBodyView();
                View inputView = mCreateView.getBodyView();
                ButtonView buttonView = mCreateView.buildButton();
                applyButton(buttonView, inputView);
            }
            //lottie动画框
            else if (mParams.lottieParams != null) {
                mCreateView = new BuildViewLottieImpl(mContext, mParams);
                mCreateView.buildBodyView();
                ButtonView buttonView = mCreateView.buildButton();
                applyButton(buttonView, null);
            }
            //自定义内容视图
            else if (mParams.bodyViewId != 0) {
                mCreateView = new BuildViewCustomBodyImpl(mContext, mParams);
                mCreateView.buildBodyView();
                View bodyView = mCreateView.getBodyView();
                ButtonView buttonView = mCreateView.buildButton();
                applyButton(buttonView, null);
                if (mParams.createBodyViewListener != null)
                    mParams.createBodyViewListener.onCreateBodyView(bodyView);
            }
        }
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
                if (mParams.inputParams == null) {
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
        mCreateView.refreshContent();
        mCreateView.refreshButton();
        //刷新时带动画
        if (mParams.dialogParams.refreshAnimation != 0 && getView() != null)
            getView().post(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(mContext, mParams
                            .dialogParams
                            .refreshAnimation);
                    if (animation != null) {
                        getView().startAnimation(animation);
                    }
                }
            });
    }

    View getView() {
        return mCreateView.getRootView();
    }

    public interface OnClickListener {
        /**
         * dialog中可以点击的空间需要继承的接口，通过这个接口调用各自的监听事件
         *
         * @param view  实现了OnClickListener的view
         * @param which 点击事件对应的id，如果是列表中的item 则是对应的下标
         */
        void onClick(View view, int which);
    }

    public interface OnDialogInternalListener {
        void dialogAtLocation(int x, int y);

        void onDismiss();
    }

    static class ButtonHandler extends Handler {
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
                    ((AbsBaseCircleDialog) msg.obj).dismissAllowingStateLoss();
                    break;
                default:
                    ((OnClickListener) msg.obj).onClick((View) msg.obj, msg.what);
                    break;
            }
        }
    }
}
