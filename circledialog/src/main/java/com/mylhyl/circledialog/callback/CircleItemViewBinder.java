package com.mylhyl.circledialog.callback;

import android.widget.TextView;

/**
 * Created by hupei on 2019/12/23.
 *
 * @author hupei
 * @since 2.6.15
 */
public interface CircleItemViewBinder<T> {

    void onBinder(TextView itemView, T item, int position, int curPosition);
}
