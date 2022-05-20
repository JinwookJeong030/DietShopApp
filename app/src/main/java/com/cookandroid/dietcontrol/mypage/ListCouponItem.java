package com.cookandroid.dietcontrol.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class ListCouponItem {
    private String couponItemName, couponItemDiscount, couponItemDeadline;
    private boolean couponItemUseable;

    public String getCouponItemName() {
        return couponItemName;
    }
    public String getCouponItemDiscount() {
        return couponItemDiscount;
    }
    public String getCouponItemDeadline() {
        return couponItemDeadline;
    }
    public boolean isCouponItemUseable() {
        return couponItemUseable;
    }

    public void setCouponItemName(String couponItemName) {
        this.couponItemName = couponItemName;
    }
    public void setCouponItemDiscount(String couponItemDiscount) {
        this.couponItemDiscount = couponItemDiscount;
    }
    public void setCouponItemDeadline(String couponItemDeadline) {
        this.couponItemDeadline = couponItemDeadline;
    }
    public void setCouponItemUseable(boolean couponItemUseable) {
        this.couponItemUseable = couponItemUseable;
    }

    public ListCouponItem(String couponItemName, String couponItemDiscount, String couponItemDeadline,
                          boolean couponItemUseable) {
        this.couponItemName = couponItemName;
        this.couponItemDiscount = couponItemDiscount;
        this.couponItemDeadline = couponItemDeadline;
        this.couponItemUseable = couponItemUseable;
    }

    public ListCouponItem(String couponItemName) {
        this.couponItemName = couponItemName;
    }

    public static class  ListCouponItemAdapter extends BaseAdapter {
        ArrayList<ListCouponItem> items = new ArrayList<ListCouponItem>();
        Context context;
        TextView listCouponItemName, listCouponItemDiscount, listCouponItemDeadLine, listCouponItemUseableText;
        ImageView listCouponItemUseableIcon;

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
            ListCouponItem listCouponItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.coupon_item_list, parent, false);
            }

            listCouponItemName = convertView.findViewById(R.id.coupon_item_name);
            listCouponItemDiscount = convertView.findViewById(R.id.coupon_item_discount);
            listCouponItemDeadLine = convertView.findViewById(R.id.coupon_item_deadline);
            listCouponItemUseableText = convertView.findViewById(R.id.coupon_item_isuseable);
            listCouponItemUseableIcon = convertView.findViewById(R.id.coupon_item_isuseable_icon);

            listCouponItemName.setText(listCouponItem.getCouponItemName());
            listCouponItemDiscount.setText(listCouponItem.getCouponItemDiscount());
            listCouponItemDeadLine.setText(listCouponItem.getCouponItemDeadline());

            if(listCouponItem.isCouponItemUseable()) {
                listCouponItemUseableIcon.setImageResource(R.drawable.ic_use_ok);
                listCouponItemUseableText.setText("사용 가능");
            }
            else {
                listCouponItemUseableIcon.setImageResource(R.drawable.ic_close);
                listCouponItemUseableText.setText("사용 만료");
            }

            return convertView;
        }
        public void addCouponItemList(ListCouponItem item) {
            items.add(item);
        }
    }
}
