package com.bruce.open.mortgage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.bruce.open.mortgage.R;
import com.bruce.open.mortgage.adapter.MyMortgageListAdapter;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class MyMortgageFragment extends BaseFragment {

    private ListView mListView;
    private View mHeadView;
    private MyMortgageListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_my_mortgage_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        mListView = (ListView) mContentView.findViewById(R.id.fragment_my_mortgage_lv);
        mHeadView = mActivity.getLayoutInflater().inflate(R.layout.list_headview_my_mortgage_layout, null);
        mListView.addHeaderView(mHeadView);
    }

    @Override
    protected void initData() {
        mAdapter = new MyMortgageListAdapter(mContext, MyMortgageListAdapter.YEAR, new float[120]);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }
}
