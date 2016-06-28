package com.bruce.open.mortgage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.open.mortgage.Model.EveryPayInfo;
import com.bruce.open.mortgage.Model.PayResult;
import com.bruce.open.mortgage.MyApplication;
import com.bruce.open.mortgage.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qizhenghao on 16/6/20.
 */
public class MyMortgageListAdapter extends BaseAdapter {

    private Context context;
    private EveryPayInfo[] infos;
    private DecimalFormat decimalFormat;

    public MyMortgageListAdapter(Context context, EveryPayInfo[] infos) {
        this.context = context;
        this.infos = infos;
        decimalFormat = new DecimalFormat(".##");
    }
    @Override
    public int getCount() {
        return infos==null?0:infos.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return position==0?null:infos[position-1];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_my_mortgage_layout, null);
            holder.dateTv = (TextView) convertView.findViewById(R.id.date_tv);
            holder.interestTv = (TextView) convertView.findViewById(R.id.interest_tv);
            holder.corpusTv = (TextView) convertView.findViewById(R.id.corpus_tv);
            holder.everyPayTv = (TextView) convertView.findViewById(R.id.every_pay_tv);
            holder.leftCorpusTv = (TextView) convertView.findViewById(R.id.left_corpus_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.dateTv.setText("期次");
            holder.interestTv.setText("本期利息");
            holder.corpusTv.setText("本期本金");
            holder.everyPayTv.setText("还款额");
            holder.leftCorpusTv.setText("剩余本金");

            holder.dateTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
            holder.interestTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
            holder.corpusTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
            holder.everyPayTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
            holder.leftCorpusTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
        } else {
            position -= 1;
            holder.dateTv.setText(infos[position].date);
            holder.interestTv.setText(decimalFormat.format(infos[position].interest));
            holder.corpusTv.setText(decimalFormat.format(infos[position].corpus));
            holder.everyPayTv.setText(decimalFormat.format(infos[position].everyPay));
            holder.leftCorpusTv.setText(decimalFormat.format(infos[position].leftCorpus));

            holder.dateTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.common_font_black_selector));
            holder.interestTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.common_font_black_selector));
            holder.corpusTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.common_font_black_selector));
            holder.everyPayTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.blue_light));
            holder.leftCorpusTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.common_font_black_selector));

        }

        return convertView;
    }

    public void setData(EveryPayInfo[] infos) {
        this.infos = infos;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView dateTv;
        TextView interestTv;
        TextView corpusTv;
        TextView everyPayTv;
        TextView leftCorpusTv;
    }
}
