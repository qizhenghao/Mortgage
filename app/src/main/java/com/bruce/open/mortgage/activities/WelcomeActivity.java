package com.bruce.open.mortgage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;

import com.baidu.mobads.AppActivity;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.bruce.open.mortgage.R;

/**
 * Created by qizhenghao on 16/6/30.
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);

//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                startActivity(new Intent(WelcomeActivity.this, DesktopActivity.class));
//                WelcomeActivity.this.finish();
//            }
//        };
//        timer.schedule(timerTask, 3000);

        //                new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(WelcomeActivity.this, DesktopActivity.class);
//                intent.putExtra("is_from_welcome", true);
//                startActivity(intent);
//                WelcomeActivity.this.finish();
//            }
//        }, 1500);

        // 设置'广告着陆页'劢作栏癿颜色主题
         AppActivity.setActionBarColorTheme(AppActivity.ActionBarColorTheme.ACTION_BAR_GREEN_THEME);
        RelativeLayout adsParent = (RelativeLayout) this
                .findViewById(R.id.welcome_activity_layout);
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
                Log.i("RSplashActivity", "onAdDismissed");
                jump2Desktop();
            }

            @Override
            public void onAdFailed(String arg0) {
                Log.i("RSplashActivity", "onAdFailed");
                jump2Desktop();
            }

            @Override
            public void onAdPresent() {
                Log.i("RSplashActivity", "onAdPresent");
            }

            @Override
            public void onAdClick() {
                Log.i("RSplashActivity", "onAdClick");
                //设置开屏可接受点击时,该回调可用
                jump2Desktop();
            }
        };

        String adPlaceId = "2673193";//重要:请填上你的代码位ID,否则无法请求到广告
        new SplashAd(this, adsParent, listener, adPlaceId, true);
    }

    private void jump2Desktop() {
        Intent intent = new Intent(WelcomeActivity.this, DesktopActivity.class);
        intent.putExtra("is_from_welcome", true);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }
}
