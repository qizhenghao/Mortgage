package com.bruce.open.mortgage;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bruce.open.mortgage.IPayStrategy.EqualCorpusStrategy;
import com.bruce.open.mortgage.IPayStrategy.EqualInterestStrategy;
import com.bruce.open.mortgage.IPayStrategy.PayContext;
import com.bruce.open.mortgage.Model.PayResult;
import com.bruce.open.mortgage.Utils.SettingManager;
import com.bruce.open.mortgage.adapter.BaseSpinnerAdapter;
import com.bruce.open.mortgage.customViews.CustomScrollView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final float NORMAL_BUSINESS_RATE = 4.90f;
    public static final float NORMAL_HOUSING_RATE = 3.25f;
    public static final float DEFAULT_FIRST_PAY = 3;
    public static final float DEFAULT_YEAR = 30;

    private RadioGroup mortgageTypeRG;
    private RadioGroup calculateTypeRG;
    private EditText unitPriceEdit;
    private EditText areaEdit;
    private Spinner firstPaySp;
    private EditText sumLoanEdit;
    private EditText sumBussLoanEdit;
    private EditText sumHousingLoanEdit;

    private Spinner yearSp;
    private Spinner bussRateSp;
    private Spinner housingRateSp;

    private RadioGroup payTypeRG;

    private Button calculateBtn;
    private Button refillBtn;

    private EditText resSumPriceEdit;
    private EditText resSumLoanEdit;
    private EditText resSumPaymentEdit;
    private EditText resSumInterestEdit;
    private EditText resfirstPaymentEdit;
    private EditText resSumMonthEdit;
    private EditText resEveryMonthPaymentEdit;

    private float[] firstPayArr = new float[10];
    private float[] yearArr = new float[30];
    private float[] bussRateArr = new float[]{5.25f, 5.15f, 5.00f, 4.90f, 4.80f, 4.70f, 4.60f, 4.50f, 4.40f, 4.30f,
                                            4.20f, 4.10f, 4.00f};
    private float[] housingRateArr = new float[]{4.00f, 3.90f, 3.80f, 3.70f, 3.60f, 3.50f, 3.40f, 3.30f, 3.25f};

    private BaseSpinnerAdapter firstPayAdapter;
    private BaseSpinnerAdapter yearAdapter;
    private BaseSpinnerAdapter bussRateAdapter;
    private BaseSpinnerAdapter housingRateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListener();
        initData();
    }

    private void setListener() {
        mortgageTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mortgage_type_housing_fund_rb:
                        findViewById(R.id.calculate_type_no_combine_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.calculate_type_combine_layout).setVisibility(View.GONE);
                        findViewById(R.id.housing_rate_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.buss_rate_layout).setVisibility(View.GONE);
                        findViewById(R.id.calculate_type_sum_layout).setVisibility(calculateTypeRG.getCheckedRadioButtonId() == R.id.calculate_type_sum_mortgage_rb ? View.VISIBLE : View.GONE);
                        break;
                    case R.id.mortgage_type_business_rb:
                        findViewById(R.id.housing_rate_layout).setVisibility(View.GONE);
                        findViewById(R.id.buss_rate_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.calculate_type_no_combine_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.calculate_type_combine_layout).setVisibility(View.GONE);
                        findViewById(R.id.calculate_type_sum_layout).setVisibility(calculateTypeRG.getCheckedRadioButtonId() == R.id.calculate_type_sum_mortgage_rb ? View.VISIBLE : View.GONE);
                        break;
                    case R.id.mortgage_type_combine_rb:
                        findViewById(R.id.calculate_type_no_combine_layout).setVisibility(View.GONE);
                        findViewById(R.id.calculate_type_combine_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.housing_rate_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.buss_rate_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.calculate_type_sum_layout).setVisibility(View.GONE);
                        break;
                }
            }
        });
        calculateTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.calculate_type_sum_mortgage_rb) {
                    findViewById(R.id.calculate_type_unit_layout).setVisibility(View.GONE);
                    findViewById(R.id.calculate_type_sum_layout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.calculate_type_unit_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.calculate_type_sum_layout).setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.calculate_btn).setOnClickListener(this);
        findViewById(R.id.fill_again_btn).setOnClickListener(this);
    }

    private void initData() {
        SettingManager settingManager = SettingManager.getInstance();
        mortgageTypeRG.check(settingManager.getLoanType());
        calculateTypeRG.check(settingManager.getCalculateType());
        payTypeRG.check(settingManager.getPayType());

        firstPayAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.FIRST_PAYMENT, firstPayArr);
        yearAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.YEAR, yearArr);
        bussRateAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.BUSSINESS_RATE, bussRateArr);
        housingRateAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.HOUSING_RATE, housingRateArr);

        firstPaySp.setAdapter(firstPayAdapter);
        firstPaySp.setSelection((int) settingManager.getFirstPay());

        yearSp.setAdapter(yearAdapter);
        yearSp.setSelection((int) settingManager.getYear() - 1);

        initSpinnerSelection(bussRateSp, bussRateAdapter, settingManager.getBussRate(), bussRateArr);

        initSpinnerSelection(housingRateSp, housingRateAdapter, settingManager.getHousingRate(), housingRateArr);

        unitPriceEdit.setText(settingManager.getUnitPrice());
        areaEdit.setText(settingManager.getArea());
        sumLoanEdit.setText(settingManager.getSumLoan());
        sumBussLoanEdit.setText(settingManager.getSumBussLoan());
        sumHousingLoanEdit.setText(settingManager.getSumHousingLoan());
    }

    private void initSpinnerSelection(Spinner spinner, BaseSpinnerAdapter adapter, float selectValue, float[] arr) {
        spinner.setAdapter(adapter);
        for (int i=0;i<arr.length;i++) {
            if (arr[i] == selectValue) {
                spinner.setSelection(i);
            }
        }
    }

    private void initViews() {
        mortgageTypeRG = (RadioGroup) findViewById(R.id.mortgage_type_rg);
        calculateTypeRG = (RadioGroup) findViewById(R.id.calculate_type_rg);
        unitPriceEdit = (EditText) findViewById(R.id.calculate_unit_price_et);
        areaEdit = (EditText) findViewById(R.id.calculate_area_et);
        firstPaySp = (Spinner) findViewById(R.id.calculate_unit_price_payment_ratio_spinner);
        sumLoanEdit = (EditText) findViewById(R.id.calculate_sum_price_et);
        sumBussLoanEdit = (EditText) findViewById(R.id.calculate_type_combine_buss_et);
        sumHousingLoanEdit = (EditText) findViewById(R.id.calculate_type_combine_housing_et);
        sumLoanEdit = (EditText) findViewById(R.id.calculate_sum_price_et);
        yearSp = (Spinner) findViewById(R.id.mortgage_year_spinner);
        bussRateSp = (Spinner) findViewById(R.id.mortgage_rate_spinner);
        housingRateSp = (Spinner) findViewById(R.id.housing_rate_spinner);
        payTypeRG = (RadioGroup) findViewById(R.id.repayment_type_rg);
        calculateBtn = (Button) findViewById(R.id.calculate_btn);
        refillBtn = (Button) findViewById(R.id.fill_again_btn);
        resSumPriceEdit = (EditText) findViewById(R.id.result_sum_price_edit);
        resSumLoanEdit = (EditText) findViewById(R.id.result_sum_loan_edit);
        resSumPaymentEdit = (EditText) findViewById(R.id.result_repayment_sum_price_edit);
        resSumInterestEdit = (EditText) findViewById(R.id.result_sum_interest_edit);
        resfirstPaymentEdit = (EditText) findViewById(R.id.result_fisrt_payment_price_edit);
        resSumMonthEdit = (EditText) findViewById(R.id.result_sum_month_edit);
        resEveryMonthPaymentEdit = (EditText) findViewById(R.id.result_every_month_payment_edit);
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
        switch (v.getId()) {
            case R.id.calculate_btn:
                PayResult result = calculate();
                setResultViewData(result);
                break;
            case R.id.fill_again_btn:
                unitPriceEdit.setText("");
                areaEdit.setText("");
                sumLoanEdit.setText("");
                sumBussLoanEdit.setText("");
                sumHousingLoanEdit.setText("");
                break;
        }
    }

    @Nullable
    private PayResult calculate() {
        double unitPrice = 0, area = 0, sumLoan = 0, bussRate, housingRate , sumBussLoan = 0, sumHousingLoan = 0;
        if (mortgageTypeRG.getCheckedRadioButtonId() == R.id.mortgage_type_combine_rb) {
            sumBussLoan = getLoanFromEdit(sumBussLoanEdit);
            sumHousingLoan = getLoanFromEdit(sumHousingLoanEdit);
        } else {
            if (calculateTypeRG.getCheckedRadioButtonId() == R.id.calculate_type_sum_mortgage_rb) {
                sumLoan = getLoanFromEdit(sumLoanEdit);
            } else {
                unitPrice = Double.parseDouble("".equals(unitPriceEdit.getText().toString().trim())?"0":unitPriceEdit.getText().toString().trim());
                area = Double.parseDouble("".equals(areaEdit.getText().toString().trim())?"0": areaEdit.getText().toString().trim());
            }
        }

        double firstPay = firstPaySp.getSelectedItemPosition()/10d;

        int year = yearSp.getSelectedItemPosition()+1;

        PayResult result = null;
        switch (mortgageTypeRG.getCheckedRadioButtonId()) {
            case R.id.mortgage_type_business_rb:
                bussRate = bussRateArr[bussRateSp.getSelectedItemPosition()]/100d;
                PayContext bussContext = new PayContext(payTypeRG.getCheckedRadioButtonId() == R.id.repayment_type_interest_rb ? new EqualInterestStrategy(unitPrice, area, sumLoan, firstPay, year, bussRate) : new EqualCorpusStrategy(unitPrice, area, sumLoan, firstPay, year, bussRate));
                result = bussContext.operate();
                break;
            case R.id.mortgage_type_housing_fund_rb:
                housingRate = housingRateArr[housingRateSp.getSelectedItemPosition()]/100d;
                PayContext housingContext = new PayContext(payTypeRG.getCheckedRadioButtonId() == R.id.repayment_type_interest_rb ? new EqualInterestStrategy(unitPrice, area, sumLoan, firstPay, year, housingRate) : new EqualCorpusStrategy(unitPrice, area, sumLoan, firstPay, year, housingRate));
                result = housingContext.operate();
                break;
            case R.id.mortgage_type_combine_rb:
                bussRate = bussRateArr[bussRateSp.getSelectedItemPosition()]/100d;
                housingRate = housingRateArr[housingRateSp.getSelectedItemPosition()]/100d;
                PayContext combineBussContext = new PayContext(payTypeRG.getCheckedRadioButtonId() == R.id.repayment_type_interest_rb ? new EqualInterestStrategy(unitPrice, area, sumBussLoan, firstPay, year, bussRate) : new EqualCorpusStrategy(unitPrice, area, sumBussLoan, firstPay, year, bussRate));
                PayContext combineHousingContext = new PayContext(payTypeRG.getCheckedRadioButtonId() == R.id.repayment_type_interest_rb ? new EqualInterestStrategy(unitPrice, area, sumHousingLoan, firstPay, year, housingRate) : new EqualCorpusStrategy(unitPrice, area, sumHousingLoan, firstPay, year, housingRate));
                result = combineBussContext.operate().add(combineHousingContext.operate());
                break;
        }
        return result;
    }

    private void setResultViewData(PayResult result) {
        resSumPriceEdit.setText(result.sumPrice == 0 ? "0" : new DecimalFormat("#.00").format(result.sumPrice));
        resSumLoanEdit.setText(result.sumLoan==0?"0":new DecimalFormat("#.00").format(result.sumLoan));
        resSumPaymentEdit.setText(result.sumPayPrice==0?"0":new DecimalFormat("#.00").format(result.sumPayPrice));
        resSumInterestEdit.setText(result.sumInterest==0?"0":new DecimalFormat("#.00").format(result.sumInterest));
        resfirstPaymentEdit.setText(result.firstPay==0?"0":new DecimalFormat("#.00").format(result.firstPay));
        resSumMonthEdit.setText(result.monthCount==0?"0":new DecimalFormat("#.00").format(result.monthCount));
        if (payTypeRG.getCheckedRadioButtonId() == R.id.repayment_type_interest_rb) {
            resEveryMonthPaymentEdit.setText(result.everyMonthPay==0?"0":new DecimalFormat("#.00").format(result.everyMonthPay));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) resEveryMonthPaymentEdit.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            resEveryMonthPaymentEdit.setLayoutParams(params);
            CustomScrollView.IS_INTERCEPT = false;
        } else {
            CustomScrollView.IS_INTERCEPT = true;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.everyMonthPayArr.length; i++) {
                sb.append("第" + (i+1) + "月，" + new DecimalFormat("#.00").format(result.everyMonthPayArr[i]) + "\n");
            }
            resEveryMonthPaymentEdit.setText(sb.toString());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) resEveryMonthPaymentEdit.getLayoutParams();
            params.height = 600;
            resEveryMonthPaymentEdit.setLayoutParams(params);
        }
    }

    private double getLoanFromEdit(EditText edit) {
        double loan = Double.parseDouble("".equals(edit.getText().toString().trim()) ? "0" : edit.getText().toString().trim());
        if (loan < 1) {
            Toast.makeText(MainActivity.this, "贷款总额不能小于1万", Toast.LENGTH_SHORT).show();
        }
        return loan*10000;
    }

    @Override
    protected void onDestroy() {
        SettingManager settingManager = SettingManager.getInstance();

        settingManager.setLoanType(mortgageTypeRG.getCheckedRadioButtonId());
        settingManager.setCalculateType(calculateTypeRG.getCheckedRadioButtonId());
        settingManager.setPayType(payTypeRG.getCheckedRadioButtonId());

        settingManager.setUnitPrice(unitPriceEdit.getText().toString());
        settingManager.setArea(areaEdit.getText().toString());
        settingManager.setSumLoan(sumLoanEdit.getText().toString());
        settingManager.setSumBussLoan(sumBussLoanEdit.getText().toString());
        settingManager.setSumHousingLoan(sumHousingLoanEdit.getText().toString());

        settingManager.setFirstPay(firstPaySp.getSelectedItemPosition());
        settingManager.setYear(yearSp.getSelectedItemPosition() + 1);
        settingManager.setBussRate(bussRateArr[bussRateSp.getSelectedItemPosition()]);
        settingManager.setHousingRate(housingRateArr[housingRateSp.getSelectedItemPosition()]);
        super.onDestroy();
    }
}
