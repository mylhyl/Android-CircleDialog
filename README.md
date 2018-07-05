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

[ ![Download](https://api.bintray.com/packages/mylhyl/maven/circleDialog/images/download.svg) ](https://bintray.com/mylhyl/maven/circleDialog/_latestVersion)  将`latestVersion`替换为左则`Download`图标后面的数字

```xml
 compile 'com.mylhyl:circleDialog:latestVersion'
```

[下载APK体验](https://fir.im/sbvq)或手机扫描下面二维码

<img src="preview/qrdown.png"/>

# 使用
* 对话框属性
```java
        new CircleDialog.Builder(this)
            .setGravity(Gravity)//位置，默认居中
            .setCanceledOnTouchOutside(boolean)//触摸外部关闭，默认true
            .setCancelable(boolean)//返回键关闭，默认true
            .setWidth(from = 0.0, to = 1.0)//宽度
            .setMaxHeight(from = 0.0, to = 1.0)//最大高度
            .setRadius(radius)//圆角半径
            .setYoff()//对话框y坐标偏移
            .configDialog(ConfigDialog)//配置对话框更多的属性
            .setOnShowListener()//对话框显示监听事件
            .setOnDismissListener()//对话框关闭监听事件
            .setOnCancelListener()//对话框取消监听事件
```


* 普通对话框

```java
        new CircleDialog.Builder(this)
            .setTitle(title)
            .setTitleColor(@ColorInt color)//标题字体颜值 0x909090 or Color.parseColor("#909090")
            .setTitleIcon(@DrawableRes icon)//标题图标
            .configTitle(ConfigTitle)//配置标题更多的属性
            .setOnCreateTitleListener(OnCreateTitleListener)//如果 ConfigTitle 不能满足你，此监听器可以帮助你
            .setSubTitle(subTitle)//副标题
            .setSubTitleColor(@ColorInt color)//副标题字体色值 0x909090 or Color.parseColor("#909090")
            .configSubTitle(ConfigSubTitle)//配置标题更多的属性
            .setText(message)//内容
            .setTextColor(@ColorInt color)//内容字体色值
            .configText(ConfigText)//配置内容更多的属性
            .setOnCreateTextListener(OnCreateTextListener)//如果 ConfigText 不能满足你，此监听器可以帮助你
            .setPositive("确定", OnClickListener)
            .configPositive(ConfigButton)//配置确定按钮更多的属性
            .setNegative("取消", OnClickListener)
            .configNegative(ConfigButton)//配置取消按钮更多的属性
            .setNeutral("中间", OnClickListener)
            .configNeutral(ConfigButton)//配置中间按钮更多的属性
            .setOnCreateButtonListener(OnCreateButtonListener)//如果 configPositive configNegative configNeutral 不能满足你，此监听器可以帮助你
            .show();
```

* 列表对话框

```java
        new CircleDialog.Builder(this)
            //添加标题，参考普通对话框
            .setItems(items, OnItemClickListener)//Arrays或List to ListView
            .setItems(BaseAdapter, OnItemClickListener)//to ListView
            .setItems(items, OnRvItemClickListener)//Arrays或List to RecyclerView
            .setItems(items, layoutManager, OnRvItemClickListener)//to RecyclerView
            .setItems(Adapter, layoutManager)//to RecyclerView
            .setItems(Adapter, layoutManager, ItemDecoration)//to RecyclerView
            .setItemsManualClose(manualClose)//点击item是否关闭对话框，默认是关闭
            .configItems(ConfigItems)//配置列表更多的属性
            //添加按钮，参考普通对话框
            .show();
```

* 输入对话框
```java
        new CircleDialog.Builder(this)
            //添加标题，参考普通对话框
            .setInputText(text)//输入框默认文本
            .setInputText(text, hint)//输入框默认文本，提示
            .setInputHint(hint)//提示
            .setInputHeight(height)//输入框高度
            .setInputCounter(maxLen)//输入框的最大字符数，默认格式在输入右下角例如：20
            .setInputCounterColor(color)//最大字符数文字的颜色值
            .autoInputShowKeyboard();//自动弹出键盘
            //输入框的最大字符数
            //OnInputCounterChangeListener灵活配置格式例如：maxLen - currentLen + "/" + maxLen 最终效果是：10/20
            .setInputCounter(maxLen, OnInputCounterChangeListener)
            .setInputManualClose(boolean)//点击确定按钮时是否关闭对话框，默认关闭
            .configInput(ConfigInput)//配置输入框更多的属性
            //添加按钮，参考普通对话框
            .show();
```

* 进度对话框
```java
        new CircleDialog.Builder(this)
            //添加标题，参考普通对话框
            .setProgressStyle(style)//STYLE_HORIZONTAL 或 STYLE_SPINNER
            .setProgressText(text)// style = 水平样式时，text支持String.format() 例如：已经下载%s
            .setProgress(max, progress)//水平样式的最大刻度和当前刻度
            .setProgressDrawable(@DrawableRes int progressDrawableId)//自定义进度样式资源文件
            .setProgressHeight(height)//进度条的高度
            .configProgress(ConfigProgress)//配置进度框更多的属性
            //添加按钮，参考普通对话框
            .show();
```

* lottie动画框
```java
        new CircleDialog.Builder()
            .setTitle("提示")
            .setWidth(0.7f)
            .setLottieAnimation("loading.json")//动画文件 assets 中
            .setLottieLoop(true)//循环
            .playLottieAnimation()//动画开始
            .setLottieSize(width, height)//只是动画宽高
            .setLottieText("正在加载...")
            .setOnCreateLottieListener(OnCreateLottieListener)//更多属性配置
            .show(getSupportFragmentManager());
``

* 自定义对话框  
1、完全重新设计可继承`BaseCircleDialog`，此基类提供了对话框的常用属性  
2、如果只是对话框内容的部分满足不了你，可以使用下面的方式
```java
        new CircleDialog.Builder(this)
            //不影响顶部标题和底部按钮部份
            .setBodyView(@LayoutRes int bodyViewId, OnCreateBodyViewListener listener)
```

* 全局配置属性值
```java
        //建议在Application配置
        static {
            CircleDimen.DIALOG_RADIUS = 20;
            //CircleColor.
        }
```
[更多参数](https://github.com/mylhyl/Android-CircleDialog/tree/master/circledialog/src/main/java/com/mylhyl/circledialog/params)

#说明

 * 此库自动将px转换百分比，由于 Dialog 布局一般只有微调，暂时只支持textSize，height，padding
 * 默认字体大小;Title、message、button、padding 的px在设计稿为 1080 * 1920 的尺寸
 * 也可自己定义，只需在manifest.xml中加入如下格式

```xml
        <meta-data android:name="circle_dialog_design_width" android:value="1200"/>
        <meta-data android:name="circle_dialog_design_height" android:value="1920"/>
```

#注意
 * 继承基类`BaseCircleDialog`背景为透明，自定义layout时按需求设置背景

#感谢
[AutoLayout-Android](https://github.com/DTHeaven/AutoLayout-Android)；
