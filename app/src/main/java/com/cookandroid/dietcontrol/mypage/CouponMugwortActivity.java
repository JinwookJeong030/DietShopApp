package com.cookandroid.dietcontrol.mypage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.model.CouponVO;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponMugwortActivity extends AppCompatActivity {
    public static String sessionCSeq;
    private RetrofitAPI api;
    private Call<String> callSession;
    private CustomerVO customerVO;
    private CouponVO[] couponVO = new CouponVO[50];
    private Call<CouponVO[]> callCouponVO;
    private Call<CustomerVO> callCustomerVO;
    private String cp_name[] = new String[50], cp_discount[] = new String[50], cp_date[] = new String[50];
    private boolean cp_useable[] = new boolean[50];
    private TextView actionBarTitle;

    TextView myHowhang, purchaseList, myReview, pointGarlic, couponMugwort, useableText, c_use_cnt;
    ImageView cpUseableIcon;

    Button couponEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_mugwort);
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("내 쿠폰 조회");
        api = RetrofitInit.getRetrofit();

        myHowhang = findViewById(R.id.move_my_howhang);
        purchaseList = findViewById(R.id.move_purchase_list);
        myReview = findViewById(R.id.move_review_product);
        pointGarlic = findViewById(R.id.move_point_garlic);
        couponMugwort = findViewById(R.id.move_coupon_mugwort);
        useableText = findViewById(R.id.coupon_isuseable_text);
        couponEnroll = findViewById(R.id.coupon_enroll);
        c_use_cnt = findViewById(R.id.c_useable_cnt);

        couponMugwort.setTextColor(getColor(R.color.orange));


        callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                sessionCSeq = response.body();
                //1
                ListView listView;
                ListCouponItem.ListCouponItemAdapter adapter;
                //2
                listView = findViewById(R.id.coupon_listview);
                //3
                adapter = new ListCouponItem.ListCouponItemAdapter();

                if(useableText.getText().toString().equals("사용가능 쿠폰")) {
                    callCouponVO = api.callSelectCoupon(sessionCSeq);
                    callCouponVO.enqueue(new Callback<CouponVO[]>() {
                        @Override
                        public void onResponse(Call<CouponVO[]> call, Response<CouponVO[]> response) {
                            couponVO = response.body();

                            if (couponVO != null) {

                                for (int i = 0; i < couponVO.length; i++) {
                                    cp_name[i] = couponVO[i].getCp_name();
                                    cp_discount[i] = String.valueOf(couponVO[i].getCp_discount());
                                    cp_date[i] = String.format("%1$tY-%1$tm-%1$td", couponVO[i].getCp_date());
                                    cp_useable[i] = couponVO[i].isCp_useable();

                                    if (cp_useable[i] == true)
                                        adapter.addCouponItemList(new ListCouponItem(cp_name[i], cp_discount[i], cp_date[i], cp_useable[i]));
                                }
                                if (adapter != null)
                                    listView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<CouponVO[]> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


                callCustomerVO = api.callSelectCustomerInfo(sessionCSeq);
                callCustomerVO.enqueue(new Callback<CustomerVO>() {
                    @Override
                    public void onResponse(Call<CustomerVO> call, Response<CustomerVO> response) {
                        customerVO = response.body();

                        c_use_cnt.setText(String.valueOf(customerVO.getC_useable_coupon()));
                    }

                    @Override
                    public void onFailure(Call<CustomerVO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "customer 실패", Toast.LENGTH_SHORT).show();
                    }
                });



                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.move_my_howhang:
                                finish();
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.move_purchase_list:
                                startActivity(new Intent(getApplicationContext(), PurchaseListActivity.class));
                                overridePendingTransition(0, 0);
                                finish();

                                break;
                            case R.id.move_review_product:
                                startActivity(new Intent(getApplicationContext(), MyReviewActivity.class));
                                overridePendingTransition(0, 0);
                                finish();
                                break;
                            case R.id.move_point_garlic:
                                startActivity(new Intent(getApplicationContext(), PointGarlicActivity.class));
                                overridePendingTransition(0, 0);
                                finish();
                                break;
                            case R.id.coupon_enroll:

//                      쿠폰등록부분
                                break;
                        }
                    }
                };
                myHowhang.setOnClickListener(listener);
                purchaseList.setOnClickListener(listener);
                myReview.setOnClickListener(listener);
                pointGarlic.setOnClickListener(listener);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void OnclickUseable(View v) {

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        dlg.setItems(R.array.isUseable, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String[] items = getResources().getStringArray(R.array.isUseable);
                useableText.setText(items[position]);

                callSession = api.callGetSession();
                callSession.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //1
                        ListView listView;
                        ListCouponItem.ListCouponItemAdapter adapter;
                        //2
                        listView = findViewById(R.id.coupon_listview);
                        //3
                        adapter = new ListCouponItem.ListCouponItemAdapter();
                        if(useableText.getText().toString().equals("사용가능 쿠폰")) {
                            callCouponVO = api.callSelectCoupon(sessionCSeq);
                            callCouponVO.enqueue(new Callback<CouponVO[]>() {
                                @Override
                                public void onResponse(Call<CouponVO[]> call, Response<CouponVO[]> response) {
                                    for (int i = 0; i < couponVO.length; i++) {
                                        cp_name[i] = couponVO[i].getCp_name();
                                        cp_discount[i] = String.valueOf(couponVO[i].getCp_discount());
                                        cp_date[i] = String.format("%1$tY-%1$tm-%1$td", couponVO[i].getCp_date());
                                        cp_useable[i] = couponVO[i].isCp_useable();

                                        if (cp_useable[i] == true)
                                            adapter.addCouponItemList(new ListCouponItem(cp_name[i], cp_discount[i], cp_date[i], cp_useable[i]));
                                    }
                                    if (adapter != null)
                                        listView.setAdapter(adapter);
                                }

                                @Override
                                public void onFailure(Call<CouponVO[]> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            callCouponVO = api.callSelectCoupon(sessionCSeq);
                            callCouponVO.enqueue(new Callback<CouponVO[]>() {
                                @Override
                                public void onResponse(Call<CouponVO[]> call, Response<CouponVO[]> response) {
                                    for (int i = 0; i < couponVO.length; i++) {
                                        cp_name[i] = couponVO[i].getCp_name();
                                        cp_discount[i] = String.valueOf(couponVO[i].getCp_discount());
                                        cp_date[i] = String.format("%1$tY-%1$tm-%1$td", couponVO[i].getCp_date());
                                        cp_useable[i] = couponVO[i].isCp_useable();

                                        if (cp_useable[i] == false)
                                            adapter.addCouponItemList(new ListCouponItem(cp_name[i], cp_discount[i], cp_date[i], cp_useable[i]));
                                    }
                                    if (adapter != null)
                                        listView.setAdapter(adapter);
                                }

                                @Override
                                public void onFailure(Call<CouponVO[]> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });


            }
        });

        AlertDialog alert= dlg.create();
        alert.show();
    }
}