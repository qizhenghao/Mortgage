package com.bruce.open.mortgage.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qizhenghao on 16/6/28.
 */
public class EveryPayInfo {

    public String date;
    public double interest;
    public double corpus;
    public double everyPay;
    public double leftCorpus;

    public static EveryPayInfo[] parse(PayResult payResult) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(payResult.beginTime);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        double sumCorpus = payResult.sumLoan;
        EveryPayInfo[] infos = new EveryPayInfo[payResult.everyMonthPayArr==null?0:payResult.everyMonthPayArr.length];
        for (int i = 0; i < infos.length; i++) {
            EveryPayInfo info = new EveryPayInfo();
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            info.date = dateFormat.format(calendar.getTime());
            info.interest = sumCorpus*payResult.monthRate;
            info.corpus = payResult.everyMonthPayArr[i] - info.interest;
            info.everyPay = payResult.everyMonthPayArr[i];
            sumCorpus -= info.corpus;
            info.leftCorpus = sumCorpus;
            infos[i] = info;
        }
        return infos;
    }

    public static EveryPayInfo[] parse(PayResult result, PayResult result1, PayResult result2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(result.beginTime);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        double sumCorpus1 = result1.sumLoan;
        double sumCorpus2 = result2.sumLoan;
        EveryPayInfo[] infos = new EveryPayInfo[result.everyMonthPayArr==null?0:result.everyMonthPayArr.length];
        for (int i = 0; i < infos.length; i++) {
            EveryPayInfo info = new EveryPayInfo();
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            info.date = dateFormat.format(calendar.getTime());
            info.interest = result1.everyMonthPayArr[i];
            info.corpus = result2.everyMonthPayArr[i];
            info.everyPay = result.everyMonthPayArr[i];
            double interest1 = sumCorpus1*result1.monthRate;
            double interest2 = sumCorpus2*result2.monthRate;
            double corpus1 = result1.everyMonthPayArr[i] - interest1;
            double corpus2 = result2.everyMonthPayArr[i] - interest2;
            sumCorpus1 -= corpus1;
            sumCorpus2 -= corpus2;
            info.leftCorpus = sumCorpus1 + sumCorpus2;
            infos[i] = info;
        }
        return infos;
    }
}
