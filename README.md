# Android-CircleDialog 
 * 基于`DialogFragment`封装，已封装的对话框类型有，常规对话框、列表框、输入框、进度框；
 每个对话框类型也支持自定义边框圆角、背景透明度、字体大小与色值
 * 初衷是掌握知识点，此库不一定适合你的产品整体风格，当然能够适合你的项目最好不过，有建议和不足之处欢迎骚扰

# 知识点
  全代码创建`shape`、`selector`、`Layout`，主要是`Drawable`所使用类如下：
  `ShapeDrawable`、`RoundRectShape`、`GradientDrawable`、`ClipDrawable`、`LayerDrawable`、`StateListDrawable`

# 效果图
<img src="preview/gif.gif" width="240px"/>

# 引入
```xml
 compile 'com.mylhyl:circleDialog:2.1.6'
```

eclipse 可以[点击这里下载aar文件](http://jcenter.bintray.com/com/mylhyl/circleDialog/), 然后用zip解压取出jar包

# 使用
简单的对话框

```java
                new CircleDialog.Builder(this)
                        .setTitle("标题")
                        .setText("提示框")
                        .setPositive("确定", null)
                        .show();
```

选择对话框

```java
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
```
各种自由配置
```java
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                
                            }
                        })
                        
                        .configTitle(new ConfigTitle() {
                            @Override
                            public void onConfig(TitleParams params) {
                                
                            }
                        })
                        
                        .configText(new ConfigText() {
                            @Override
                            public void onConfig(TextParams params) {
                                
                            }
                        })
```

[更多参数](https://github.com/mylhyl/Android-CircleDialog/tree/master/circledialog/src/main/java/com/mylhyl/circledialog/params)

#说明

 * 此库自动将px转换百分比，由于 Dialog 布局一般只有微调，暂时只支持textSize，height，padding
 * 默认字体大小;Title、message、button、padding 的px在设计稿为 1080 * 1920 的尺寸
 * 也可自己定义，代码中加入ScaleLayoutConfig.init(appContext,480,800),建议在Application中加入
 或者在 manifest.xml中加入如下格式

```xml
    <application>
        <meta-data android:name="circle_dialog_design_width" android:value="1200"/>
        <meta-data android:name="circle_dialog_design_height" android:value="1920"/>
    </application>
```

#注意
 * 继承基类`BaseCircleDialog`背景为透明，自定义layout时按需求设置背景

QQ交流群：553426264 [点击链接加入群Android-CircleDialog](https://jq.qq.com/?_wv=1027&k=47ptoki)

<img src="preview/qrcode.png"/>

#感谢
[AutoLayout-Android](https://github.com/DTHeaven/AutoLayout-Android)

# 版本

> 2.1.6 增加`text`及`title`参数`gravity`

> 2.1.5 修复已知Bug

> 2.1.4 minSdkVersion 15

> 2.1.2 修复已知Bug

> 2.1.1 修复`BaseCircleDialog`横竖屏恢复参数

> 2.1.0 Progress支持旋转样式

> 2.0.0 初始化版本
