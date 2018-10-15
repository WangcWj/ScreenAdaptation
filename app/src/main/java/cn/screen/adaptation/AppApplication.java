package cn.screen.adaptation;

import android.app.Application;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.screen_lib.ScreenAdaptation;

/**
 * Created to:
 * @author WANG
 * @date 2018/9/27
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdaptation.instance()
                .setBaseWidth(360)
                .setBaseHeight(640)
                .setAutoScreenAdaptation(false)
                .create(this);
    }

}
