package cn.screen.adaptation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *     全局适配 取消{@link IgnoreScreenAdapter}
 *  
 * <p/>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface ScreenAdaptation {
  IdentificationEnum value() ;
}
