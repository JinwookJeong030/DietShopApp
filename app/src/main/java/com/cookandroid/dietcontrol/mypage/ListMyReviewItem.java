package com.cookandroid.dietcontrol.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class ListMyReviewItem {
    private  String re_date,re_product_name,re_contents;
    private int re_rating;

    public String getRe_date() {
        return re_date;
    }
    public String getRe_product_name() {
        return re_product_name;
    }
    public String getRe_contents() {
        return re_contents;
    }
    public int getRe_rating() {
        return re_rating;
    }


    public void setRe_date(String re_date) {
        this.re_date = re_date;
    }
    public void setRe_product_name(String re_product_name) {
        this.re_product_name = re_product_name;
    }
    public void setRe_contents(String re_contents) {
        this.re_contents = re_contents;
    }
    public void setRe_rating(int re_rating) {
        this.re_rating = re_rating;
    }

    public ListMyReviewItem(String re_date, String re_product_name, String re_contents,
                            int re_rating) {
        this.re_date = re_date;
        this.re_product_name = re_product_name;
        this.re_contents = re_contents;
        this.re_rating = re_rating;
    }


    public static class ListMyReviewItemAdapter extends BaseAdapter {
        ArrayList<ListMyReviewItem> items = new ArrayList<ListMyReviewItem>();
        Context context;

        TextView listReviewNameDate, listReviewContents;
        RatingBar listReviewRating;
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
            ListMyReviewItem listMyReviewItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.review_item_list, parent, false);
            }
            listReviewNameDate = convertView.findViewById(R.id.re_name_date);
            listReviewContents = convertView.findViewById(R.id.re_contents);
            listReviewRating = convertView.findViewById(R.id.re_review_ratingbar);

            listReviewNameDate.setText(listMyReviewItem.getRe_date()
                                        + " | " + listMyReviewItem.getRe_product_name());
            listReviewContents.setText(listMyReviewItem.getRe_contents());
            listReviewRating.setRating(listMyReviewItem.getRe_rating());


            return convertView;
        }
        public void addMyReviewItemList(ListMyReviewItem item) {
            items.add(item);
        }
    }
}
