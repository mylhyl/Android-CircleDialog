# Android-CircleDialog
   相比SuperDialog而言，此库支持横竖屏切换
   基于`DialogFragment`封装，支持自定义边框圆角、背景透明度、字体大小与色值等。
   列表选择框可以接收`List`与`Arrays`的数据源，详细见`demo`。
   初衷是掌握知识点，此库不一定适合你的产品整体风格，当然能够适合你的项目最好不过，有建议和不足之处欢迎骚扰。
# 知识点
  全代码创建`shape`、`selector`、`Layout`，三大`Layout`不用多讲，肯定都会的，主要是`Drawable`所使用类如下：
  * `ShapeDrawable`、`RoundRectShape`、`GradientDrawable`、`ClipDrawable`、`LayerDrawable`、`StateListDrawable`

# 效果图
<img src="preview/gif.gif" width="240px"/>

# 引入
```xml
 compile 'com.mylhyl:circleDialog:2.0.0'
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
[更多具体参数](https://github.com/mylhyl/Android-CircleDialog/tree/master/circledialog/src/main/java/com/mylhyl/circledialog/params)

#说明

	* 此库自动将px转换百分比，由于 Dialog 布局一般只有微调，暂时只支持textSize，height，padding
	* 默认字体大小;Title、message、button、padding 的px在设计稿为 1080 * 1920 的尺寸
	* 也可自己定义，只需在manifest.xml中加入如下格式

```xml
<meta-data android:name="design_width" android:value="1200"/>
<meta-data android:name="design_height" android:value="1920"/>
```

QQ交流群:435173211

#感谢
[AutoLayout-Android](https://github.com/DTHeaven/AutoLayout-Android)

# 版本

> 2.0.0 初始化版本
