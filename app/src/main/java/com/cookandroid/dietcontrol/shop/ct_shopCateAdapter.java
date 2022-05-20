package com.cookandroid.dietcontrol.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.util.LoadImage;

import java.util.ArrayList;

public class ct_shopCateAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<ct_cateItem> ctCate_itemlist;

    public ct_shopCateAdapter(Context context) {
        this.myContext = context;
        this.ctCate_itemlist = new ArrayList<ct_cateItem>();
    }


    @Override
    public int getCount() {
        return this.ctCate_itemlist.size();
    }

    @Override
    public Object getItem(int i) {
        return this.ctCate_itemlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cate_itemlist, viewGroup, false);
        }

        ImageView image = view.findViewById(R.id.cate_productImage);
        TextView name = view.findViewById(R.id.cate_productName);
        TextView price = view.findViewById(R.id.cate_productPrice);
        RatingBar rating = view.findViewById(R.id.cate_ratingBar);



        LoadImage loadImage = new LoadImage(ctCate_itemlist.get(i).getImg(), image);
        loadImage.execute();
        name.setText(ctCate_itemlist.get(i).getName());
        price.setText(String.valueOf(ctCate_itemlist.get(i).getPrice()));
        rating.setRating((float) ctCate_itemlist.get(i).getRating());



        return view;
    }
    public void addItem(String url, String name, int price, double rating) {
        this.ctCate_itemlist.add(new ct_cateItem(url, name, price, rating));
    }
}
