package com.bruce.open.mortgage.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

    public long beginTime;
    public int loanTypeId;
    public int payTypeId;

    public JSONObject toJSONObj() {
        JSONObject object = new JSONObject();
        try {
            object.put("sumPrice", sumPrice);
            object.put("sumLoan", sumLoan);
            object.put("sumPayPrice", sumPayPrice);
            object.put("sumInterest", sumInterest);
            object.put("firstPay", firstPay);
            object.put("monthCount", monthCount);
            object.put("everyMonthPay", everyMonthPay);
            object.put("monthRate", monthRate);
            object.put("beginTime", beginTime);
            if (everyMonthPayArr != null) {
                JSONArray array = new JSONArray();
                for (double anEveryMonthPayArr : everyMonthPayArr) {
                    array.put(anEveryMonthPayArr);
                }
                object.put("everyMonthPayArr", array);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public static PayResult parse(JSONObject object) {
        PayResult result = new PayResult();
        try {
            result.sumPrice = object.getDouble("sumPrice");
            result.sumLoan = object.getDouble("sumLoan");
            result.sumPayPrice = object.getDouble("sumPayPrice");
            result.sumInterest = object.getDouble("sumInterest");
            result.firstPay = object.getDouble("firstPay");
            result.monthCount = object.getInt("monthCount");
            result.everyMonthPay = object.getDouble("everyMonthPay");
            result.monthRate = object.getDouble("monthRate");
            result.beginTime = object.getLong("beginTime");
            JSONArray array = object.getJSONArray("everyMonthPayArr");
            if (array != null) {
                result.everyMonthPayArr = new double[array.length()];
                for (int i=0;i<array.length();i++) {
                    result.everyMonthPayArr[i] = array.getDouble(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

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
