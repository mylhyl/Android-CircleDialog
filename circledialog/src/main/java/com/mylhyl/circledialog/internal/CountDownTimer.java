package com.mylhyl.circledialog.internal;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Schedule a countdown until a time in the future, with
 * regular notifications on intervals along the way.
 * <p>
 * Example of showing a 30 second countdown in a text field:
 *
 * <pre class="prettyprint">
 * new CountDownTimer(30000, 1000) {
 *
 *     public void onTick(long millisUntilFinished) {
 *         mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
 *     }
 *
 *     public void onFinish() {
 *         mTextField.setText("done!");
 *     }
 *  }.start();
 * </pre>
 * <p>
 * The calls to {@link #onTick(long)} are synchronized to this object so that
 * one call to {@link #onTick(long)} won't ever occur before the previous
 * callback is complete.  This is only relevant when the implementation of
 * {@link #onTick(long)} takes an amount of time to execute that is significant
 * compared to the countdown interval.
 */
public abstract class CountDownTimer {
    private static final int MSG = 1;
    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;
    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;
    private long mMillisLeft = 0;
    private long mStopTimeInFuture;
    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;
    // handles counting down
    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            synchronized (CountDownTimer.this) {
                if (mCancelled) {
                    return;
                }
                long elapsedRealtime = mStopTimeInFuture;
                mStopTimeInFuture = SystemClock.elapsedRealtime();
                final long millis = mStopTimeInFuture - elapsedRealtime;
                mMillisLeft += millis;

                if (mMillisInFuture <= mMillisLeft) {
                    removeMessages(MSG);
                    onFinish();
                } else {
                    long millisUntilFinished = mMillisInFuture - mMillisLeft;
                    onTick(millisUntilFinished);
                    long delay = millisUntilFinished;
                    if (delay > mCountdownInterval) {
                        delay = mStopTimeInFuture + mCountdownInterval - SystemClock.elapsedRealtime() - mMillisLeft % mCountdownInterval;
                    }

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= mMillisLeft) {
            mCancelled = true;
            onFinish();
            return this;
        }
        mMillisLeft = 0;
        mStopTimeInFuture = SystemClock.elapsedRealtime();
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public synchronized final void restart() {
        mMillisLeft = 0;
        mStopTimeInFuture = SystemClock.elapsedRealtime();
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
    }

    /**
     * pause the countdown.
     */
    public synchronized final CountDownTimer pause() {
        mHandler.removeMessages(MSG);

        if (mMillisInFuture > mMillisLeft) {
            long elapsedRealtime = mStopTimeInFuture;
            mStopTimeInFuture = SystemClock.elapsedRealtime();
            final long millis = mStopTimeInFuture - elapsedRealtime;
            mMillisLeft += millis;

            onTick(mMillisLeft);
        }
        return this;
    }

    public synchronized final void resume() {
        if (mMillisInFuture > mMillisLeft) {
            mStopTimeInFuture = SystemClock.elapsedRealtime();
            mHandler.sendMessage(mHandler.obtainMessage(MSG));
        }
    }

    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();

}
