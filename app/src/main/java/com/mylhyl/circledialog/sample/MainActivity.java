package com.mylhyl.circledialog.sample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.sample.entities.MySectionEntity;
import com.mylhyl.circledialog.sample.entities.PictureTypeEntity;
import com.mylhyl.circledialog.sample.list.CheckedAdapter;
import com.mylhyl.circledialog.sample.list.ListViewActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    //全局配置
    static {
        CircleDimen.DIALOG_RADIUS = 20;
        //CircleColor.
    }

    private CircleDialog.Builder builder;
    private DialogFragment dialogFragment;
    private Handler handler;
    private Runnable runnable;
    private int time = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<String> listData = Arrays.asList(new String[]{"提示框", "确定框", "换头像", "输入框"
                , "进度框", "等待框", "动态改变内容"
                , "自定义dialog", "list中使用", "倒计时", "三个按钮", "自定义List adapter(多选)"
                , "Rv换头像", "自定义Rv adapter", "自定义List adapter(单选)", "自定义内容视图"
                , "lottie动画框"});
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1
                , listData) {
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
                new CircleDialog.Builder()
                        .setTitle("标题")
                        .setText("提示框")
                        .setPositive("确定", null)
                        .setOnShowListener(dialog ->
                                Toast.makeText(MainActivity.this, "显示了！", Toast.LENGTH_SHORT).show())
                        .setOnCancelListener(dialog ->
                                Toast.makeText(MainActivity.this, "取消了！", Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 1:
                new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .configDialog(params -> {
                            params.backgroundColor = Color.DKGRAY;
                            params.backgroundColorPress = Color.BLUE;
                        })
                        .setTitle("标题")
                        .setText("冷却风扇口无异物，风机扇叶无损伤，无过热痕迹")
                        .configText(params -> {
//                                params.gravity = Gravity.LEFT | Gravity.TOP;
                            params.padding = new int[]{100, 0, 100, 50};
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", v ->
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show())
                        .configPositive(params -> params.backgroundColorPress = Color.RED)
                        .show(getSupportFragmentManager());
                break;
            case 2:
//                final List<PictureTypeEntity> list = new ArrayList<>();
//                list.add(new PictureTypeEntity(1, "拍照"));
//                list.add(new PictureTypeEntity(2, "从相册选择"));
//                list.add(new PictureTypeEntity(3, "小视频"));
                final String[] items = {"拍照", "从相册选择", "小视频"};
                new CircleDialog.Builder()
                        .configDialog(params -> {
                            params.backgroundColorPress = Color.CYAN;
                            //增加弹出动画
                            params.animStyle = R.style.dialogWindowAnim;
                        })
                        .setTitle("标题")
//                        .setTitleColor(Color.BLUE)
                        .configTitle(params -> {
//                                params.backgroundColor = Color.RED;
                        })
                        .setSubTitle("副标题：请从以下中选择照片的方式进行提交")
                        .configSubTitle(params -> {
//                                params.backgroundColor = Color.YELLOW;
                        })
                        .setItems(items, (parent, view1, position1, id) ->
                                Toast.makeText(MainActivity.this, "点击了：" + items[position1]
                                        , Toast.LENGTH_SHORT).show())
                        .setNegative("取消", null)
//                        .setNeutral("中间", null)
//                        .setPositive("确定", null)
//                        .configNegative(new ConfigButton() {
//                            @Override
//                            public void onConfig(ButtonParams params) {
//                                //取消按钮字体颜色
//                                params.textColor = Color.RED;
//                                params.backgroundColorPress = Color.BLUE;
//                            }
//                        })
                        .show(getSupportFragmentManager());
                break;
            case 3:
                dialogFragment = new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setInputManualClose(true)
                        .setTitle("输入框")
                        .setSubTitle("提示人物是什么？")
                        .setInputHint("请输入条件")
                        .setInputText("默认文本")
//                        .autoInputShowKeyboard()
                        .setInputCounter(20)
//                        .setInputCounter(20, (maxLen, currentLen) -> maxLen - currentLen + "/" + maxLen)
                        .configInput(params -> {
//                            params.padding = new int[]{30, 30, 30, 30};
//                                params.inputBackgroundResourceId = R.drawable.bg_input;
//                                params.gravity = Gravity.CENTER;
                            //密码
//                                params.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                                        | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                            //文字加粗
                            params.styleText = Typeface.BOLD;
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("确定", (text, v) -> {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(MainActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 4:
                final Timer timer = new Timer();
                builder = new CircleDialog.Builder();
                builder.setCancelable(false).setCanceledOnTouchOutside(false)
                        .configDialog(params -> params.backgroundColor = Color.CYAN)
                        .setTitle("下载")
                        .setProgressText("已经下载")
//                        .setProgressText("已经下载%s了")
//                        .setProgressDrawable(R.drawable.bg_progress_h)
                        .setNegative("取消", v -> timer.cancel())
                        .show(getSupportFragmentManager());
                TimerTask timerTask = new TimerTask() {
                    final int max = 222;
                    int progress = 0;

                    @Override
                    public void run() {
                        progress++;
                        if (progress >= max) {
                            MainActivity.this.runOnUiThread(() -> {
                                builder.setProgressText("下载完成").create();
                                timer.cancel();
                            });
                        } else {
                            builder.setProgress(max, progress).create();
                        }
                    }
                };
                timer.schedule(timerTask, 0, 50);
                break;
            case 5:
                dialogFragment = new CircleDialog.Builder()
                        .setProgressText("登录中...")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
                        .show(getSupportFragmentManager());
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialogFragment.dismiss();
//                    }
//                }, 3000);
                break;
            case 6:
                builder = new CircleDialog.Builder();
                builder.configDialog(params -> {
                    params.gravity = Gravity.TOP;
//                        TranslateAnimation refreshAnimation = new TranslateAnimation(15, -15,
// 0, 0);
//                        refreshAnimation.setInterpolator(new OvershootInterpolator());
//                        refreshAnimation.setDuration(100);
//                        refreshAnimation.setRepeatCount(3);
//                        refreshAnimation.setRepeatMode(Animation.RESTART);
                    params.refreshAnimation = R.anim.refresh_animation;
                })

                        .setTitle("动态改变内容")
                        .setText("3秒后更新其它内容")
                        .setOnDismissListener(dialog -> removeRunnable())
                        .show(getSupportFragmentManager());
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        builder.setText("已经更新内容").create();
                    }
                };
                handler.postDelayed(runnable, 3000);
                break;
            case 7:
//                DialogLoginConnPc.getInstance().show(getSupportFragmentManager(), "connPc");
                DialogLogout.getInstance().show(getSupportFragmentManager(), "DialogLogout");
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
            case 9:

                builder = new CircleDialog.Builder()
                        .setTitle("标题")
                        .setText("提示框")
                        .configPositive(params -> params.disable = true)
                        .setPositive("确定(" + time + "s)", null)
                        .setNegative("取消", null);
                builder.setOnDismissListener(dialog -> removeRunnable());
                dialogFragment = builder.show(getSupportFragmentManager());

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        builder.configPositive(params -> {
                            --time;
                            params.text = "确定(" + time + "s)";
                            if (time == 0) {
                                params.disable = false;
                                params.text = "确定";
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
                new CircleDialog.Builder()
                        .setTitle("标题")
                        .setTitleIcon(R.mipmap.ic_launcher)
                        .configTitle(params -> {
                            params.styleText = Typeface.BOLD;
//                                params.backgroundColor = Color.YELLOW;
                        })
                        .setOnCreateTitleListener((titleIcon, title, subTitle) -> {
                            title.setText("重设标题");
                            titleIcon.setPadding(0, 0, 30, 0);
                        })
                        .setSubTitle("副标题")
                        .configSubTitle(params -> {
                            params.styleText = Typeface.BOLD;
                            params.gravity = Gravity.LEFT;
                        })
                        .setText("提示框")
                        .configText(params -> params.styleText = Typeface.BOLD)
                        .setOnCreateTextListener(textBody -> textBody.setText("重新设置对话框内容"))
                        .setNegative("取消", v ->
                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show())
                        .configNegative(params -> params.styleText = Typeface.BOLD)
                        .setNeutral("中间", v ->
                                Toast.makeText(MainActivity.this, "中间", Toast.LENGTH_SHORT).show())
                        .configNeutral(params -> params.styleText = Typeface.BOLD)
                        .setPositive("确定", v ->
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show())
                        .configPositive(params -> params.styleText = Typeface.BOLD)
                        .setOnCreateButtonListener((negativeButton, positiveButton, neutralButton) -> {
                            negativeButton.setText("取消？");
                            positiveButton.setText("确定？");
                            neutralButton.setText("中间？");
                        })
                        .show(getSupportFragmentManager());
                break;
            case 11:
                final String[] objects = {"item0", "item1", "item2", "item3"};
                final CheckedAdapter checkedAdapter = new CheckedAdapter(this, objects);

                new CircleDialog.Builder()
                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
                        .setTitle("带复选的ListView")
                        .setSubTitle("可多选")
                        .setItems(checkedAdapter, (parent, view12, position12, id) ->
                                checkedAdapter.toggle(position12, objects[position12]))
                        .setItemsManualClose(true)
                        .setPositive("确定", v -> Toast.makeText(MainActivity.this
                                , "选择了：" + checkedAdapter.getSaveChecked().toString()
                                , Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 12:
                final List<PictureTypeEntity> list = new ArrayList<>();
                list.add(new PictureTypeEntity(1, "拍照"));
                list.add(new PictureTypeEntity(2, "从相册选择"));
                list.add(new PictureTypeEntity(3, "小视频"));
                list.add(new PictureTypeEntity(4, "拍照1"));
                list.add(new PictureTypeEntity(5, "从相册选择1"));
//                list.add(new PictureTypeEntity(6, "小视频1"));
//                list.add(new PictureTypeEntity(7, "拍照2"));
//                list.add(new PictureTypeEntity(8, "从相册选择2"));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
                new CircleDialog.Builder()
                        .setTitle("Rv换头像")
                        .setSubTitle("副标题：请从以下中选择照片的方式进行提交")
                        .configItems(params -> params.dividerHeight = 0)
                        .setItems(list, gridLayoutManager, (view13, position13) ->
                                Toast.makeText(MainActivity.this, "点击了：" + list.get(position13)
                                        , Toast.LENGTH_SHORT).show())
                        .setNegative("取消", null)
                        .show(getSupportFragmentManager());
                break;
            case 13:
                String[] heads = {"Head1", "Head2"};
                ArrayList<MySectionEntity> listData = new ArrayList<>();
                for (int i = 0; i < heads.length; i++) {
                    listData.add(new MySectionEntity(true, heads[i]));
                    for (int j = 0; j < (i == 0 ? 4 : 6); j++) {
                        listData.add(new MySectionEntity(new PictureTypeEntity(j, heads[i] + "：" + j)));
                    }
                }
                final BaseQuickAdapter rvAdapter = new BaseSectionQuickAdapter<MySectionEntity, BaseViewHolder>(
                        android.R.layout.simple_list_item_1, R.layout.item_rv, listData) {
                    @Override
                    protected void convertHead(BaseViewHolder helper, MySectionEntity item) {
                        helper.setText(R.id.textView2, item.header);
                    }

                    @Override
                    protected void convert(BaseViewHolder helper, MySectionEntity item) {
                        TextView textView = helper.getView(android.R.id.text1);
                        textView.setText(item.t.typeName);
                        textView.setGravity(Gravity.CENTER);
                    }

                };
                dialogFragment = new CircleDialog.Builder()
                        .setGravity(Gravity.BOTTOM)
                        .setRadius(0)
                        .setWidth(1f)
                        .setMaxHeight(0.8f)
                        .setYoff(0)
                        .setTitle("rvAdapter")
                        .setSubTitle("副标题哦！")
                        .setItems(rvAdapter, new LinearLayoutManager(this))
                        .setNegative("关闭", null)
                        .configNegative(params -> params.topMargin = 0)
                        .show(getSupportFragmentManager());
                rvAdapter.setOnItemClickListener((adapter1, view14, position14) -> {
                    Toast.makeText(MainActivity.this, "点击的是：" + adapter1.getData().get(position14), Toast.LENGTH_SHORT).show();
                    dialogFragment.dismiss();
                });
                break;
            case 14:
                final String[] objectsR = {"item0", "item1", "item2", "item3"};
                final CheckedAdapter checkedAdapterR = new CheckedAdapter(this, objectsR, true);

                new CircleDialog.Builder()
                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
                        .setTitle("带复选的ListView")
                        .setSubTitle("单选")
                        .setItems(checkedAdapterR, (parent, view15, position15, id) ->
                                checkedAdapterR.toggle(position15, objectsR[position15]))
                        .setItemsManualClose(true)
                        .setPositive("确定", v -> Toast.makeText(MainActivity.this
                                , "选择了：" + checkedAdapterR.getSaveChecked().toString()
                                , Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 15:

                dialogFragment = new CircleDialog.Builder()
                        .setTitle("提示")
                        .setWidth(0.7f)
                        .setBodyView(R.layout.share_page_loading, view16 -> {
                            CircleDrawable bgCircleDrawable = new CircleDrawable(CircleColor.DIALOG_BACKGROUND
                                    , 0, 0, CircleDimen.DIALOG_RADIUS, CircleDimen.DIALOG_RADIUS);
                            view16.setBackgroundDrawable(bgCircleDrawable);
                        })
                        .show(getSupportFragmentManager());
                break;
            case 16:
                new CircleDialog.Builder()
                        .setTitle("提示")
                        .setSubTitle("副提示语")
                        .setWidth(0.7f)
                        .setLottieAnimation("loading.json")
                        .setLottieLoop(true)
                        .playLottieAnimation()
                        .setLottieText("正在加载...")
                        .show(getSupportFragmentManager());
                break;
        }
    }

    private void removeRunnable() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        handler = null;
        runnable = null;
    }
}
