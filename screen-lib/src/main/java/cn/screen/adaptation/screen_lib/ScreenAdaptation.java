package cn.screen.adaptation.screen_lib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.screen_lib.activitylifecycle.ActivityLifeCircleCallback;
import cn.screen.adaptation.screen_lib.activitylifecycle.ActivityLifecycleImp;
import cn.screen.adaptation.screen_lib.utils.DesignUtils;
import cn.screen.adaptation.screen_lib.utils.ScreenAdaptationUtils;

/**
 * Created to : 屏幕适配入口.
 *
 * @author WANG
 * @date 2018/10/10
 */

public class ScreenAdaptation implements ActivityLifeCircleCallback {

    private final String CLASS_NAME = "cn.screen.adaptation.annotation.Screen_BaseWidth";
    private final String LOG_HEADER = "-------";
    private final String LOG_MESSAGE = LOG_HEADER + "并未使用@ScreenAdaptation注解";


    private String TAG = "WANG";
    private HashMap<String, IdentificationEnum> mBaseWidthActivitys = new HashMap<>();
    private ActivityLifecycleImp mActivityLifecycleImp;

    private float mDefaultDpi;
    private float mDefaultDensity;

    private boolean mAutoScreenAdaptation = true;
    private boolean mUserDebug = true;
    private IdentificationEnum mBase = IdentificationEnum.NULL;


    public static ScreenAdaptation instance() {
        ScreenAdaptation screenAdaptation = new ScreenAdaptation();
        return screenAdaptation;
    }

    public void create(Application application) {
        setDefaultDpi(application);
        register(application);
        initAnnotation(application);
        if (mAutoScreenAdaptation && mBase == IdentificationEnum.NULL) {
            //当自动全局适配的话需要区分是高度适配还是宽度适配.
            log(LOG_HEADER + "请设置适配那个纬度 @IdentificationEnum.WIDTH or @IdentificationEnum.HEIGHT ");
        }
    }

    public ScreenAdaptation setBase(IdentificationEnum base) {
        mBase = base;
        return this;
    }

    public ScreenAdaptation setBaseHeight(int mBaseHeight) {
        DesignUtils.setDesignHeight(mBaseHeight);
        return this;
    }

    public ScreenAdaptation setBaseWidth(int mBaseWidth) {
        DesignUtils.setDesignWidth(mBaseWidth);
        return this;
    }

    public ScreenAdaptation setAutoScreenAdaptation(boolean mAutoScreenAdaptation) {
        this.mAutoScreenAdaptation = mAutoScreenAdaptation;
        return this;
    }

    public ScreenAdaptation setTAG(String TAG) {
        this.TAG = TAG;
        return this;
    }

    public ScreenAdaptation isDebug(boolean userDebug) {
        this.mUserDebug = userDebug;
        return this;
    }

    private void setDefaultDpi(Application application) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        mDefaultDensity = displayMetrics.density;
        mDefaultDpi = displayMetrics.densityDpi;
    }

    private void register(Application application) {
        mActivityLifecycleImp = new ActivityLifecycleImp(this);
        application.registerActivityLifecycleCallbacks(mActivityLifecycleImp);
    }

    public void setBaseWidthActivitys(HashMap activitys) {
        this.mBaseWidthActivitys.clear();
        this.mBaseWidthActivitys.putAll(activitys);
    }

    private void initAnnotation(Application application) {
        try {
            Class<?> aClass = application.getClassLoader().loadClass(CLASS_NAME);
            if (null != aClass) {
                Constructor<?> constructor = aClass.getConstructor(ScreenAdaptation.class);
                if (null == constructor) {
                    log(LOG_MESSAGE);
                    return;
                }
                constructor.newInstance(this);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log(LOG_MESSAGE + e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log(LOG_MESSAGE + e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log(LOG_MESSAGE + e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            log(LOG_MESSAGE + e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            log(LOG_MESSAGE + e);
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String className = activity.getComponentName().getClassName();
        if (mAutoScreenAdaptation && mBase != IdentificationEnum.NULL) {
            //当全局适配的时候 只考虑忽略的注解
            ignoreTargetActivity(activity, className);
        } else {
            //不是全局适配的时候只考虑 WIDTH 和HEIGHT的注解
            setTargetActivityAdaptation(activity, className);
        }
    }

    /**
     * 排查
     *
     * @param activity
     * @param className
     */
    private void ignoreTargetActivity(Activity activity, String className) {
        //有可能使用了其他的注解  如果适配全局的话是不建议使用别的注解的 但是你用了也没事 我这里不会处理你的
        if (mBaseWidthActivitys.containsKey(className)) {
            IdentificationEnum identificationEnum = mBaseWidthActivitys.get(className);
            if (identificationEnum == IdentificationEnum.IGNORE) {
                ScreenAdaptationUtils.recoverDensity(activity, activity.getApplication(), mDefaultDpi, mDefaultDensity);
            } else {
                ScreenAdaptationUtils.setCustomDensity(activity, activity.getApplication(), mDefaultDpi, mBase);
            }
        } else {
            ScreenAdaptationUtils.setCustomDensity(activity, activity.getApplication(), mDefaultDpi, mBase);
        }
    }

    private void setTargetActivityAdaptation(Activity activity, String className) {
        if (mBaseWidthActivitys.containsKey(className)) {
            IdentificationEnum identification = mBaseWidthActivitys.get(className);
            ScreenAdaptationUtils.setCustomDensity(activity, activity.getApplication(), mDefaultDpi, identification);
        } else {
            ScreenAdaptationUtils.recoverDensity(activity, activity.getApplication(), mDefaultDpi, mDefaultDensity);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private void log(String message) {
        if (mUserDebug) {
            Log.e(TAG, message);
        }
    }
}
