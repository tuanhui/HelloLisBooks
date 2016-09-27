package tuantuan.com.xiangshengxp.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by chentuanhui on 2016/9/23.
 */

public class App extends Application {
    private static Context mApp;

    public static Context getContext() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
