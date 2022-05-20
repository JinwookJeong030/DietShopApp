package com.cookandroid.dietcontrol.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class ListPointGarlicItem {
    private  String pointItemName, pointItemDatetime, pointItemCount;

    public String getPointItemName() {
        return pointItemName;
    }
    public String getPointItemDatetime() {
        return pointItemDatetime;
    }
    public String getPointItemCount() {
        return pointItemCount;
    }

    public void setPointItemName(String pointItemName) {
        this.pointItemName = pointItemName;
    }
    public void setPointItemDatetime(String pointItemDatetime) {
        this.pointItemDatetime = pointItemDatetime;
    }
    public void setPointItemCount(String pointItemCount) {
        this.pointItemCount = pointItemCount;
    }

    public ListPointGarlicItem(String pointItemName, String pointItemDatetime, String pointItemCount) {
        this.pointItemName = pointItemName;
        this.pointItemDatetime = pointItemDatetime;
        this.pointItemCount = pointItemCount;
    }
    public static class ListPointGarlicItemAdapter extends BaseAdapter {
        ArrayList<ListPointGarlicItem> items = new ArrayList<ListPointGarlicItem>();
        Context context;

        @Override
        public int getCount() {
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            context = parent.getContext();
            ListPointGarlicItem listPointGarlicItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.point_item_list, parent, false);
            }
            TextView listPointItemName =  convertView.findViewById(R.id.point_item_name);
            TextView listPointItemDatetime = convertView.findViewById(R.id.point_item_datetime);
            TextView listPointItemCount =  convertView.findViewById(R.id.point_item_count);

            listPointItemName.setText(listPointGarlicItem.getPointItemName());
            listPointItemDatetime.setText(listPointGarlicItem.getPointItemDatetime());
            listPointItemCount.setText(listPointGarlicItem.getPointItemCount());


            return convertView;
        }
        public void addPointGarlicItemList(ListPointGarlicItem item) {
            items.add(item);
        }

    }
}
