package com.bruce.open.mortgage.fragments;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bruce.open.mortgage.Model.EveryPayInfo;
import com.bruce.open.mortgage.Model.PayResult;
import com.bruce.open.mortgage.R;
import com.bruce.open.mortgage.Utils.Methods;
import com.bruce.open.mortgage.Utils.SettingManager;
import com.bruce.open.mortgage.adapter.MyMortgageListAdapter;
import com.bruce.open.mortgage.customViews.DesktopTabHost;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by qizhenghao on 16/6/23.
 */
public class MyMortgageFragment extends BaseFragment implements View.OnClickListener{

    private ListView mListView;
    private View mHeadView;
    private TextView loanTypeTv;
    private TextView calculateTypeTv;
    private TextView payTypeTv;
    private TextView housingRateTv;
    private TextView bussRateTv;
    private TextView yearTv;

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
        loanTypeTv = (TextView) mHeadView.findViewById(R.id.my_mortgage_header_loan_type_tv);
        calculateTypeTv = (TextView) mHeadView.findViewById(R.id.my_mortgage_header_calculate_type_tv);
        payTypeTv = (TextView) mHeadView.findViewById(R.id.my_mortgage_header_pay_type_tv);
        housingRateTv = (TextView) mHeadView.findViewById(R.id.my_mortgage_header_housing_rate_tv);
        bussRateTv = (TextView) mHeadView.findViewById(R.id.my_mortgage_header_buss_rate_tv);
        yearTv = (TextView) mHeadView.findViewById(R.id.my_mortgage_header_year_tv);
        sumPriceTv = (TextView) mHeadView.findViewById(R.id.result_sum_price_tv);
        sumLoanTv = (TextView) mHeadView.findViewById(R.id.result_sum_loan_tv);
        sumPayPriceTv = (TextView) mHeadView.findViewById(R.id.result_repayment_sum_price_tv);
        sumInterestTv = (TextView) mHeadView.findViewById(R.id.result_sum_interest_tv);
        firstPayTv = (TextView) mHeadView.findViewById(R.id.result_fisrt_payment_price_tv);
        monthTv = (TextView) mHeadView.findViewById(R.id.result_sum_month_tv);
        everyMonthPayTv = (TextView) mHeadView.findViewById(R.id.result_every_month_payment_tv);
        mListView.addHeaderView(mHeadView);

        initEmptyView((ViewGroup) mContentView, LayoutInflater.from(mActivity).inflate(R.layout.empry_view_layout, null));
    }

    @Override
    protected void initData() {
        pair = SettingManager.getInstance().getMyMortgageResultJson();
        try {
            result = PayResult.parse(new JSONObject((String) pair.second));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result == null) {
            showEmptyView();
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        loanTypeTv.setText(result.loanType);
        calculateTypeTv.setText(result.calculateType);
        payTypeTv.setText(result.payType);
        housingRateTv.setText("公积金利率：" + (result.housingRate == 0 ? "" : decimalFormat.format(result.housingRate) + "%"));
        housingRateTv.setVisibility(result.housingRate == 0 ? View.GONE : View.VISIBLE);
        bussRateTv.setText("商业利率：" + (result.bussRate == 0 ? "" : decimalFormat.format(result.bussRate) + "%"));
        bussRateTv.setVisibility(result.bussRate == 0 ? View.GONE : View.VISIBLE);
        yearTv.setText("按揭年数：" + (result.monthCount / 12) + "年");

        sumPriceTv.setText(result.sumPrice == 0 ? "0" : decimalFormat.format(result.sumPrice));
        sumLoanTv.setText(result.sumLoan == 0 ? "0" : decimalFormat.format(result.sumLoan));
        sumPayPriceTv.setText(result.sumPayPrice == 0 ? "0" : decimalFormat.format(result.sumPayPrice));
        sumInterestTv.setText(result.sumInterest == 0 ? "0" : decimalFormat.format(result.sumInterest));
        firstPayTv.setText(result.firstPay == 0 ? "0" : decimalFormat.format(result.firstPay));
        monthTv.setText(result.monthCount == 0 ? "0" : decimalFormat.format(result.monthCount));
        everyMonthPayTv.setText(result.everyMonthPay == 0 ? "0" : decimalFormat.format(result.everyMonthPay));
        EveryPayInfo[] infos = null;
        int type;
        if (result.loanType.contains("组合")) {
            type = MyMortgageListAdapter.COMBINE_TYPE;
            PayResult result1 = null, result2 = null;
            Pair pair1 = SettingManager.getInstance().getMyMortgageBussResultJson();
            Pair pair2 = SettingManager.getInstance().getMyMortgageHousingResultJson();
            try {
                result1 = PayResult.parse(new JSONObject((String) pair1.second));
                result2 = PayResult.parse(new JSONObject((String) pair2.second));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            infos = EveryPayInfo.parse(result, result1, result2);
        } else {
            type = MyMortgageListAdapter.BUSS_TYPE|MyMortgageListAdapter.HOUSING_TYPE;
            infos = EveryPayInfo.parse(result);
        }
        if (mAdapter == null) {
            mAdapter = new MyMortgageListAdapter(mContext, infos, type);
            mListView.setAdapter(mAdapter);
        } else
            mAdapter.setData(infos);
    }

    @Override
    protected void initListener() {
        mContentView.findViewById(R.id.empty_view_btn).setOnClickListener(this);
    }

    @Override
    public void refresh() {
        if (pair != null && SettingManager.getInstance().version != pair.first) {
            initData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_view_btn:
                onTabItemClickListener.onTabItemSelected(DesktopTabHost.TabType.MORTGAGE_CALCULATE, null);
                break;
        }
    }

    @Override
    public void initEmptyView(ViewGroup emptyViewContainer, View emptyView) {
        ImageView imageView = (ImageView) emptyView.findViewById(R.id.empty_view_iv);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = (int) (Methods.getScreenWidth() * 0.618);
        params.height = (int) (params.width*1.4);
        super.initEmptyView(emptyViewContainer, emptyView);
    }
}
