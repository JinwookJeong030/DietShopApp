package com.cookandroid.dietcontrol.mypage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.shop.addressAPI;

public class AddressSearchActivity extends AppCompatActivity {
    private TextView actionBarTitle;
    private ImageView actionBarBackBtn;
    private String address1 = null, address2 =  null;
    private Button addressSearchBtn, okBtn;
    private EditText ad_mdf_zipcode, ad_mdf_address, ad_sub_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        //      커스텀 엑션바
        actionBarTitle = findViewById(R.id.actionbar_title);
        actionBarTitle.setText("주소 수정");
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);

        addressSearchBtn = findViewById(R.id.addr_mdf_address_select);
        okBtn = findViewById(R.id.ad_search_ok);
        ad_sub_address = findViewById(R.id.addr_mdf_sub_address);
        ad_mdf_zipcode = findViewById(R.id.addr_mdf_zipcode);
        ad_mdf_address = findViewById(R.id.addr_mdf_address);
        ad_sub_address = findViewById(R.id.addr_mdf_sub_address);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.addr_mdf_address_select:
                        // 우편번호 검색 버튼
                        Intent goAdressAPI = new Intent(getApplicationContext(), addressAPI.class);

                        startActivityForResult(goAdressAPI, 0);
                        break;
                    case R.id.ad_search_ok:
                        Intent gobackModify = new Intent(getApplicationContext(), ClientInfoModify.class);
                        if(address1 != null && address2 != null) {
                            gobackModify.putExtra("mdfAdZipcode", address1);
                            gobackModify.putExtra("mdfAddressee", address2);
                            gobackModify.putExtra("mdfSubAddressee", ad_sub_address.getText().toString());
                            setResult(RESULT_OK, gobackModify);
                        }
                        finish();
                        break;
                    case R.id.actionbar_left_btn:
                        finish();
                        break;
                }
            }
        };
        actionBarBackBtn.setOnClickListener(listener);
        addressSearchBtn.setOnClickListener(listener);
        okBtn.setOnClickListener(listener);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            address1 = data.getStringExtra("result1");
            address2 = data.getStringExtra("result2");


            ad_mdf_zipcode.setText(address1);
            ad_mdf_address.setText(address2);
        }
    }
}