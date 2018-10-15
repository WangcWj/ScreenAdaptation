package cn.screen.adaptation.screen_lib.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created to :Activity启动生命周期的监听.
 *
 * @author WANG
 * @date 2018/10/10
 */

public interface ActivityLifeCircleCallback {
    /**
     * 再Activity的onCreate方法之前调用
     * @param activity
     * @param savedInstanceState
     */
    void onActivityCreated(Activity activity, Bundle savedInstanceState);

    /**
     * 再Activity的onDestroyed方法之前调用
     * @param activity
     */
    void onActivityDestroyed(Activity activity);
}
