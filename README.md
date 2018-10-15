# ScreenAdaptation
**背景:**![为什么我们要屏幕适配?](https://juejin.im/post/5bc0133f6fb9a05cd31ede40).   

**项目简介:** 该项目使用注解方式实现了今日头条的适配方案,使用者可以自由的选择全局适配还是每个页面单独适配,也可取消一个界面的适配效果.再使用该注解的时候要注意一下兼容老项目,这也是今日头条适配方案的不足之处.   
**功能:**  
* 再Application使用以下代码去开启单个界面适配.
```
   ScreenAdaptation.instance()
                .setBaseWidth(360)
                .setBaseHeight(640)
                .setAutoScreenAdaptation(false)
                .create(this);

```
**日志:** : v1.0.0   date:08-10-15 : 基本代码提交.
