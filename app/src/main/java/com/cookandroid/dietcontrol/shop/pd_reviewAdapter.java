package com.cookandroid.dietcontrol.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class pd_reviewAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<pd_reviewItem> pd_reviewList;

    public pd_reviewAdapter(Context context) {
        this.myContext = context;
        this.pd_reviewList = new ArrayList<pd_reviewItem>();
    }

    @Override
    public int getCount() {
        return this.pd_reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pd_reviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pd_review, viewGroup, false);
        }

        RatingBar ratingBar = view.findViewById(R.id.pd_review_ratingbar);
        TextView name_date = view.findViewById(R.id.pd_name_date);
        TextView review = view.findViewById(R.id.pd_review);


        ratingBar.setRating(pd_reviewList.get(i).getPd_rating_num());
        name_date.setText(pd_reviewList.get(i).getPd_review_name_date());
        review.setText(pd_reviewList.get(i).getPd_review());




        return view;
    }

    public void pd_addItem(String name, String date, String image, String review, int ratingnum) {
        this.pd_reviewList.add(new pd_reviewItem(name, date, image, review, ratingnum));
    }

    public void pd_listViewHeightSetting(Adapter listAdapter, ListView listView) {
        int totalHeight = 0;
        for(int i=0; i<listAdapter.getCount();i++) {
            View listItem = listAdapter.getView(i, null, listView);
            float px = 500*(listView.getResources().getDisplayMetrics().density);
            listItem.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1));
        listView.setLayoutParams(params);
    }
}
