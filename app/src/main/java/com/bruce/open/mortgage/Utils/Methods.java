package com.bruce.open.mortgage.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

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


    public static int computePixelsWithDensity(int dp) {
        float scale;
//        if (density!=0){
//            scale = density;
//        }else{
        scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
//        }
        return (int) (dp * scale + 0.5);
    }

    /**
     * 显示Toast
     *
     * @param text
     * @param lengthLong
     */
    public static void showToast(final CharSequence text, final boolean lengthLong) {
        Runnable update = new Runnable() {
            public void run() {
                if (Variables.gToast == null && MyApplication.getContext() != null) {
                    Variables.gToast = Toast.makeText(MyApplication.getContext().getApplicationContext(), "",
                            Toast.LENGTH_LONG);
                }
                if (Variables.gToast != null) {
                    if (text == null) {
                        Variables.gToast.cancel();
                    } else {
                        Variables.gToast.setText(text);
                        Variables.gToast.setDuration(lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                        Variables.gToast.show();
                    }
                }
            }
        };
        MyApplication.getApplicationHandler().post(update);
    }
}
