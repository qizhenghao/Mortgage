package com.bruce.open.mortgage.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bruce.open.mortgage.MainActivity;
import com.bruce.open.mortgage.MyApplication;
import com.bruce.open.mortgage.R;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 * Created by qizhenghao on 16/6/23.
 */
public class SettingManager {

    private static final String FILE_NAME = "share_data"; //保存在手机里面的文件名

    private SharedPreferences sp = null;

    private static SettingManager INSTANCE = null;

    public static SettingManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SettingManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SettingManager();
                }
            }
        }
        return INSTANCE;
    }

    private SettingManager() {
        sp = MyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /** 存储贷款类型id*/
    public void setLoanType(int id) {
        sp.edit().putInt("loan_type_id", id).apply();
    }
    public int getLoanType() {
        return sp.getInt("loan_type_id", R.id.mortgage_type_business_rb);
    }

    /** 存储计算方式id*/
    public void setCalculateType(int id) {
        sp.edit().putInt("calculate_type_id", id).apply();
    }
    public int getCalculateType() {
        return sp.getInt("calculate_type_id", R.id.calculate_type_unit_price_rb);
    }

    /** 存储还款方式id*/
    public void setPayType(int id) {
        sp.edit().putInt("pay_type_id", id).apply();
    }
    public int getPayType() {
        return sp.getInt("pay_type_id", R.id.repayment_type_interest_rb);
    }

    /** 存储单价*/
    public void setUnitPrice(String price) {
        sp.edit().putString("unit_price", price).apply();
    }
    public String getUnitPrice() {
        return sp.getString("unit_price", "");
    }

    /** 存储面积*/
    public void setArea(String area) {
        sp.edit().putString("area", area).apply();
    }
    public String getArea() {
        return sp.getString("area", "");
    }

    /** 存储贷款总价*/
    public void setSumLoan(String loan) {
        sp.edit().putString("sum_loan", loan).apply();
    }
    public String getSumLoan() {
        return sp.getString("sum_loan", "");
    }

    /** 存储商业贷款总价*/
    public void setSumBussLoan(String loan) {
        sp.edit().putString("sum_buss_loan", loan).apply();
    }
    public String getSumBussLoan() {
        return sp.getString("sum_buss_loan", "");
    }

    /** 存储公积金贷款总价*/
    public void setSumHousingLoan(String loan) {
        sp.edit().putString("sum_housing_loan", loan).apply();
    }
    public String getSumHousingLoan() {
        return sp.getString("sum_housing_loan", "");
    }

    /** 存储首付比例*/
    public void setFirstPay(float firstPay) {
        sp.edit().putFloat("first_pay", firstPay).apply();
    }
    public float getFirstPay() {
        return sp.getFloat("first_pay", MainActivity.DEFAULT_FIRST_PAY);
    }

    /** 存储利率*/
    public void setRate(float rate) {
        sp.edit().putFloat("rate", rate).apply();
    }
    public float getRate() {
        return sp.getFloat("rate", 0);
    }

    /** 存储商业贷款利率*/
    public void setBussRate(float rate) {
        sp.edit().putFloat("buss_rate", rate).apply();
    }
    public float getBussRate() {
        return sp.getFloat("buss_rate", MainActivity.NORMAL_BUSINESS_RATE);
    }

    /** 存储公积金贷款利率*/
    public void setHousingRate(float rate) {
        sp.edit().putFloat("housing_rate", rate).apply();
    }
    public float getHousingRate() {
        return sp.getFloat("housing_rate", MainActivity.NORMAL_HOUSING_RATE);
    }

    /** 存储按揭年数*/
    public void setYear(float year) {
        sp.edit().putFloat("year", year).apply();
    }
    public float getYear() {
        return sp.getFloat("year", MainActivity.DEFAULT_YEAR);
    }

    /** 存储计算结果*/
    public void setResultJson(String resultJson) {
        sp.edit().putString("result_json", resultJson).apply();
    }
    public String getResultJson() {
        return sp.getString("result_json", "");
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context , String key, Object object){

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }
}