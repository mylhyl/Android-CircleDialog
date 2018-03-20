package com.mylhyl.circledialog.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.sample.list.ListViewActivity;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    private CircleDialog.Builder builder;
    int time = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<String> data = Arrays.asList(new String[]{"提示框", "确定框", "换头像", "输入框"
                , "进度框", "等待框", "动态改变内容"
                , "自定义dialog", "list中使用", "倒计时", "三个按钮"});
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1
                , data) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
//        ScaleLayoutConfig.init(this.getApplicationContext(),480,800);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.backgroundColor = Color.DKGRAY;
                                params.backgroundColorPress = Color.BLUE;
                            }
                        })
                        .setTitle("标题")
                        .setText("确定框")
                        .configText(new ConfigText() {
                            @Override
                            public void onConfig(TextParams params) {
                                params.padding = new int[]{150, 10, 50, 10};
                            }
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .configPositive(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
                                params.backgroundColorPress = Color.RED;
                            }
                        })
                        .show();
                break;
            case 2:
//                final List<PictureType> list = new ArrayList<>();
//                list.add(new PictureType(1, "拍照"));
//                list.add(new PictureType(2, "从相册选择"));
//                list.add(new PictureType(3, "小视频"));
                final String[] items = {"拍照", "从相册选择", "小视频"};
                new CircleDialog.Builder(this)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.backgroundColor = Color.CYAN;
                                //增加弹出动画
                                params.animStyle = R.style.dialogWindowAnim;
                            }
                        })
                        .setTitle("标题")
                        .setTitleColor(Color.BLUE)
                        .setItems(items, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int
                                    position, long id) {
                                Toast.makeText(MainActivity.this, "点击了：" + items[position]
                                        , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegative("取消", null)
                        .configNegative(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
                                //取消按钮字体颜色
                                params.textColor = Color.RED;
                                params.backgroundColorPress = Color.BLUE;
                            }
                        })
                        .show();
                break;
            case 3:
                new CircleDialog.Builder(this)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setTitle("输入框")
                        .setInputHint("请输入条件")
                        .configInput(new ConfigInput() {
                            @Override
                            public void onConfig(InputParams params) {
//                                params.inputBackgroundResourceId = R.drawable.bg_input;
                                params.gravity = Gravity.CENTER;
                                params.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
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
            case 4:
                final Timer timer = new Timer();
                builder = new CircleDialog.Builder(this);
                builder.setCancelable(false).setCanceledOnTouchOutside(false)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.backgroundColor = Color.CYAN;
                            }
                        })
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
            case 5:
                final DialogFragment dialogFragment = new CircleDialog.Builder(this)
                        .setProgressText("登录中...")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
                        .show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragment.dismiss();
                    }
                }, 3000);
                break;
            case 6:
                builder = new CircleDialog.Builder(this);
                builder.configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.gravity = Gravity.TOP;
//                        TranslateAnimation refreshAnimation = new TranslateAnimation(15, -15,
// 0, 0);
//                        refreshAnimation.setInterpolator(new OvershootInterpolator());
//                        refreshAnimation.setDuration(100);
//                        refreshAnimation.setRepeatCount(3);
//                        refreshAnimation.setRepeatMode(Animation.RESTART);
                        params.refreshAnimation = R.anim.refresh_animation;
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
            case 7:
//                DialogLoginConnPc.getInstance().show(getSupportFragmentManager(), "connPc");
                DialogLogout.getInstance().show(getSupportFragmentManager(), "DialogLogout");
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
            case 9:

                builder = new CircleDialog.Builder(this)
                        .setTitle("标题")
                        .setText("提示框")
                        .configPositive(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
                                params.disable = true;
                            }
                        })
                        .setPositive("确定(" + time + "s)", null)
                        .setNegative("取消", null);

                builder.show();

                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        builder.configPositive(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
                                --time;
                                params.text = "确定(" + time + "s)";
                                if (time == 0) {
                                    params.disable = false;
                                    params.text = "确定";
                                }
                            }
                        }).create();

                        if (time == 0)
                            handler.removeCallbacks(this);
                        else
                            handler.postDelayed(this, 1000);

                    }
                };
                handler.postDelayed(runnable, 1000);
                break;
            case 10:
                new CircleDialog.Builder(this)
                        .setTitle("标题")
                        .setText("提示框")
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutral("中间", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "中间", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }
    }
}
