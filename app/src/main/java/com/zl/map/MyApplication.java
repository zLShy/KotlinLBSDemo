package com.zl.map;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imkit.RongIM;

/**
 * Created by zhangli on 2018/6/14.
 */

public class MyApplication extends Application {
    private static Context mContext;
    private static DisplayImageOptions options;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        RongIM.init(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static DisplayImageOptions getOptions() {
        return options;
    }
}
