package com.riverlet.ui.test.app;

import android.app.Application;
import android.content.Context;

import com.riverlet.ui.RiverletUI;

/**
 * Created by liujian on 17-7-1.
 */

public class RiverletUIApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        RiverletUI.get().init(this);
    }

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onTerminate() {
        RiverletUI.get().uninit();
        super.onTerminate();
    }
}
