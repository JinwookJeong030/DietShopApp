package com.cookandroid.dietcontrol.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.model.PointGarlicVO;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointGarlicActivity extends AppCompatActivity {
    public static String sessionCSeq;
    private RetrofitAPI api;
    private CustomerVO customerVO;
    private PointGarlicVO[] pointGarlicVO  = new PointGarlicVO[50];
    private Call<String> callSession;
    private Call<PointGarlicVO[]> callPointGarlicVO;
    private Call<CustomerVO> callCustomerVO;
    private TextView actionBarTitle;
    private String pt_name[] = new String[50], pt_point[] = new String[50], pt_date[] = new String[50];
    private int c_totalPoint;
    //액션바부분
    TextView myHowhang, purchaseList, myReview, pointGarlic, couponMugwort;
    //그 외
    TextView pgHoldGarlic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_garlic);
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("내 포인트 조회");
        api = RetrofitInit.getRetrofit();

        myHowhang = findViewById(R.id.move_my_howhang);
        purchaseList = findViewById(R.id.move_purchase_list);
        myReview = findViewById(R.id.move_review_product);
        pointGarlic = findViewById(R.id.move_point_garlic);
        couponMugwort = findViewById(R.id.move_coupon_mugwort);

        pointGarlic.setTextColor(getColor(R.color.orange));
        pgHoldGarlic = findViewById(R.id.pg_hold_garlic);

        callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                sessionCSeq = response.body();

                //1
                ListView listView;
                ListPointGarlicItem.ListPointGarlicItemAdapter adapter;
                //2
                listView = findViewById(R.id.point_garlic_listview);
                //3
                adapter =new ListPointGarlicItem.ListPointGarlicItemAdapter();

                callPointGarlicVO = api.callSelectPointGarlic(sessionCSeq);
                callPointGarlicVO.enqueue(new Callback<PointGarlicVO[]>() {
                    @Override
                    public void onResponse(Call<PointGarlicVO[]> call, Response<PointGarlicVO[]> response) {
                        pointGarlicVO = response.body();


                        if(pointGarlicVO != null) {
                            for(int i = 0; i < pointGarlicVO.length;i++) {
                                pt_name[i] = pointGarlicVO[i].getPt_name();
                                pt_point[i] = String.valueOf(pointGarlicVO[i].getPt_point());
                                pt_date[i] = pointGarlicVO[i].getPt_date();

                                adapter.addPointGarlicItemList(new ListPointGarlicItem(pt_name[i], pt_date[i], pt_point[i]));
                            }
                            listView.setAdapter(adapter);

                        }
                    }
                    @Override
                    public void onFailure(Call<PointGarlicVO[]> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "pointGarlic 실패", Toast.LENGTH_SHORT).show();
                    }
                });

                callCustomerVO = api.callSelectCustomerInfo(sessionCSeq);
                callCustomerVO.enqueue(new Callback<CustomerVO>() {
                    @Override
                    public void onResponse(Call<CustomerVO> call, Response<CustomerVO> response) {
                        customerVO = response.body();
                        c_totalPoint = customerVO.getC_point();

                        pgHoldGarlic.setText(String.valueOf(c_totalPoint));
                    }

                    @Override
                    public void onFailure(Call<CustomerVO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "customer 실패", Toast.LENGTH_SHORT).show();
                    }
                });



                //버튼리스너
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
                            case R.id.move_coupon_mugwort:
                                startActivity(new Intent(getApplicationContext(), CouponMugwortActivity.class));
                                overridePendingTransition(0, 0);
                                finish();
                                break;
                        }
                    }
                };

                myHowhang.setOnClickListener(listener);
                purchaseList.setOnClickListener(listener);
                couponMugwort.setOnClickListener(listener);
                myReview.setOnClickListener(listener);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

}