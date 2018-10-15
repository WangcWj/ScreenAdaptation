# ScreenAdaptation
**背景:** 

[为什么我们要屏幕适配?](https://juejin.im/post/5bc0133f6fb9a05cd31ede40)   

**项目简介:** 

该项目使用注解方式实现了今日头条的适配方案，使用者可以自由的选择全局适配还是每个页面单独适配，也可取消一个界面的适配效果。再使用该注解的时候要注意一下兼容老项目，这也是今日头条适配方案的不足之处。 注解让代码更规范。功能还在逐渐完善，敬请期待！
**功能:**  

* 在Application使用以下代码去开启单个界面适配。

  ```java
  ScreenAdaptation.instance()
                  .setBaseWidth(360) // 设计图的宽度 单位dp  必填.
                  .setBaseHeight(640) // 设计图的高度 单位dp  必填.
                  .setAutoScreenAdaptation(false) // 不开启全局适配.
                  .create(this);
  ```

   再需要适配的界面使用`@ScreenAdaptation(value = IdentificationEnum. WIDTH)`注解去选择使用宽度适配还是高度适配。取值有: 

  ```java
  public enum IdentificationEnum {
      WIDTH,   //基于高度适配
      HEIGHT,  //基于高度适配
      IGNORE,  //忽略该界面的适配
      NULL     //不采取措施
  }
  ```

* 在Application使用以下代码去开启全局适配。   

  ```java
   ScreenAdaptation.instance()
                  .setBaseWidth(360)  // 设计图的宽度 单位dp  必填.
                  .setBaseHeight(640)  // 设计图的高度 单位dp  必填.
                  .setBase(IdentificationEnum.WIDTH) //全局以哪个纬度开始适配 取值有IdentificationEnum.WIDTH,和IdentificationEnum.HEIGHT.
                  .setAutoScreenAdaptation(true) //开启全局适配
                  .create(this);
  ```

  取消某页面的适配请用`@ScreenAdaptation(value = IdentificationEnum. IGNORE)`。

  注意：该注解使用在Activity上面。如：

  ```java
  @ScreenAdaptation(value = IdentificationEnum. WIDTH)
  public class MainActivity extends AppCompatActivity {
  }
  ```

​      

**日志:** : v1.0.0   date:08-10-15 : 基本代码提交。
