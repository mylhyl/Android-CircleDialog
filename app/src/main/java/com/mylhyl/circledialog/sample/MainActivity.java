package com.mylhyl.circledialog.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.CircleViewHolder;
import com.mylhyl.circledialog.callback.CircleItemViewBinder;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.params.CloseParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.sample.entities.MySectionEntity;
import com.mylhyl.circledialog.sample.entities.PictureTypeEntity;
import com.mylhyl.circledialog.sample.entities.WeiBoItem;
import com.mylhyl.circledialog.sample.list.CheckedAdapter;
import com.mylhyl.circledialog.view.listener.CountDownTimerObserver;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnAdPageChangeListener;
import com.mylhyl.circledialog.view.listener.OnButtonClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    //全局配置
    static {
        CircleDimen.DIALOG_RADIUS = 8;
        //CircleColor.
    }

    private BaseQuickAdapter<FunItem, BaseViewHolder> adapter;
    private CircleDialog.Builder builder;
    private BaseCircleDialog dialogFragment;
    private Handler handler;
    private Runnable runnable;
    //private Typeface typeface;
    private int time = 30;

    @SuppressLint("RestrictedApi")
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FunItem funItem = (FunItem) adapter.getItem(position);
        switch (funItem) {
            case fragment:
                startActivity(new Intent(this, FgtAty.class));
                break;
            case 提示框:
                dialogFragment = new CircleDialog.Builder()
                        //.setTypeface(typeface)
                        .setTitle("标题")
                        .setMaxHeight(0.8f)
                        //.setManualClose(true)
                        .configTitle(params -> params.isShowBottomDivider = true)
                        .setWidth(0.5f)
                        .setText("提示框")
                        .setPositive("确定", (OnButtonClickListener) null)
                        .setOnShowListener((dialog, viewHolder) ->
                                Toast.makeText(MainActivity.this, "显示了！", Toast.LENGTH_SHORT).show())
                        .setOnCancelListener(dialog ->
                                Toast.makeText(MainActivity.this, "取消了！", Toast.LENGTH_SHORT).show())
                        .show(getSupportFragmentManager());
                break;
            case 确定框:
                new CircleDialog.Builder()
                        .setCloseResId(R.mipmap.ic_close)
                        //.setTypeface(typeface)
                        .setMaxHeight(0.7f)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .configDialog(params -> {
//                            params.backgroundColor = Color.DKGRAY;
//                            params.backgroundColorPress = Color.BLUE;
                        })
                        .setTitle("移动认证简介")
                        .setSubTitle("更新日期:2019-01-16")
                        .configSubTitle(params -> params.isShowBottomDivider = true)
                        .setText("一、移动认证简介" +
                                "\n\n1、什么是移动认证" +
                                "\n\n" +
                                "移动认证，基于运营商独有的网关能力+大数据能力，以手机号码作为开放的统一账号体系，为各类应用提供全面的用户账号使用和用户数据管理的一站式解决方案，实现身份认证、鉴权、管理的新型认证技术。目前已推出一键登录、本机号码校验两大产品，已为爱奇艺、支付宝、小米、同花顺等多款热门APP提供服务。" +
                                "\n\n2、移动认证的特点" +
                                "\n\n● 以手机号码作为账号，通过确认手机号码，实现用户身份唯一性的认证。" +
                                "\n\n● 可使用户在应用注册登录环节，耗时仅1.5秒。" +
                                "\n\n● 适用于在移动手机客户端注册/登录/号码校验等多种场景。" +
                                "\n\n● 基于运营商网络认证结果，可避免短验或密码被拦截、攻击。" +
                                "\n\n3、移动认证能实现哪些能力" +
                                "\n\n目前已推出一键登录和本机号码校验两大产品。" +
                                "\n\n二、移动认证能做什么" +
                                "\n\n1、一键登录" +
                                "\n\n一键登录能力，即通过移动认证的网络认证能力，实现APP" +
                                "用户无需输入帐号密码，即可使用本机手机号码自动登录的能力。利用应用层无法截取的网络层号码认证能力验证号码的真实性，本机号码自动校验是现有短信验证方式的优化，能消除现有短信验证模式等待时间长、操作繁琐和容易泄露的痛点。" +
                                "\n\n一键登录的能力优势" +
                                "\n\n● 降低应用注册/登录门槛，减轻用户记忆负担，提高用户体验；" +
                                "\n\n● 降低对用户身份、通信行为等属性验证的繁琐步骤，助力企业完善风险管控系统" +
                                "\n\n● 取号成功率高达99.8%。" +
                                "\n\n● 两步完成注册登录，耗时仅需1.5秒。" +
                                "\n\n● 节省企业短验成本")
                        .configText(params -> {
                            params.gravity = Gravity.LEFT | Gravity.TOP;
//                            params.padding = new int[]{100, 0, 100, 50};
                        })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .setPositive("确定", v -> {
                            Toast.makeText(MainActivity.this, "确定0", Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .configPositive(params -> params.backgroundColorPress = Color.RED)
                        .show(getSupportFragmentManager());
                break;
            case 换头像:
                final List<PictureTypeEntity> items = new ArrayList<>();
                items.add(new PictureTypeEntity(1, "拍照"));
                items.add(new PictureTypeEntity(2, "从相册选择"));
                items.add(new PictureTypeEntity(3, "小视频"));
//                final String[] items = {"拍照", "从相册选择", "小视频"};
                new CircleDialog.Builder()
                        //.setTypeface(typeface)
                        .configDialog(params -> {
                            params.backgroundColorPress = Color.CYAN;
                            //增加弹出动画
                            params.animStyle = R.style.dialogWindowAnim;
//                            params.gravity = Gravity.TOP;
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
                        .setItems(items, (parent, view1, position1, id) -> {
                                    Toast.makeText(MainActivity.this, "点击了：" + items.get(position1)
                                            , Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                        )
//                        .setItemsViewBinder((CircleItemViewBinder<PictureTypeEntity>)
//                                (itemView, pictureTypeEntity, position17) -> {
//                                    if (position17 % 2 == 0) {
//                                        itemView.setTextColor(Color.RED);
//                                    }
//                                })
                        .setNegative("取消", (OnButtonClickListener) null)
//                        .setNeutral("中间", null)
                        .setPositive("确定", (OnButtonClickListener) null)
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
            case 输入框:
                dialogFragment = new CircleDialog.Builder()
                        //.setTypeface(typeface)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setTitle("输入框")
                        .setSubTitle("提示人物是什么？")
                        .setInputHint("请输入条件")
                        .setInputText("默认文本")
//                        .setInputHeight(115)
//                        .setInputShowKeyboard(true)
                        .setInputEmoji(true)
                        .setInputCounter(2048)
//                        .setInputCounter(20, (maxLen, currentLen) -> maxLen - currentLen + "/" + maxLen)
                        .configInput(params -> {
//                            params.isCounterAllEn = true;
//                            params.padding = new int[]{30, 30, 30, 30};
//                                params.inputBackgroundResourceId = R.drawable.bg_input;
//                                params.gravity = Gravity.CENTER;
                            //密码
//                                params.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                                        | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                            //文字加粗
                            params.styleText = Typeface.BOLD;
                        })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .setPositiveInput("确定", (text, v) -> {
                            if (TextUtils.isEmpty(text)) {
                                v.setError("请输入内容");
                                return false;
                            } else {
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 进度框:
                final Timer timer = new Timer();
                builder = new CircleDialog.Builder();
                builder.setCancelable(false).setCanceledOnTouchOutside(false)
                        //.setTypeface(typeface)
                        .configDialog(params -> params.backgroundColor = Color.CYAN)
                        .setTitle("下载")
                        .setProgressText("已经下载")
//                        .setProgressText("已经下载%s了")
//                        .setProgressDrawable(R.drawable.bg_progress_h)
                        .setNegative("取消", v -> {
                            timer.cancel();
                            return true;
                        })
                        .show(getSupportFragmentManager());
                TimerTask timerTask = new TimerTask() {
                    final int max = 222;
                    int progress = 0;

                    @Override
                    public void run() {
                        progress++;
                        if (progress >= max) {
                            MainActivity.this.runOnUiThread(() -> {
                                builder.setProgressText("下载完成").refresh();
                                timer.cancel();
                            });
                        } else {
                            builder.setProgress(max, progress).refresh();
                        }
                    }
                };
                timer.schedule(timerTask, 0, 50);
                break;
            case 等待框:
                dialogFragment = new CircleDialog.Builder()
                        //.setTypeface(typeface)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setWidth(0.6f)
//                        .configDialog(new ConfigDialog() {
//                            @Override
//                            public void onConfig(DialogParams params) {
//                                int systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                                    systemUiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                                } else {
//                                    systemUiVisibility |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
//                                }
//                                params.systemUiVisibility = systemUiVisibility;
//                            }
//                        })
                        .setProgressText("登录中...")
                        .configProgress(new ConfigProgress() {
                            @Override
                            public void onConfig(ProgressParams params) {
                                params.indeterminateColor = Color.parseColor("#E9AD44");
                            }
                        })
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressColor(getColor(R.color.colorAccent))
//                        .setProgressColor(Color.parseColor("#E9AD44"))
//                        .setProgressDrawable(R.drawable.bg_progress_s)

                        // 图标x关闭按钮
                        .setCloseResId(R.mipmap.ic_close, 18)
                        .setCloseGravity(CloseParams.CLOSE_TOP_RIGHT)
                        .setClosePadding(new int[]{0, 0, 3, 3})
                        .setOnCancelListener(dialog -> Toast.makeText(MainActivity.this, "取消请求", Toast.LENGTH_SHORT).show())

                        .show(getSupportFragmentManager());
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialogFragment.dismiss();
//                    }
//                }, 3000);
                break;
            case 重试等待框:
                dialogFragment = new CircleDialog.Builder()
                        .setManualClose(true)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setWidth(0.6f)
                        .setTitle("请稍后")

//                        .setText("哈哈")

                        // 进度框
                        .setProgressText("正在发送...", "超时啦！请重试")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
                        .setPositive("重试", v -> {
                            Toast.makeText(MainActivity.this, "点了重试", Toast.LENGTH_SHORT).show();
                            return true;
                        })

                        // 输入框的倒计时
//                        .setInputHint("输入")
//                        .setPositiveInput("确定", new OnInputClickListener() {
//                            @Override
//                            public boolean onClick(String text, EditText editText) {
//                                return false;
//                            }
//                        })
                        .setNegative("取消", new OnButtonClickListener() {
                            @Override
                            public boolean onClick(View v) {
                                dialogFragment.dialogDismiss();
                                return true;
                            }
                        })
                        .setPositiveTime(10 * 1000, 1000, "再来(%d)", new CountDownTimerObserver() {

                            @Override
                            public void onTimerFinish() {
                                Toast.makeText(MainActivity.this, "onTimerFinish", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 动态改变内容:
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
                        // .setTypeface(typeface)
                        .setTitle("动态改变内容")
                        .setSubTitle("小标题")
                        .setText("3秒后更新其它内容")
                        .setOnDismissListener(dialog -> removeRunnable())
                        .show(getSupportFragmentManager());
                handler = new Handler();
                runnable = () -> builder.setText("已经更新内容")
                        .setTitle("标题也变了")
                        .setSubTitle("小标题也变了")
                        .refresh();
                handler.postDelayed(runnable, 3000);
                break;
            case 自定义dialog:
//                DialogLogout.getInstance().show(getSupportFragmentManager(), "DialogLogout");
                DialogLogin.getInstance().show(getSupportFragmentManager(), "DialogLogin");
                break;
            case 倒计时:
                builder = new CircleDialog.Builder()
                        //  .setTypeface(typeface)
                        .setTitle("标题")
                        .setText("提示框")
                        .configPositive(params -> params.disable = true)
                        .setPositive("确定(" + time + "s)", (OnButtonClickListener) null)
                        .setNegative("取消", (OnButtonClickListener) null);
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
                        }).refresh();

                        if (time == 0)
                            handler.removeCallbacks(this);
                        else
                            handler.postDelayed(this, 1000);

                    }
                };
                handler.postDelayed(runnable, 1000);
                break;
            case 三个按钮:
                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setTitle("标题")
                        .setTitleIcon(R.mipmap.ic_launcher)
                        .configTitle(params -> {
                            params.styleText = Typeface.BOLD;
//                                params.backgroundColor = Color.YELLOW;
                        })
                        .setOnCreateTitleListener((titleIcon, title, subTitle) -> {
                            title.setText("重设标题");
                            titleIcon.setPadding(0, 0, 12, 0);
                        })
                        .setSubTitle("副标题")
                        .configSubTitle(params -> {
                            params.styleText = Typeface.BOLD;
                            params.gravity = Gravity.LEFT;
                        })
                        .setText("提示框")
                        .configText(params -> params.styleText = Typeface.BOLD)
                        .setOnCreateTextListener(textBody -> textBody.setText("重新设置对话框内容"))
                        .setNegative("取消", v -> {
                            Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .configNegative(params -> params.styleText = Typeface.BOLD)
                        .setNeutral("中间", v -> {
                            Toast.makeText(MainActivity.this, "中间", Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .configNeutral(params -> params.styleText = Typeface.BOLD)
                        .setPositive("确定", v -> {
                            Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .configPositive(params -> params.styleText = Typeface.BOLD)
                        .setOnCreateButtonListener((negativeButton, positiveButton, neutralButton) -> {
                            negativeButton.setText("取消？");
                            positiveButton.setText("确定？");
                            neutralButton.setText("中间？");
                        })
                        .show(getSupportFragmentManager());
                break;
            case 自定义复选Adapter:
                final String[] objects = {"item0", "item1", "item2", "item3"};
                final CheckedAdapter checkedAdapter = new CheckedAdapter(this, objects);

                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setGravity(Gravity.CENTER)
                        .setTitle("自定义复选adapter")
                        .setSubTitle("可多选")
                        .setItems(checkedAdapter, (parent, view12, position12, id) -> {
                                    checkedAdapter.toggle(position12, objects[position12]);
                                    return false;
                                }
                        )
                        .configItems(params -> {
                            params.bottomMargin = 0; // 0底部按钮左上右上就没有圆角了
                        })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .setPositive("确定", v -> {
                            SparseArray<String> saveChecked = checkedAdapter.getSaveChecked();
                            if (saveChecked.size() == 0) {
                                Toast.makeText(MainActivity.this, "请选择", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            String str = saveChecked.toString();
                            Toast.makeText(MainActivity.this, "选择了：" + str, Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .show(getSupportFragmentManager());
                break;
            case Rv换头像:
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
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 2;
                    }
                });
                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setTitle("Rv换头像")
                        .setSubTitle("副标题：请从以下中选择照片的方式进行提交")
                        .configItems(params -> params.dividerHeight = 0)
                        .setItems(list, gridLayoutManager, (view13, position13) -> {
                            Toast.makeText(MainActivity.this, "点击了：" + list.get(position13), Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .setItemsViewBinder((CircleItemViewBinder<PictureTypeEntity>)
                                (itemView, item, position17) -> {
                                    if (position17 % 2 == 0) {
                                        itemView.setTextColor(Color.RED);
                                    }
                                })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .show(getSupportFragmentManager());
                break;
            case 自定义RvAdapter:
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
                        helper.setText(R.id.textView2, item.header)
                                .setTextColor(R.id.textView2, Color.BLACK);
                    }

                    @Override
                    protected void convert(BaseViewHolder helper, MySectionEntity item) {
                        TextView textView = helper.getView(android.R.id.text1);
                        textView.setText(item.t.typeName);
                        textView.setTextColor(Color.BLACK);
                        textView.setGravity(Gravity.CENTER);
                    }

                };
                dialogFragment = new CircleDialog.Builder()
                        //.setTypeface(typeface)
                        .setRadius(0)
                        .setWidth(1f)
                        .setMaxHeight(0.6f)
                        .setYoff(0)
                        .setTitle("rvAdapter")
                        .setSubTitle("副标题哦！")
                        .setItems(rvAdapter, new LinearLayoutManager(this))
                        .configItems(params -> params.bottomMargin = 0)
                        .setNegative("关闭", (OnButtonClickListener) null)
                        .configNegative(params -> params.topMargin = 0)
                        .show(getSupportFragmentManager());
                rvAdapter.setOnItemClickListener((adapter1, view14, position14) -> {
                    Toast.makeText(MainActivity.this, "点击的是：" + adapter1.getData().get(position14), Toast
                            .LENGTH_SHORT).show();
                    dialogFragment.dismiss();
                });
                break;
            case 自定义ListAdapter单选:
                final String[] objectsR = {"item0", "item1", "item2", "item3", "item4", "item5"
                        , "item6", "item7", "item8", "item9", "item10"};
                final CheckedAdapter checkedAdapterR = new CheckedAdapter(this, objectsR, true);

                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setMaxHeight(0.5f)
                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
                        .setTitle("带复选的ListView")
                        .setSubTitle("单选")
                        .configItems(params -> params.bottomMargin = 12)
                        .setItems(checkedAdapterR, (parent, view15, position15, id) -> {
                            checkedAdapterR.toggle(position15, objectsR[position15]);
                            return false;
                        })
                        .setPositive("确定", v -> {
                            String string = checkedAdapterR.getSaveChecked().toString();
                            Toast.makeText(MainActivity.this, "选择了：" + string, Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .show(getSupportFragmentManager());
                break;
            case 自定义内容视图:
                dialogFragment = new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setTitle("提示")
                        .setBodyView(R.layout.body_login_conn_pic, viewHolder -> {
//                            CircleDrawable bgCircleDrawable = new CircleDrawable(CircleColor.DIALOG_BACKGROUND
//                                    , 0, 0, 0, 0);
//                            viewHolder.setBackgroundDrawable(R.id.root_body, bgCircleDrawable);
                        })
                        .setNegative("关闭", (OnButtonClickListener) null)
                        .show(getSupportFragmentManager());
                break;
            case lottie动画框:
                builder = new CircleDialog.Builder();
                builder.setTitle("提示")
                        .setSubTitle("副提示语")
                        // .setTypeface(typeface)
                        .setWidth(0.7f)
                        .setLottieAnimation("loading.json")

                        // 有文件夹的
//                        .setLottieAnimation("loading/data.json")
//                        .configLottie(params -> {
//                            params.imageAssetsFolder="loading/images";
//                        })
                        .setLottieLoop(true)
                        .playLottieAnimation()
                        .setLottieText("正在加载...")
                        .show(getSupportFragmentManager());
                handler = new Handler();
                runnable = () ->
                        builder
//                                .setLottieAnimation("loading/data.json")
//                                .configLottie(params ->
//                                        params.imageAssetsFolder = "loading/images"
//                                )
                                .setLottieText("已经更新内容")
                                .refresh();
                handler.postDelayed(runnable, 3000);
                break;
            case 仿微博分享:
                MenuInflater menuInflater = new SupportMenuInflater(this);
                MenuBuilder menuBuilder = new MenuBuilder(this);
                menuInflater.inflate(R.menu.menu_share_grid, menuBuilder);
                List<WeiBoItem> weiBoItems = new ArrayList<>();
                for (int i = 0; i < menuBuilder.size(); i++) {
                    MenuItem menuItem = menuBuilder.getItem(i);
                    weiBoItems.add(new WeiBoItem(menuItem.getItemId(), menuItem.getTitle().toString()
                            , menuItem.getIcon()));
                }

                final BaseQuickAdapter weiboRvAdapter = new BaseQuickAdapter<WeiBoItem, BaseViewHolder>(
                        android.R.layout.simple_list_item_1, weiBoItems) {

                    @Override
                    protected void convert(BaseViewHolder helper, WeiBoItem item) {
                        TextView textView = helper.getView(android.R.id.text1);
                        textView.setText(item.getTitle());
                        textView.setTextColor(Color.BLACK);
                        textView.setCompoundDrawablesWithIntrinsicBounds(null, item.getIcon(), null, null);
                        textView.setGravity(Gravity.CENTER);
                        TypedValue typedValue = new TypedValue();
                        helper.itemView.getContext().getTheme().resolveAttribute(android.R.attr
                                .selectableItemBackground, typedValue, true);
                        textView.setBackgroundResource(typedValue.resourceId);
                    }
                };
                weiboRvAdapter.setOnItemClickListener((adapter12, view17, position16) -> {
                    WeiBoItem item = weiBoItems.get(position16);
                    Toast.makeText(MainActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                    dialogFragment.dismissAllowingStateLoss();
                });

                dialogFragment = new CircleDialog.Builder()
                        .bottomFull()
                        // .setTypeface(typeface)
                        .setTitle("分享到")
                        .configTitle(params -> params.gravity = Gravity.LEFT)
                        .configItems(params -> params.dividerHeight = 0)
                        .setItems(weiboRvAdapter, new GridLayoutManager(this, 5))
                        .configItems(params -> params.bottomMargin = 0)
                        .show(getSupportFragmentManager());
                break;
            case RvVertical:
                final List<PictureTypeEntity> rvListForV = new ArrayList<>();
                for (int i = 0; i < 3 * 11; i += 3) {
                    rvListForV.add(new PictureTypeEntity(i, "拍照" + i));
                    rvListForV.add(new PictureTypeEntity(i + 1, "从相册选择" + (i + 1)));
                    rvListForV.add(new PictureTypeEntity(i + 2, "小视频" + (i + 2)));
                }
                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setMaxHeight(0.5f)
                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
//                        .setTitle("Rv Vertical")
                        .configItems(params -> params.dividerHeight = 1)
                        .setItems(rvListForV
                                , (view13, position13) -> {
                                    Toast.makeText(MainActivity.this
                                            , "点击了：" + rvListForV.get(position13)
                                            , Toast.LENGTH_SHORT).show();
                                    return true;
                                })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .show(getSupportFragmentManager());
                break;
            case RvHorizontal:
                final List<PictureTypeEntity> rvListForH = new ArrayList<>();
                rvListForH.add(new PictureTypeEntity(1, "拍照"));
                rvListForH.add(new PictureTypeEntity(2, "从相册选择"));
                rvListForH.add(new PictureTypeEntity(3, "小视频"));
                rvListForH.add(new PictureTypeEntity(4, "拍照1"));
                rvListForH.add(new PictureTypeEntity(5, "从相册选择1"));
                rvListForH.add(new PictureTypeEntity(6, "小视频1"));
                rvListForH.add(new PictureTypeEntity(7, "拍照2"));
                rvListForH.add(new PictureTypeEntity(8, "从相册选择3"));
                rvListForH.add(new PictureTypeEntity(9, "小视频4"));

                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
                        .setTitle("Rv Horizontal")
                        .configItems(params -> params.dividerHeight = 2)
                        .setItems(rvListForH, new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                                , (view13, position13) -> {
                                    Toast.makeText(MainActivity.this
                                            , "点击了：" + rvListForH.get(position13)
                                            , Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                        )
                        .setNegative("取消", (OnButtonClickListener) null)
                        .show(getSupportFragmentManager());
                break;
            case 广告无x:
                new CircleDialog.Builder()
                        //  .setTypeface(typeface)
                        .setWidth(0.5f)
                        .setCloseResId(R.mipmap.ic_close, 27)
                        .setCloseGravity(CloseParams.CLOSE_TOP_RIGHT)
                        .setAdResId(R.mipmap.ic_zfbxcc, (view18, position18) -> {
                            Toast.makeText(MainActivity.this, "点击了"
                                    , Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .show(getSupportFragmentManager());
                break;
            case 广告下有x:
                List<String> urls = new ArrayList<>();
                urls.add("http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-007.jpg");
                urls.add("http://img.ivsky.com/img/tupian/pre/201801/16/qinwen_lianren-006.jpg");
                urls.add("http://img.ivsky.com/img/tupian/pre/201803/24/qinwen_lianren-001.jpg");
                new CircleDialog.Builder()
                        // .setTypeface(typeface)
                        .setWidth(0.5f)
                        //.setImageLoadEngine(new Glide4ImageLoadEngine())
                        .setCloseResId(R.mipmap.ic_close, 27)
                        .setCloseGravity(CloseParams.CLOSE_BOTTOM_CENTER)
                        .setClosePadding(new int[]{0, 15, 0, 0})
                        .setAdUrl(urls, (view18, position18) -> {
                            Toast.makeText(MainActivity.this, "点击了" + (position18 + 1)
                                    , Toast.LENGTH_SHORT).show();
                            return true;
                        })
                        .setAdPageChangeListener(new OnAdPageChangeListener() {
                            @Override
                            public void onPageSelected(Context context, ImageView imageView, String url, int position) {
                                Glide.with(context).load(url).into(imageView);
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case 广告左上有x:
                List<String> urls1 = new ArrayList<>();
                urls1.add("http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-005.jpg");
                urls1.add("http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-007.jpg");
                urls1.add("http://img.ivsky.com/img/tupian/pre/201801/16/qinwen_lianren-006.jpg");
                urls1.add("http://img.ivsky.com/img/tupian/pre/201803/24/qinwen_lianren-001.jpg");
                new CircleDialog.Builder()
                        .setWidth(0.8f)
//                .setImageLoadEngine(new Glide4ImageLoadEngine())
                        .setAdPageChangeListener(new OnAdPageChangeListener() {
                            @Override
                            public void onPageSelected(Context context, ImageView imageView, String url, int position) {
                                Glide.with(context).load(url).into(imageView);
                            }
                        })
                        .setAdUrl(urls1
//                        "http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-005.jpg"
                                , new OnAdItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position) {
                                        Toast.makeText(MainActivity.this, "点击了第" + (position + 1) + "页",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                        return true;
                                    }
                                })
//                .setAdUrl("http://img.ivsky.com/img/tupian/pre/201707/30/xingganyoumeilidemeinvtupian-007.jpg")
//                .setAdUrl("http://img.ivsky.com/img/tupian/pre/201801/16/qinwen_lianren-006.jpg")
//                .setAdUrl("http://img.ivsky.com/img/tupian/pre/201803/24/qinwen_lianren-001.jpg")
                        .setAdIndicator(true)
//                .setAdIndicatorPoint(R.drawable.selector_point)
                        .setCloseResId(R.mipmap.ic_close, 23)
                        .setClosePadding(new int[]{8, 0, 0, 0})
                        .setCloseGravity(CloseParams.CLOSE_TOP_LEFT)
                        .setCloseConnector(1, 19)
                        .show(getSupportFragmentManager());
                break;
            case 自定义body输入框的响应: {

                CircleDialog.Builder builder = new CircleDialog.Builder();
                builder.setTitle("自定义body")
                        .setSubTitle("按钮回调view")
                        .configSubTitle(params -> params.isShowBottomDivider = true)
                        .setBodyView(R.layout.body_login, viewHolder -> {
                            EditText etUser = viewHolder.findViewById(R.id.login_et_user);
                            EditText etPwd = viewHolder.findViewById(R.id.login_et_pwd);

                            viewHolder.addTextChangedListener(etUser, (s, start, before, count) ->
                                    builder.setPositiveDisable(
                                            TextUtils.isEmpty(etUser.getText()) || TextUtils.isEmpty(etPwd.getText())
                                    ).refresh()
                            );
                            viewHolder.addTextChangedListener(etPwd, (s, start, before, count) -> {
                                // 测试长度为3自动关闭
                                if (s.length() == 3) {
                                    builder.dismiss();
                                } else {
                                    builder.setPositiveDisable(
                                            TextUtils.isEmpty(etUser.getText()) || TextUtils.isEmpty(etPwd.getText())
                                    ).refresh();
                                }
                            });
                        })
                        .setPositiveDisable(true)
                        .setPositiveBody("登录", viewHolder -> {
                            EditText etUser = viewHolder.findViewById(R.id.login_et_user);
                            EditText etPwd = viewHolder.findViewById(R.id.login_et_pwd);
                            TextView tvError = viewHolder.findViewById(R.id.login_tv_error);

                            if ("1".equals(etUser.getText().toString()) &&
                                    "2".equals(etPwd.getText().toString())) {
                                return true;
                            }
                            tvError.setText("用户或密码错误");
                            return false;
                        })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .show(getSupportFragmentManager());
                break;
            }
            case 密码确认框:
                CircleDialog.Builder builder = new CircleDialog.Builder();
                builder.setTitle("设置密码")
                        .setSubTitle("请您牢记此密码")
                        .configSubTitle(params -> params.isShowBottomDivider = false)
                        .setBodyView(R.layout.body_verify_pwd, viewHolder -> {
                            EditText etPwd = viewHolder.findViewById(R.id.verify_et_pwd);
                            EditText etPwdConfirm = viewHolder.findViewById(R.id.verify_et_pwdConfirm);

                            // 输入为空禁用确认按钮
                            CircleViewHolder.TextWatcher textWatcher = (s, start, before, count) ->
                                    builder.setPositiveDisable(TextUtils.isEmpty(etPwd.getText()) ||
                                            TextUtils.isEmpty(etPwdConfirm.getText())
                                    ).refresh();
                            viewHolder.addTextChangedListener(etPwd, textWatcher)
                                    .addTextChangedListener(etPwdConfirm, textWatcher);

                        })
                        .setPositiveDisable(true)
                        .setPositiveBody("确定", viewHolder -> {
                            EditText etPwd = viewHolder.findViewById(R.id.verify_et_pwd);
                            EditText etPwdConfirm = viewHolder.findViewById(R.id.verify_et_pwdConfirm);
                            TextView tvError = viewHolder.findViewById(R.id.login_tv_error);
                            if (etPwd.getText().equals(etPwdConfirm.getText())) {
                                // do something
                                return true;
                            }
                            tvError.setText("二次密码不一致");
                            return false;
                        })
                        .setNegative("取消", (OnButtonClickListener) null)
                        .show(getSupportFragmentManager());
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //typeface = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<FunItem> listData = Arrays.asList(FunItem.values());
        adapter = new BaseQuickAdapter<FunItem, BaseViewHolder>(android.R.layout.simple_list_item_1, listData) {
            @Override
            protected void convert(BaseViewHolder helper, FunItem item) {
                helper.setText(android.R.id.text1, item.name());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void removeRunnable() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        handler = null;
        runnable = null;
    }

    public enum FunItem {
        fragment,
        提示框, 确定框, 换头像, 输入框, 进度框, 等待框, 重试等待框, 动态改变内容, 自定义dialog, 倒计时, 三个按钮,
        自定义复选Adapter, Rv换头像, 自定义RvAdapter, 自定义ListAdapter单选, 自定义内容视图, lottie动画框, 仿微博分享,
        RvVertical, RvHorizontal, 广告无x, 广告下有x, 广告左上有x, 自定义body输入框的响应, 密码确认框
    }

}