package com.cookandroid.dietcontrol.dietmanagement;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import com.cookandroid.dietcontrol.model.DietLogVO;
import com.cookandroid.dietcontrol.util.LoadImage;
import com.cookandroid.dietcontrol.util.LoadingDialog;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListDietLogItem {

    private String dietPeriod;
    private String dietTime;
    private String dietContent;
    private String dietImg;


    public ListDietLogItem() {
    }

    public ListDietLogItem(String dietPeriod, String dietTime, String dietContent , String dietImg) {
        this.dietPeriod = dietPeriod;
        this.dietTime = dietTime;
        this.dietContent = dietContent;
        this.dietImg = dietImg;
    }

    public String getDietImg() {
        return dietImg;
    }

    public void setImg(String img) {
        this.dietImg = dietImg;
    }

    public String getDietPeriod() {
        return dietPeriod;
    }

    public String getDietDatetime() {
        return dietTime;
    }


    public String getDietContent() {
        return dietContent;
    }

    public void setDietPeriod(String dietPeriod) {
        this.dietPeriod = dietPeriod;
    }

    public void setDietDatetime(String dietTime) {
        this.dietTime = dietTime;
    }


    public void setDietContent(String dietContent) {
        this.dietContent = dietContent;
    }

    public static class ListItemAdapter extends BaseAdapter {
        ArrayList<ListDietLogItem> items = new ArrayList<ListDietLogItem>();
        Context context;

        /**
         *  식단 탭 부분을 위한 리스트뷰어댑터
         */

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
            ListDietLogItem listDietLogItem = items.get(position);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_diet_dietlog, parent, false);
            }
            ImageView listImageView = (ImageView) convertView.findViewById(R.id.img_view_diet_log) ;
            TextView listDietPeriod = (TextView) convertView.findViewById(R.id.period_diet_log) ;
            TextView  listDietDateTime = (TextView) convertView.findViewById(R.id.date_time_log) ;
            TextView listDietContents = (TextView) convertView.findViewById(R.id.contents_diet_log) ;

            LoadImage image =new LoadImage(listDietLogItem.getDietImg(), listImageView);

            image.execute();


            listDietPeriod.setText(listDietLogItem.getDietPeriod());
            listDietDateTime.setText(listDietLogItem.getDietDatetime());
            listDietContents.setText(listDietLogItem.getDietContent());

            return convertView;
        }

        public void addItem(String period, String date, String contents){
            DietLogVO item = new DietLogVO();
            }

        public void addItem(ListDietLogItem item) {
            items.add(item);
        }
        public void clearItem(){
            items.clear();
        }







    }





}




