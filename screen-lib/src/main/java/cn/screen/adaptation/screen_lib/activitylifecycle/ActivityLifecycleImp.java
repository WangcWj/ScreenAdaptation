package cn.screen.adaptation.screen_lib.activitylifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created to :Activity启动生命周期的监听.
 *
 * @author WANG
 * @date 2018/10/10
 */

public class ActivityLifecycleImp implements Application.ActivityLifecycleCallbacks {

    ActivityLifeCircleCallback activityLifeCircleCallback;

    public ActivityLifecycleImp(ActivityLifeCircleCallback activityLifeCircleCallback) {
        this.activityLifeCircleCallback = activityLifeCircleCallback;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityLifeCircleCallback.onActivityCreated(activity,savedInstanceState);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityLifeCircleCallback.onActivityDestroyed(activity);
    }
}
