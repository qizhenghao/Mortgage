package com.bruce.open.mortgage.fragments;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bruce.open.mortgage.Model.EveryPayInfo;
import com.bruce.open.mortgage.Model.PayResult;
import com.bruce.open.mortgage.R;
import com.bruce.open.mortgage.Utils.SettingManager;
import com.bruce.open.mortgage.adapter.MyMortgageListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class MyMortgageFragment extends BaseFragment {

    private ListView mListView;
    private View mHeadView;
    private TextView sumPriceTv;
    private TextView sumLoanTv;
    private TextView sumPayPriceTv;
    private TextView sumInterestTv;
    private TextView firstPayTv;
    private TextView monthTv;
    private TextView everyMonthPayTv;
    private MyMortgageListAdapter mAdapter;



    private Pair pair;
    private PayResult result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_my_mortgage_layout, null);
        return mContentView;
    }

    @Override
    protected void initView() {
        mListView = (ListView) mContentView.findViewById(R.id.fragment_my_mortgage_lv);
        mHeadView = mActivity.getLayoutInflater().inflate(R.layout.list_headview_my_mortgage_layout, null);
        sumPriceTv = (TextView) mHeadView.findViewById(R.id.result_sum_price_tv);
        sumLoanTv = (TextView) mHeadView.findViewById(R.id.result_sum_loan_tv);
        sumPayPriceTv = (TextView) mHeadView.findViewById(R.id.result_repayment_sum_price_tv);
        sumInterestTv = (TextView) mHeadView.findViewById(R.id.result_sum_interest_tv);
        firstPayTv = (TextView) mHeadView.findViewById(R.id.result_fisrt_payment_price_tv);
        monthTv = (TextView) mHeadView.findViewById(R.id.result_sum_month_tv);
        everyMonthPayTv = (TextView) mHeadView.findViewById(R.id.result_every_month_payment_tv);
        mListView.addHeaderView(mHeadView);
    }

    @Override
    protected void initData() {
        pair = SettingManager.getInstance().getMyMortgageResultJson();
        try {
            result = PayResult.parse(new JSONObject((String) pair.second));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result == null)
            return;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        sumPriceTv.setText(result.sumPrice == 0 ? "0" : decimalFormat.format(result.sumPrice));
        sumLoanTv.setText(result.sumLoan == 0 ? "0" : decimalFormat.format(result.sumLoan));
        sumPayPriceTv.setText(result.sumPayPrice == 0 ? "0" : decimalFormat.format(result.sumPayPrice));
        sumInterestTv.setText(result.sumInterest == 0 ? "0" : decimalFormat.format(result.sumInterest));
        firstPayTv.setText(result.firstPay == 0 ? "0" : decimalFormat.format(result.firstPay));
        monthTv.setText(result.monthCount == 0 ? "0" : decimalFormat.format(result.monthCount));
        everyMonthPayTv.setText(result.everyMonthPay == 0 ? "0" : decimalFormat.format(result.everyMonthPay));
        if (mAdapter == null) {
            mAdapter = new MyMortgageListAdapter(mContext, EveryPayInfo.parse(result));
            mListView.setAdapter(mAdapter);
        } else
            mAdapter.setData(EveryPayInfo.parse(result));
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void refresh() {
        if (pair != null && SettingManager.getInstance().version != pair.first) {
            initData();
        }
    }

}
