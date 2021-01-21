package com.mylhyl.circledialog.view.listener;

/**
 * Created by hupei on 2021/1/21.
 * <p>
 * 功能描述：
 * <p>
 * 业务逻辑说明：
 * <ul>
 *      <li></li>
 * </ul>
 * <p>
 * 注意事项：
 * <ul>
 *      <li></li>
 * </ul>
 * <p>
 * 变更记录：
 * <ul>
 *      <li></li>
 * </ul>
 *
 * @author hupei
 * @since 5.3.6
 */
public abstract class CountDownTimerObserver {

    public void onTimerTick(long millisUntilFinished) {
    }

    public abstract void onTimerFinish();
}
