package com.bruce.open.mortgage;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.bruce.open.mortgage.adapter.BaseFramentPagerAdapter;
import com.bruce.open.mortgage.customViews.TabViewPagerIndicator;
import com.bruce.open.mortgage.fragments.MortgageCalculateFragment;
import com.bruce.open.mortgage.fragments.MyMortgageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final float NORMAL_BUSINESS_RATE = 4.90f;
    public static final float NORMAL_HOUSING_RATE = 3.25f;
    public static final float DEFAULT_FIRST_PAY = 3;
    public static final float DEFAULT_YEAR = 30;

    private ViewPager viewPager;
    private TabViewPagerIndicator tabViewPagerIndicator;
    private BaseFramentPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;

    private MortgageCalculateFragment mortgageCalculateFragment;
    private MyMortgageFragment myMortgageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        setListener();
        initData();
    }

    private void setListener() {

    }

    private void initData() {
        mortgageCalculateFragment = new MortgageCalculateFragment();
        myMortgageFragment = new MyMortgageFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mortgageCalculateFragment);
        fragmentList.add(myMortgageFragment);

        viewPagerAdapter = new BaseFramentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        tabViewPagerIndicator.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());

        tabViewPagerIndicator.setCurrentItem(0);
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabViewPagerIndicator = (TabViewPagerIndicator) findViewById(R.id.tab_page_indicator);
        tabViewPagerIndicator.setViewIds(new int[]{R.id.tab_line_layout, R.id.tab_one, R.id.tab_two});
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
}
