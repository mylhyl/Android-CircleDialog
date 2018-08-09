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
* [教程](https://github.com/mylhyl/Android-CircleDialog/wiki)

* [更多参数](https://github.com/mylhyl/Android-CircleDialog/tree/master/circledialog/src/main/java/com/mylhyl/circledialog/params)

# 说明

 * 此库自动将px转换百分比，由于 Dialog 布局一般只有微调，暂时只支持textSize，height，padding
 * 默认字体大小;Title、message、button、padding 的px在设计稿为 1080 * 1920 的尺寸
 * 也可自己定义，只需在manifest.xml中加入如下格式

```xml
        <meta-data android:name="circle_dialog_design_width" android:value="1200"/>
        <meta-data android:name="circle_dialog_design_height" android:value="1920"/>
```

# 注意
 * 依赖包：`appcompat-v7`、`cardview-v7`、`recyclerview-v7`
 * 继承基类`AbsBaseCircleDialog`背景为透明，自定义layout时按需求设置背景

# 感谢
[AutoLayout-Android](https://github.com/DTHeaven/AutoLayout-Android)；
