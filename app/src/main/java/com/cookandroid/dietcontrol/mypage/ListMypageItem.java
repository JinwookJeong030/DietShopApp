package com.cookandroid.dietcontrol.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class ListMypageItem {
    private String mypageTitle;
    private String mypagePointCnt;

    public String getMypageTitle() {
        return mypageTitle;
    }
    public void setMypageTitle(String mypageTitle) {
        this.mypageTitle = mypageTitle;
    }
    public String getMypagePointCnt() {
        return mypagePointCnt;
    }
    public void setMypagePointCnt(String mypagePointCnt) {
        this.mypagePointCnt = mypagePointCnt;
    }

    public ListMypageItem(String mypageTitle, String mypagePointCtn) {
        this.mypageTitle = mypageTitle;
        this.mypagePointCnt = mypagePointCtn;
    }
    public ListMypageItem(String mypageTitle) {
        this.mypageTitle = mypageTitle;
    }

    public static class ListMypageItemAdapter extends BaseAdapter {
        ArrayList<ListMypageItem> items = new ArrayList<ListMypageItem>();
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
            ListMypageItem listMypageItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mypage_listview, parent, false);
            }
            TextView listTitleText = (TextView) convertView.findViewById(R.id.list_title);
            TextView listPointCtn = (TextView) convertView.findViewById(R.id.point_cnt);

            listTitleText.setText(listMypageItem.getMypageTitle());
            listPointCtn.setText(listMypageItem.getMypagePointCnt());


            return convertView;
        }
        public void addMapageItem(ListMypageItem item) {
            items.add(item);
        }
    }
}
