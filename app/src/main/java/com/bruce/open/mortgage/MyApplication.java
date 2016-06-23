package com.bruce.open.mortgage;

import android.app.Application;
import android.content.Context;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class MyApplication extends Application {

    private static MyApplication mContext;


    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
    }

    public static MyApplication getContext() {
        return mContext;
    }
}
