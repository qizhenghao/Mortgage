package com.bruce.open.mortgage.activities;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bruce.open.mortgage.R;
import com.bruce.open.mortgage.Utils.SystemStatusManager;
import com.bruce.open.mortgage.adapter.BaseFramentPagerAdapter;
import com.bruce.open.mortgage.customViews.DesktopTabHost;
import com.bruce.open.mortgage.fragments.BaseFragment;
import com.bruce.open.mortgage.fragments.MortgageCalculateFragment;
import com.bruce.open.mortgage.fragments.MyMortgageFragment;
import com.bruce.open.mortgage.listeners.OnRefreshFragmentListener;
import com.bruce.open.mortgage.listeners.OnTabItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DesktopActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshFragmentListener, OnTabItemClickListener {

    public static final float NORMAL_BUSINESS_RATE = 4.90f;
    public static final float NORMAL_HOUSING_RATE = 3.25f;
    public static final float DEFAULT_FIRST_PAY = 3;
    public static final float DEFAULT_YEAR = 30;

    private ViewPager viewPager;
    private DesktopTabHost desktopTabHost;
    private BaseFramentPagerAdapter viewPagerAdapter;
    private List<BaseFragment> fragmentList;

    private MortgageCalculateFragment mortgageCalculateFragment;
    private MyMortgageFragment myMortgageFragment;
    private FrameLayout delayLayout;
    private boolean isFromWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.main);
        initViews();
        setListener();
        initData();
    }

    private void setListener() {

    }

    private void initData() {
        isFromWelcome = getIntent().getBooleanExtra("is_from_welcome", true);
        mortgageCalculateFragment = new MortgageCalculateFragment();
        myMortgageFragment = new MyMortgageFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mortgageCalculateFragment);
        fragmentList.add(myMortgageFragment);

        viewPagerAdapter = new BaseFramentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        desktopTabHost.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());

        desktopTabHost.setCurrentItem(0);
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        desktopTabHost = (DesktopTabHost) findViewById(R.id.tab_page_indicator);
        desktopTabHost.setViewIds(new int[]{R.id.tab_line_layout, R.id.tab_one, R.id.tab_two});
        delayLayout = new FrameLayout(DesktopActivity.this);
        delayLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        delayLayout.setBackgroundResource(R.drawable.welcome_bg);
        ((FrameLayout)getWindow().getDecorView()).addView(delayLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    // 需要setContentView之前调用
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R.color.blue_light);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

    @Override
    public void onRefresh(Class fragmentClass) {
        viewPagerAdapter.refresh(fragmentClass);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //实现了每次重新回到视野，先显示一张loading图
        if (isFromWelcome) {
            isFromWelcome = false;
            delayLayout.setVisibility(View.GONE);
        } else {
            delayLayout.setVisibility(View.VISIBLE);
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayLayout.setVisibility(View.GONE);
                }
            }, 1500);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);//不销毁activity，重新回到视野后保持原样
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTabItemSelected(int index, Bundle args) {
        desktopTabHost.setCurrentItem(index);
    }
}
