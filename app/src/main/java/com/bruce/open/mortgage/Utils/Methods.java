package com.bruce.open.mortgage.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.bruce.open.mortgage.MyApplication;

/**
 * Created by qizhenghao on 16/7/1.
 */
public class Methods {

    public static int getScreenWidth() {
//        DisplayMetrics metric = new DisplayMetrics();

        WindowManager wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metric);
//        density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0）
//        scaledDensity = metric.scaledDensity;
        return wm.getDefaultDisplay().getWidth();
    }
}
