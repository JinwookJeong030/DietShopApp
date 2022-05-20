package com.cookandroid.dietcontrol.mypage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class ListMyConsignmentItem {
    private String name;
    private String phoneNum;
    private String address;
    private boolean baseAddress;

//  get, set  메소드
    public String getName() {
        return name;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public String getAddress() {
        return address;
    }
    public boolean isBaseAddress() {
        return baseAddress;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBaseAddress(boolean baseAddress) {
        this.baseAddress = baseAddress;
    }
//   생성자
    public ListMyConsignmentItem(String name, String phoneNum, String address, boolean baseAddress) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.baseAddress = baseAddress;
    }

     public static class ListMyConsignmentAdapter extends BaseAdapter {
        ArrayList<ListMyConsignmentItem> items = new ArrayList<ListMyConsignmentItem>();
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
             ListMyConsignmentItem listMyConsignmentItem = items.get(position);

             if(convertView == null) {
                 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = inflater.inflate(R.layout.my_consignment_listview,parent, false);
             }

             TextView listName = (TextView) convertView.findViewById(R.id.consignment_listView_name);
             TextView listPhoneNum = (TextView) convertView.findViewById(R.id.consignment_listView_phoneNum);
             TextView listAddress = (TextView) convertView.findViewById(R.id.consignment_listView_address);

             Button listModify = (Button) convertView.findViewById(R.id.consignment_listView_modify);
             Button listDelete = (Button) convertView.findViewById(R.id.consignment_listView_delete);
             Button listBaseAddressChoice = (Button) convertView.findViewById(R.id.consignment_listView_baseAddress_choice);

//           해당 배송지: 이름, 핸드폰 번호, 주소 텍스트 변경부분
             listName.setText(listMyConsignmentItem.getName());
             listPhoneNum.setText(listMyConsignmentItem.getPhoneNum());
             listAddress.setText(listMyConsignmentItem.getAddress());

             View.OnClickListener listener = new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     switch (view.getId()) {
//           수정 버튼
                         case R.id.consignment_listView_modify:
                             Intent intent = new Intent(view.getContext(), ConsignmentModify.class);
                             break;
//           삭제 버튼
                         case R.id.consignment_listView_delete:
                             AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                             dlg.setMessage("삭제하시겠습니까?");
                             dlg.setNegativeButton("취소", null);
                             dlg.setPositiveButton("예", null);

                             AlertDialog alert = dlg.create();
                             alert.show();
                             break;
//           기본 배송지로 설정 부분
                         case R.id.consignment_listView_baseAddress_choice:
                             break;
                     }
                 }
             };
            listModify.setOnClickListener(listener);
            listDelete.setOnClickListener(listener);

             return convertView;
         }
         public void addMyConsignmentItem(ListMyConsignmentItem item) {
             items.add(item);
         }

     }
}
