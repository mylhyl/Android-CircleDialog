package com.mylhyl.circledialog.sample.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.PopupParams;
import com.mylhyl.circledialog.sample.R;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

public class ListViewActivity extends AppCompatActivity {

    ActionMenuView mActionMenuView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_list_view);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new MyAdapter(this));

        findViewById(R.id.btn_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder()
                        .setPopupArrow(PopupParams.DIRECTION_BOTTOM, PopupParams.GRAVITY_RIGHT)
                        .setPopup(new String[]{"1", "2", "3", "4"}
                                , new OnRvItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                })
                        .show(getSupportFragmentManager());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mActionMenuView = findViewById(R.id.toolbar_actionMenuView);
        getMenuInflater().inflate(R.menu.work, mActionMenuView.getMenu());
        mActionMenuView.setOnMenuItemClickListener(item -> onOptionsItemSelected(item));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void showMyDialog(String string) {
        new CircleDialog.Builder()
                .setTitle("标题")
                .setText(string)
                .setPositive("确定", null)
                .show(getSupportFragmentManager());
    }

    static class MyAdapter extends BaseAdapter {

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
            textView.setOnClickListener(v -> activity.showMyDialog(((TextView) v).getText().toString()));
            return view;
        }
    }
}
