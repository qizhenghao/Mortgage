package com.bruce.open.mortgage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bruce.open.mortgage.MainActivity;
import com.bruce.open.mortgage.R;

/**
 * Created by qizhenghao on 16/6/20.
 */
public class MyMortgageListAdapter extends BaseAdapter {

    public final static int FIRST_PAYMENT = 1001;
    public final static int YEAR = 1002;
    public final static int BUSSINESS_RATE = 1003;
    public final static int HOUSING_RATE = 1004;

    private Context context;
    private int type;
    private float[] dataArr;

    public MyMortgageListAdapter(Context context, int type, float[] dataArr) {
        this.context = context;
        this.type = type;
        this.dataArr = dataArr;
    }
    @Override
    public int getCount() {
        return dataArr.length;
    }

    @Override
    public Object getItem(int position) {
        return dataArr[position];
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
            holder.contentTv = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (type) {
            case FIRST_PAYMENT:
                holder.contentTv.setText(position + "成");
                break;
            case YEAR:
                holder.contentTv.setText((position+1) + "年");
                break;
            case BUSSINESS_RATE:
                holder.contentTv.setText(dataArr[position]==MainActivity.NORMAL_BUSINESS_RATE?(dataArr[position] + "%, 2016.1.1基准利率"):dataArr[position] + "%");
                break;
            case HOUSING_RATE:
                holder.contentTv.setText(dataArr[position]==MainActivity.NORMAL_HOUSING_RATE?(dataArr[position] + "%, 2016.1.1基准利率"):dataArr[position] + "%");
                break;
            default:
                break;
        }
        return convertView;
    }

    class ViewHolder {
        TextView contentTv;
    }
}
