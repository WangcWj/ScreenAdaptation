package cn.screen.adaptation.screen_lib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.screen_lib.R;

/**
 * Created to: 用来设置系统DisplayMetrics有关的数据.
 * @author WANG
 * @date 2018/9/18
 */

public class ScreenAdaptationUtils {

    public static void setCustomDensity(@NonNull Activity activity, @NonNull Application application , float defaultDpi,IdentificationEnum base){
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if(displayMetrics.densityDpi != defaultDpi) {
            return;
        }
        boolean isBaseWidth;
        switch (base) {
            case WIDTH:
                isBaseWidth = true;
                break;
            case HEIGHT:
                isBaseWidth = false;
                break;
            default:
                isBaseWidth = false;
                break;
        }
        setDensity(displayMetrics,activity,isBaseWidth);
    }

    public static void recoverDensity(@NonNull Activity activity, @NonNull Application application ,float defaultDpi,float defaultDensity){
        DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        float density = systemDm.densityDpi;
        if(density == defaultDpi) {
            return;
        }

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = systemDm.density;
        activityDisplayMetrics.scaledDensity = systemDm.scaledDensity;
        activityDisplayMetrics.densityDpi = systemDm.densityDpi;

        DisplayMetrics appDm = application.getResources().getDisplayMetrics();
        appDm.density = systemDm.density;
        appDm.scaledDensity = systemDm.scaledDensity;
        appDm.densityDpi  = systemDm.densityDpi;

    }

    private static void setDensity(DisplayMetrics appDm,Activity activity,boolean isBaseWidth){
        DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        float targetDensity;
        if(isBaseWidth) {
            if(0 == DesignUtils.designWidth){
                throw new NullPointerException("你必须先设置设计图的宽度");
            }
            targetDensity = activityDm.widthPixels * 1.0f / DesignUtils.designWidth;
        }else {
            if(0 == DesignUtils.designHeight){
                throw new NullPointerException("你必须先设置设计图的高度");
            }
            targetDensity = activityDm.heightPixels * 1.0f / DesignUtils.designHeight;
        }
        int targetDensityDpi = (int) (160 * targetDensity);

        activityDm.density = targetDensity;
        activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density);
        activityDm.densityDpi = targetDensityDpi;

        appDm.density =  activityDm.density;
        appDm.scaledDensity = activityDm.scaledDensity;
        appDm.densityDpi = activityDm.densityDpi;


    }

}
