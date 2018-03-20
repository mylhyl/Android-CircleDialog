package com.mylhyl.circledialog.sample.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.sample.R;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new MyAdapter(this));
    }

    public void showMyDialog(String string) {
        new CircleDialog.Builder()
                .setTitle("标题")
                .setText(string)
                .setPositive("确定", null)
                .show(getSupportFragmentManager());
    }
}
