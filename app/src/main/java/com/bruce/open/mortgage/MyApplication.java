package com.bruce.open.mortgage;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class MyApplication extends Application {

    private static MyApplication mContext;
    private static Handler mApplicationHandler;


    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
    }

    public static MyApplication getContext() {
        return mContext;
    }

    public static Handler getApplicationHandler() {
        if (mApplicationHandler == null) {
            mApplicationHandler = new Handler(Looper.getMainLooper());
        }
        return mApplicationHandler;
    }
}
