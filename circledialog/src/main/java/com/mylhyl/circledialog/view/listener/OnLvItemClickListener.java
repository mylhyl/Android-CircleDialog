package com.mylhyl.circledialog.view.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by hupei on 2018/8/28 11:17.
 */
public interface OnLvItemClickListener {
    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     * @return true 关闭对话框. false不关闭
     */
    boolean onItemClick(AdapterView<?> parent, View view, int position, long id);
}
