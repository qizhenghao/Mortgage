package com.bruce.open.mortgage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bruce.open.mortgage.adapter.BaseSpinnerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final float NORMAL_BUSINESS_RATE = 4.90f;
    public static final float NORMAL_HOUSING_RATE = 3.25f;

    private RadioGroup mortgageTypeRG;
    private RadioGroup calculateTypeRG;
    private EditText unitPriceEdit;
    private EditText sumAreaEdit;
    private Spinner firstPaymentSp;
    private EditText sumMortgageEdit;

    private Spinner mortgageYearSp;
    private Spinner mortgageRateSp;

    private RadioGroup repaymentTypeRG;

    private Button calculateBtn;
    private Button refillBtn;

    private EditText resSumPriceEdit;
    private EditText resSumMortgageEdit;
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
                if (checkedId == R.id.mortgage_type_business_rb) {

                }
            }
        });
        calculateTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.calculate_type_sum_mortgage_rb) {
                    findViewById(R.id.calculate_type_unit_layout).setVisibility(View.INVISIBLE);
                    findViewById(R.id.calculate_type_sum_layout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.calculate_type_unit_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.calculate_type_sum_layout).setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initData() {
        firstPayAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.FIRST_PAYMENT, firstPayArr);
        yearAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.YEAR, yearArr);
        bussRateAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.BUSSINESS_RATE, bussRateArr);
        housingRateAdapter = new BaseSpinnerAdapter(MainActivity.this, BaseSpinnerAdapter.HOUSING_RATE, housingRateArr);
        firstPaymentSp.setAdapter(firstPayAdapter);
        mortgageYearSp.setAdapter(yearAdapter);
        mortgageRateSp.setAdapter(bussRateAdapter);
        firstPaymentSp.setSelection(3);
        mortgageYearSp.setSelection(30-1);
        for (int i=0;i<bussRateArr.length;i++) {
            if (bussRateArr[i]==NORMAL_BUSINESS_RATE) {
                mortgageRateSp.setSelection(i);
            }
        }
    }

    private void initViews() {
        mortgageTypeRG = (RadioGroup) findViewById(R.id.mortgage_type_rg);
        calculateTypeRG = (RadioGroup) findViewById(R.id.calculate_type_rg);
        unitPriceEdit = (EditText) findViewById(R.id.calculate_unit_price_et);
        sumAreaEdit = (EditText) findViewById(R.id.calculate_area_et);
        firstPaymentSp = (Spinner) findViewById(R.id.calculate_unit_price_payment_ratio_spinner);
        sumMortgageEdit = (EditText) findViewById(R.id.calculate_sum_price_et);
        mortgageYearSp = (Spinner) findViewById(R.id.mortgage_year_spinner);
        mortgageRateSp = (Spinner) findViewById(R.id.mortgage_rate_spinner);
        repaymentTypeRG = (RadioGroup) findViewById(R.id.repayment_type_rg);
        calculateBtn = (Button) findViewById(R.id.calculate_btn);
        refillBtn = (Button) findViewById(R.id.fill_again_btn);
        resSumPriceEdit = (EditText) findViewById(R.id.result_sum_price_edit);
        resSumMortgageEdit = (EditText) findViewById(R.id.result_sum_loan_edit);
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
}
