package com.bruce.open.mortgage.IPayStrategy;

import com.bruce.open.mortgage.Model.PayResult;

/**
 * Created by qizhenghao on 16/6/21.
 */
public class EqualCorpusStrategy implements IPayStrategy {

    private double unitPrice;
    private double area;
    private double sumLoan;
    private double firstPay;
    private int year;
    private double rate;


    public EqualCorpusStrategy(double unitPrice, double area, double sumLoan, double firstPay, int year, double rate) {
        this.unitPrice = unitPrice;
        this.area = area;
        this.sumLoan = sumLoan;
        this.firstPay = firstPay;
        this.year = year;
        this.rate = rate;
    }


    @Override
    public PayResult calculate() {
        PayResult result = new PayResult();
        result.sumPrice = unitPrice * area;
        if (sumLoan == 0) {
            result.firstPay = result.sumPrice * firstPay;
            result.sumLoan = result.sumPrice - result.firstPay;
        } else {
            result.sumLoan = sumLoan;
        }
        result.monthRate = rate/12;
        result.monthCount = year*12;
        result.everyMonthPayArr = new double[result.monthCount];
        double everyCorpus = result.sumLoan / result.monthCount;
        double leftCorpus = result.sumLoan;
        double everyMonthPay;
        for (int i=0;i<result.monthCount;i++) {
            everyMonthPay = everyCorpus + leftCorpus * result.monthRate;
            leftCorpus -= everyCorpus;
            result.everyMonthPayArr[i] = everyMonthPay;
            result.sumPayPrice += everyMonthPay;
        }
        result.sumInterest = result.sumPayPrice - result.sumLoan;
        return result;
    }
}
