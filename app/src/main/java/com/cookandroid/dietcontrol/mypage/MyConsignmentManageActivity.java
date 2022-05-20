package com.cookandroid.dietcontrol.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.cookandroid.dietcontrol.R;

public class MyConsignmentManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consignment_manage);

        String name, phoneNum, address;
        boolean baseAddressChoice;
        name = "표영운";
        phoneNum = "010-9335-9069";
        address = "서울특별시 구로구 천왕로 92 (천왕동, 천왕이펜하우스2단지) 206동 702호";
        baseAddressChoice = true;

        //1
        ListView listView;
        ListMyConsignmentItem.ListMyConsignmentAdapter adapter;
        //2
        listView = findViewById(R.id.my_manage_consignment);
        //3
        adapter = new ListMyConsignmentItem.ListMyConsignmentAdapter();

        adapter.addMyConsignmentItem(new ListMyConsignmentItem(name, phoneNum, address, baseAddressChoice));
        //4
        listView.setAdapter(adapter);
    }
}