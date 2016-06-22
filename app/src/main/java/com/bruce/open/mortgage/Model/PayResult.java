package com.bruce.open.mortgage.Model;

import java.util.List;

/**
 * Created by qizhenghao on 16/6/21.
 */
public class PayResult {

    public double sumPrice;

    public double sumLoan;

    public double sumPayPrice;

    public double sumInterest;

    public double firstPay;

    public int monthCount;

    public double everyMonthPay;

    public double monthRate;

    public double[] everyMonthPayArr;

    public PayResult add(PayResult origin) {
        this.sumLoan += origin.sumLoan;
        this.sumPayPrice += origin.sumPayPrice;
        this.sumInterest += origin.sumInterest;
        this.everyMonthPay += origin.everyMonthPay;
        if (everyMonthPayArr != null)
            for (int i=0;i<everyMonthPayArr.length;i++) {
                this.everyMonthPayArr[i] += origin.everyMonthPayArr[i];
            }
        return this;
    }

}
