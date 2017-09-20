package com.mylhyl.circledialog.sample.list;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by hupei on 2017/7/25.
 */

public class MyAdapter extends BaseAdapter {

    private ListViewActivity activity;
    private String[] items = new String[20];

    public MyAdapter(ListViewActivity activity) {
        this.activity = activity;
        for (int i = 0; i < 20; i++) {
            items[i] = "item" + i;
        }
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public String getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(android.R.layout.simple_list_item_1,
                parent, false);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));
        textView .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showMyDialog(((TextView)v).getText().toString());
            }
        });
        return view;
    }
}
