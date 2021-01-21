package com.mylhyl.circledialog.internal;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mylhyl.circledialog.CircleViewHolder;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.view.BuildViewAdImpl;
import com.mylhyl.circledialog.view.BuildViewConfirmImpl;
import com.mylhyl.circledialog.view.BuildViewCustomBodyImpl;
import com.mylhyl.circledialog.view.BuildViewInputImpl;
import com.mylhyl.circledialog.view.BuildViewItemsListViewImpl;
import com.mylhyl.circledialog.view.BuildViewItemsRecyclerViewImpl;
import com.mylhyl.circledialog.view.BuildViewLottieImpl;
import com.mylhyl.circledialog.view.BuildViewProgressImpl;
import com.mylhyl.circledialog.view.listener.AdView;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.CloseView;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.ItemsView;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

/**
 * Created by hupei on 2017/3/29.
 */
public final class Controller {

    public static final boolean SDK_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    public static final boolean SDK_JELLY_BEAN = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;

    private CircleViewHolder mCircleViewHolder;
    private Context mContext;
    private CircleParams mParams;
    private BuildView mCreateView;
    private OnDialogInternalListener mOnDialogListener;

    public Controller(Context context, CircleParams params, OnDialogInternalListener dialogListener) {
        this.mContext = context;
        this.mParams = params;
        this.mOnDialogListener = dialogListener;
    }

    public static int dp2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public CircleViewHolder getViewHolder() {
        return mCircleViewHolder;
    }

    public View createView() {

        // lottie动画框
        if (mParams.lottieParams != null) {
            mCreateView = new BuildViewLottieImpl(mContext, mParams);
            mCreateView.buildBodyView();
        }
        // 自定义内容视图
        else if (mParams.bodyViewId != 0 || mParams.bodyView != null) {
            mCreateView = new BuildViewCustomBodyImpl(mContext, mParams);
            mCreateView.buildBodyView();
        }
        // 广告
        else if (mParams.adParams != null) {
            mCreateView = new BuildViewAdImpl(mContext, mParams);
            mCreateView.buildBodyView();
            AdView bodyView = mCreateView.getBodyView();
            bodyView.regOnImageClickListener(new OnAdItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position) {
                    if (mParams.circleListeners.adItemClickListener == null) {
                        return false;
                    }
                    boolean b = mParams.circleListeners.adItemClickListener.onItemClick(view, position);
                    if (b && !mParams.dialogParams.manualClose) {
                        mOnDialogListener.dialogDismiss();
                    }
                    return false;
                }
            });
        }
        // 列表
        else if (mParams.itemsParams != null) {
            //设置列表特殊的参数
            DialogParams dialogParams = mParams.dialogParams;
            // hupei 2019/5/30 since 4.0.2修复 设置 dialogParams.gravity 无效的bug
            if (dialogParams.gravity == Gravity.NO_GRAVITY) {
                dialogParams.gravity = Gravity.BOTTOM;//默认底部显示
            }
            //判断是否已经设置过
            if (dialogParams.gravity == Gravity.BOTTOM && dialogParams.yOff == -1) {
                dialogParams.yOff = 20;//底部与屏幕的距离
            }
            if (mParams.itemListViewType) {
                mCreateView = new BuildViewItemsListViewImpl(mContext, mParams);
                mCreateView.buildBodyView();
                ItemsView itemsView = mCreateView.getBodyView();
                itemsView.regOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mParams.circleListeners.itemListener == null) {
                            return;
                        }
                        boolean b = mParams.circleListeners.itemListener.onItemClick(parent, view, position, id);
                        if (b && !mParams.dialogParams.manualClose) {
                            mOnDialogListener.dialogDismiss();
                        }
                    }
                });
            } else {
                mCreateView = new BuildViewItemsRecyclerViewImpl(mContext, mParams);
                mCreateView.buildBodyView();
                ItemsView itemsView = mCreateView.getBodyView();
                itemsView.regOnItemClickListener(new OnRvItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position) {
                        if (mParams.circleListeners.rvItemListener == null) {
                            return false;
                        }
                        boolean b = mParams.circleListeners.rvItemListener.onItemClick(view, position);
                        if (b && !mParams.dialogParams.manualClose) {
                            mOnDialogListener.dialogDismiss();
                        }
                        return false;
                    }
                });
            }
        }
        // 进度条
        else if (mParams.progressParams != null) {
            mCreateView = new BuildViewProgressImpl(mContext, mParams);
            mCreateView.buildBodyView();
        }
        // 输入框
        else if (mParams.inputParams != null) {
            mCreateView = new BuildViewInputImpl(mContext, mParams);
            mCreateView.buildBodyView();
        }
        // 文本
        else {
            mCreateView = new BuildViewConfirmImpl(mContext, mParams);
            mCreateView.buildBodyView();
        }

        // 图标x关闭按钮
        if (mParams.closeParams != null) {
            CloseView closeView = mCreateView.buildCloseImgView();
            closeView.regOnCloseClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mParams.dialogParams.manualClose) {
                        mOnDialogListener.dialogDismiss();
                    }
                }
            });
        }

        // 底部按钮
        ButtonView buttonView = mCreateView.buildButton();
        regNegativeListener(buttonView);
        regNeutralListener(buttonView);
        // 输入框确定
        if (mParams.inputParams != null) {
            InputView inputView = mCreateView.getBodyView();
            //输入框确定按钮事件特殊性
            regPositiveInputListener(buttonView, inputView);
        }
        // 自定义body确定
        else if (mParams.bodyViewId != 0 || mParams.bodyView != null) {
            regPositiveBodyListener(buttonView);
        }
        // 其它
        else {
            regPositiveListener(buttonView);
        }
        return getView();
    }
    public void refreshView() {
        getView().post(new Runnable() {
            @Override
            public void run() {
                mCreateView.refreshTitle();
                mCreateView.refreshContent();
                mCreateView.refreshButton();
                //刷新时带动画
                if (mParams.dialogParams.refreshAnimation == 0 || getView() == null) {
                    return;
                }
                Animation animation = AnimationUtils.loadAnimation(mContext, mParams.dialogParams.refreshAnimation);
                if (animation != null) {
                    getView().startAnimation(animation);
                }
            }
        });
    }

    private View getView() {
        if (mCreateView == null) {
            return null;
        }
        mCircleViewHolder = new CircleViewHolder(mCreateView.getRootView());
        return mCircleViewHolder.getDialogView();
    }

    private void regNegativeListener(final ButtonView viewButton) {
        viewButton.regNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewButton.timerCancel();
                if (mParams.circleListeners.clickNegativeListener != null) {
                    mParams.circleListeners.clickNegativeListener.onClick(v);
                }
                if (!mParams.dialogParams.manualClose) {
                    mOnDialogListener.dialogDismiss();
                }
            }
        });
    }

    private void regNeutralListener(final ButtonView viewButton) {
        viewButton.regNeutralListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParams.circleListeners.clickNeutralListener != null) {
                    mParams.circleListeners.clickNeutralListener.onClick(v);
                }
                if (!mParams.dialogParams.manualClose) {
                    mOnDialogListener.dialogDismiss();
                }
            }
        });
    }

    private void regPositiveListener(final ButtonView viewButton) {
        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewButton.timerRestart();
                if (mParams.circleListeners.clickPositiveListener != null) {
                    mParams.circleListeners.clickPositiveListener.onClick(v);
                }
                if (!mParams.dialogParams.manualClose) {
                    mOnDialogListener.dialogDismiss();
                }
            }
        });
    }

    private void regPositiveInputListener(final ButtonView viewButton, final InputView inputView) {
        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewButton.timerRestart();
                EditText editText = inputView.getInput();
                String text = editText.getText().toString();
                if (mParams.circleListeners.inputListener == null) {
                    return;
                }
                boolean b = mParams.circleListeners.inputListener.onClick(text, editText);
                if (b && !mParams.dialogParams.manualClose) {
                    mOnDialogListener.dialogDismiss();
                }
            }
        });
    }

    private void regPositiveBodyListener(final ButtonView viewButton) {
        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParams.circleListeners.bindBodyViewCallback == null) {
                    return;
                }
                boolean b = mParams.circleListeners.bindBodyViewCallback.onBindBodyView(mCircleViewHolder);
                if (b && !mParams.dialogParams.manualClose) {
                    mOnDialogListener.dialogDismiss();
                }
            }
        });
    }

    public interface OnDialogInternalListener {
        void dialogDismiss();
    }
}
