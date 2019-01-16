package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2019/1/14 16:25.
 */
public interface CloseView {

    void regOnCloseClickListener(View.OnClickListener listener);

    View getView();
}
