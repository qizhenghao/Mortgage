package com.bruce.open.mortgage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdSettings;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.bruce.open.mortgage.R;

import org.json.JSONObject;

/**
 * Created by qizhenghao on 16/7/5.
 */
public class RecommendFragment extends BaseFragment {

    private LinearLayout lLayout;
    private AdView adView1;
    private AdView adView2;
    private AdView adView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_recommend_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        lLayout = (LinearLayout) mContentView.findViewById(R.id.fragment_recommend_ll);
        AdSettings.setKey(new String[]{"房贷", "首付贷", "贷款"});
        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        adView1 = new AdView(mActivity, "2674891");
        lLayout.addView(adView1, lllp);
        adView2 = new AdView(mActivity, "2674895");
        lLayout.addView(adView2, lllp);
        adView3 = new AdView(mActivity, "2675176");
        lLayout.addView(adView3, lllp);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        adView1.setListener(new AdViewListener() {

            @Override
            public void onAdReady(AdView adView) {

            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                Log.d("Bruce", "onAdShow ");
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {

            }

            @Override
            public void onAdFailed(String s) {
                Log.d("Bruce", "onAdFailed " + s);
            }

            @Override
            public void onAdSwitch() {

            }
        });

        adView2.setListener(new AdViewListener() {

            @Override
            public void onAdReady(AdView adView) {

            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                Log.d("Bruce", "onAdShow ");
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {

            }

            @Override
            public void onAdFailed(String s) {
                Log.d("Bruce", "onAdFailed " + s);
            }

            @Override
            public void onAdSwitch() {

            }
        });

        adView3.setListener(new AdViewListener() {

            @Override
            public void onAdReady(AdView adView) {

            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                Log.d("Bruce", "onAdShow ");
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {

            }

            @Override
            public void onAdFailed(String s) {
                Log.d("Bruce", "onAdFailed " + s);
            }

            @Override
            public void onAdSwitch() {

            }
        });
    }

    @Override
    public void refresh() {

    }
}
