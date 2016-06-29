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
}
