package com.cookandroid.dietcontrol.mypage;

import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.util.LoadImage;

import java.util.ArrayList;

public class mp_purchaseListAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<mp_purchaseListItem> mp_purchaseList;

    public mp_purchaseListAdapter(Context myContext) {
        this.myContext = myContext;
        this.mp_purchaseList = new ArrayList<mp_purchaseListItem>();
    }

    @Override
    public int getCount() {
        return this.mp_purchaseList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mp_purchaseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mp_purchaselistitem, viewGroup, false);
        }

        TextView o_seq = view.findViewById(R.id.mp_o_seq);
        ImageView img = view.findViewById(R.id.mp_item_image);
        TextView name = view.findViewById(R.id.mp_item_name);
        TextView price = view.findViewById(R.id.mp_item_price);
        TextView date = view.findViewById(R.id.mp_o_date);

        LoadImage loadImage = new LoadImage(SERVER_URL+"img/product/"+mp_purchaseList.get(i).getMp_item_image(),img);
        loadImage.execute();

        o_seq.setText("[" + mp_purchaseList.get(i).getMp_o_seq() + "]");
        name.setText(mp_purchaseList.get(i).getMp_item_name());
        price.setText(mp_purchaseList.get(i).getMp_item_price() + "원");
        date.setText(mp_purchaseList.get(i).getMp_item_date());

        return view;
    }

    public void mp_addItem(String o_seq, String img, String name, int price, String date) {
        this.mp_purchaseList.add(new mp_purchaseListItem(o_seq, img, name, price, date));
    }
}
