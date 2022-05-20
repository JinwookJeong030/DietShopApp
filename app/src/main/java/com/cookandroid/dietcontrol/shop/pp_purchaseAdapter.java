package com.cookandroid.dietcontrol.shop;

import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.util.LoadImage;

import java.util.ArrayList;

public class pp_purchaseAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<pp_purchaseItem> pp_purchaseList;

    public pp_purchaseAdapter(Context context) {
        this.myContext = context;
        this.pp_purchaseList = new ArrayList<pp_purchaseItem>();
    }

    @Override
    public int getCount() {
        return this.pp_purchaseList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pp_purchaseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pp_purchaseitem, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.pp_item_name);
        TextView price = view.findViewById(R.id.pp_item_price);
        TextView count = view.findViewById(R.id.pp_item_count);
        ImageView img = view.findViewById(R.id.pp_item_image);

        name.setText(pp_purchaseList.get(i).getPp_purchase_name());
        price.setText(pp_purchaseList.get(i).getPp_purchase_priceString());
        count.setText(pp_purchaseList.get(i).getPp_purchase_countString());

        LoadImage loadImage = new LoadImage(SERVER_URL +"img/product/"+ pp_purchaseList.get(i).getPp_purchase_image(), img);
        loadImage.execute();
        return view;
    }

    public void pp_addItem(String name, int price, String image, int count) {
        this.pp_purchaseList.add(new pp_purchaseItem(name, price, image, count));
    }

    public void pp_listViewHeightSetting(Adapter listAdapter, ListView listView) {
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
