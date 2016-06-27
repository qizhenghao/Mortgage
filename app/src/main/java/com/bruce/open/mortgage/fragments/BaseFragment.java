package com.bruce.open.mortgage.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.bruce.open.mortgage.listeners.OnRefreshFragmentListener;

/**
 * @description 基本Fragment，其他Fraagmen要继承
 * 
 * @author qizhenghao
 * 
 *         data: 2014年12月27日10:55:40
 * */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public Activity mActivity;
    public View mContentView;
    public OnRefreshFragmentListener onRefreshFragmentListener;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        init();
	}

    /**全部初始化，包括view、data、listener*/
	public void init() {
		initView();
		initListener();
        initData();
    }

	/** 初始化view，activity创建时调用 */
	abstract protected void initView();

	/** 初始化data，activity创建时调用，在initView方法之后调用 */
	abstract protected void initData();

	/** 设置监听事件，activity创建时调用，在initView方法之后调用 */
	abstract protected void initListener();

    /** 刷新，在需要刷新fragment的时候自行调用 */
    public abstract void refresh();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
        onRefreshFragmentListener = (OnRefreshFragmentListener) activity;
    }
}
