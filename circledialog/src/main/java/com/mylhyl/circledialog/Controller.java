package com.mylhyl.circledialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mylhyl.circledialog.view.BuildViewConfirmImpl;
import com.mylhyl.circledialog.view.BuildViewCustomBodyImpl;
import com.mylhyl.circledialog.view.BuildViewInputImpl;
import com.mylhyl.circledialog.view.BuildViewItemsListViewImpl;
import com.mylhyl.circledialog.view.BuildViewItemsRecyclerViewImpl;
import com.mylhyl.circledialog.view.BuildViewLottieImpl;
import com.mylhyl.circledialog.view.BuildViewPopupImpl;
import com.mylhyl.circledialog.view.BuildViewProgressImpl;
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
    private BaseCircleDialog mDialog;

    public Controller(Context context, CircleParams params, BaseCircleDialog dialog) {
        this.mContext = context;
        this.mParams = params;
        this.mDialog = dialog;
    }

    public void createView() {
        //输入框
        if (mParams.inputParams != null) {
            mCreateView = new BuildViewInputImpl(mContext, mParams);
            mCreateView.buildBodyView();
            InputView inputView = mCreateView.getBodyView();
            ButtonView buttonView = mCreateView.buildButton();
            regNegativeListener(buttonView);
            regNeutralListener(buttonView);
            regPositiveInputListener(buttonView, inputView);
        }
        //popup
        else if (mParams.popupParams != null) {
            SystemBarConfig systemBarConfig = mDialog.getSystemBarConfig();
            mCreateView = new BuildViewPopupImpl(mContext, mDialog, mParams
                    , systemBarConfig.getScreenSize(), systemBarConfig.getStatusBarHeight());
            mCreateView.buildBodyView();
            final ItemsView itemsView = mCreateView.getBodyView();
            itemsView.regOnItemClickListener(new OnRvItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position) {
                    if (mParams.rvItemListener != null) {
                        boolean b = mParams.rvItemListener.onItemClick(view, position);
                        if (b) {
                            mDialog.dismissAllowingStateLoss();
                        }
                    }
                    return false;
                }
            });
        } else {
            //列表
            if (mParams.itemsParams != null) {
                if (mParams.itemListViewType) {
                    mCreateView = new BuildViewItemsListViewImpl(mContext, mParams);
                    mCreateView.buildBodyView();
                    final ItemsView itemsView = mCreateView.getBodyView();
                    itemsView.regOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (mParams.itemListener != null) {
                                boolean b = mParams.itemListener.onItemClick(parent, view, position, id);
                                if (b) {
                                    mDialog.dismissAllowingStateLoss();
                                }
                            }
                        }
                    });
                } else {
                    mCreateView = new BuildViewItemsRecyclerViewImpl(mContext, mParams);
                    mCreateView.buildBodyView();
                    final ItemsView itemsView = mCreateView.getBodyView();
                    itemsView.regOnItemClickListener(new OnRvItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position) {
                            if (mParams.rvItemListener != null) {
                                boolean b = mParams.rvItemListener.onItemClick(view, position);
                                if (b) {
                                    mDialog.dismissAllowingStateLoss();
                                }
                            }
                            return false;
                        }
                    });
                }
            }
            //文本
            else if (mParams.textParams != null) {
                mCreateView = new BuildViewConfirmImpl(mContext, mParams);
                mCreateView.buildBodyView();
            }//进度条
            else if (mParams.progressParams != null) {
                mCreateView = new BuildViewProgressImpl(mContext, mParams);
                mCreateView.buildBodyView();
            }
            //lottie动画框
            else if (mParams.lottieParams != null) {
                mCreateView = new BuildViewLottieImpl(mContext, mParams);
                mCreateView.buildBodyView();
            }
            //自定义内容视图
            else if (mParams.bodyViewId != 0) {
                mCreateView = new BuildViewCustomBodyImpl(mContext, mParams);
                mCreateView.buildBodyView();
                View bodyView = mCreateView.getBodyView();
                if (mParams.createBodyViewListener != null)
                    mParams.createBodyViewListener.onCreateBodyView(bodyView);
            }
            ButtonView buttonView = mCreateView.buildButton();
            regNegativeListener(buttonView);
            regNeutralListener(buttonView);
            regPositiveListener(buttonView);
        }
    }

    private void regNegativeListener(final ButtonView viewButton) {
        viewButton.regNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnClickListener) viewButton).onClick(v, BUTTON_NEGATIVE);
                mDialog.dismissAllowingStateLoss();
            }
        });
    }

    private void regNeutralListener(final ButtonView viewButton) {
        viewButton.regNeutralListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnClickListener) viewButton).onClick(v, BUTTON_NEUTRAL);
                mDialog.dismissAllowingStateLoss();
            }
        });
    }

    private void regPositiveInputListener(final ButtonView viewButton, final InputView inputView) {

        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = inputView.getInput();
                String text = editText.getText().toString();
                if (mParams.inputListener != null) {
                    boolean b = mParams.inputListener.onClick(text, editText);
                    if (b) {
                        mDialog.dismissAllowingStateLoss();
                    }
                }
            }
        });
    }

    private void regPositiveListener(final ButtonView viewButton) {
        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnClickListener) viewButton).onClick(v, BUTTON_POSITIVE);
                mDialog.dismissAllowingStateLoss();
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

    }
}
