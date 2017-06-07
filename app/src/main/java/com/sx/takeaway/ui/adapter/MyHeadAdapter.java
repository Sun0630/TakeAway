package com.sx.takeaway.ui.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sx.takeaway.MyApplication;
import com.sx.takeaway.R;
import com.sx.takeaway.model.net.bean.GoodsTypeInfo;

import java.util.ArrayList;

/**
 * 商品信息类别列表数据适配
 */

public class MyHeadAdapter extends BaseAdapter {
    private ArrayList<GoodsTypeInfo> headDataSet;
    private int selectedHeadIndex;

    public MyHeadAdapter(ArrayList<GoodsTypeInfo> headDataSet) {
        this.headDataSet = headDataSet;
    }


    @Override
    public int getCount() {
        return headDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return headDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GoodsTypeInfo data = headDataSet.get(position);
        HeadViewHolder holder;
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
            holder = new HeadViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeadViewHolder) convertView.getTag();
        }
        holder.setData(data);

        if (position == selectedHeadIndex)
            holder.itemView.setBackgroundColor(Color.WHITE);


        return convertView;
    }

    public void setSelectPosition(int index) {
        selectedHeadIndex = index;
        notifyDataSetChanged();
    }

    private class HeadViewHolder {

        private View itemView;
        private GoodsTypeInfo data;

        public HeadViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public void setData(GoodsTypeInfo data) {
            this.data = data;
            ((TextView) itemView).setText(data.name);
            ((TextView) itemView).setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.colorItemBg));
            ((TextView) itemView).setTextSize(12);

            int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, itemView.getResources().getDisplayMetrics())+0.5f);

            ((TextView) itemView).setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));
            ((TextView) itemView).setGravity(Gravity.CENTER);
        }
    }
}