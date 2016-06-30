package com.bruce.open.mortgage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by qizhenghao on 16/6/30.
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//        WelcomeActivity.this.finish();

//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                WelcomeActivity.this.finish();
//            }
//        };
//        timer.schedule(timerTask, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("is_from_welcome", true);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 1500);
    }
}
