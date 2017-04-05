package com.mylhyl.circledialog.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private CircleDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1
                , new String[]{"提示框", "确定框", "换头像", "消息框", "输入框", "进度框", "等待框", "动态改变内容"
                , "动态改变items", "自定义dialog"}));
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
                new CircleDialog.Builder(this)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                //增加弹出动画
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
                                //取消按钮字体颜色
                                params.textColor = Color.RED;
                            }
                        })
                        .show();
                break;
            case 3:
                final DialogFragment dialogFragment = new CircleDialog.Builder(this)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setText("消息框，3秒后自动关闭")
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
                }, 3000);
                break;
            case 4:
                new CircleDialog.Builder(this)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setTitle("输入框")
                        .setInputHint("请输入条件")
                        .configInput(new ConfigInput() {
                            @Override
                            public void onConfig(InputParams params) {
//                                params.inputBackgroundResourceId = R.drawable.bg_input;
                            }
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("确定", new OnInputClickListener() {
                            @Override
                            public void onClick(String text, View v) {
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case 5:
                final Timer timer = new Timer();
                builder = new CircleDialog.Builder(this);
                builder.setCancelable(false).setCanceledOnTouchOutside(false)
                        .setTitle("下载")
                        .setProgressText("已经下载")
//                        .setProgressText("已经下载%s了")
//                        .setProgressDrawable(R.drawable.bg_progress_h)
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                timer.cancel();
                            }
                        })
                        .show();
                TimerTask timerTask = new TimerTask() {
                    final int max = 222;
                    int progress = 0;

                    @Override
                    public void run() {
                        progress++;
                        if (progress >= max) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    builder.setProgressText("下载完成").create();
                                    timer.cancel();
                                }
                            });
                        } else {
                            builder.setProgress(max, progress).create();
                        }
                    }
                };
                timer.schedule(timerTask, 0, 50);
                break;
            case 6:
                new CircleDialog.Builder(this)
                        .setProgressText("登录中...")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
                        .show();
                break;
            case 7:
                builder = new CircleDialog.Builder(this);
                builder.configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.gravity = Gravity.TOP;
                        TranslateAnimation animation = new TranslateAnimation(15, -15, 0, 0);
                        animation.setInterpolator(new OvershootInterpolator());
                        animation.setDuration(100);
                        animation.setRepeatCount(3);
                        animation.setRepeatMode(Animation.RESTART);
                        params.refreshAnimation = animation;
                    }
                })
                        .setTitle("动态改变内容")
                        .setText("3秒后更新其它内容")
                        .show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        builder.setText("已经更新内容").create();
                    }
                }, 3000);
                break;
            case 8:
                final List<PictureType> list = new ArrayList<>();
                list.add(new PictureType(1, "拍照"));
                list.add(new PictureType(2, "从相册选择"));
                list.add(new PictureType(3, "小视频"));

                builder = new CircleDialog.Builder(this);
                builder.configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                        .setTitle("动态改变Items")
                        .setTitleColor(Color.BLUE)
                        .setItems(list, new AdapterView.OnItemClickListener() {
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(new PictureType(4, "摄影"));
                        builder.create();
                    }
                }, 3000);
                break;
            case 9:
                DialogLoginConnPc.getInstance().show(getSupportFragmentManager(), "connPc");
                break;
        }
    }
}
