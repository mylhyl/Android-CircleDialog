package com.mylhyl.circledialog.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1
                , new String[]{"提示框", "确定框", "换头像", "消息框", "输入框", "进度框", "动态改变内容", "动态改变items"}));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                new CircleDialog.Builder(this)
                        .setTitle("标题")
                        .setText("提示框")
                        .setPositive("确定", null)
                        .show();
                break;
            case 1:
                new CircleDialog.Builder(this)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setTitle("标题")
                        .setText("确定框")
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case 2:
                final String[] items = {"拍照", "从相册选择", "小视频"};
//                List<People> items = new ArrayList<>();
//                items.add(new People(1, "拍照"));
//                items.add(new People(2, "从相册选择"));
//                items.add(new People(3, "小视频"));

                new CircleDialog.Builder(this)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.animStyle = R.style.dialogWindowAnim;
                            }
                        })
                        .setTitle("标题")
                        .setTitleColor(Color.BLUE)
                        .setItems(items, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        })
                        .setNegative("取消", null)
                        .configNegative(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
                                params.textColor = Color.RED;
                            }
                        })
                        .show();
                break;
            case 3:
                final DialogFragment dialogFragment = new CircleDialog.Builder(this)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setText("消息框")
                        .setTextColor(Color.BLACK)
                        .configText(new ConfigText() {
                            @Override
                            public void onConfig(TextParams params) {
                                params.height = 300;
                                params.textSize = 70;
                            }
                        })
                        .show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragment.dismiss();
                    }
                }, 5000);
                break;
        }
    }
}
