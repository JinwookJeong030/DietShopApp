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

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.model.PdJoinReviewVO;
import com.cookandroid.dietcontrol.model.ProductVO;
import com.cookandroid.dietcontrol.model.ReviewVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewActivity extends AppCompatActivity {
    public static String sessionCSeq;
    private RetrofitAPI api;
    private Call<String> callSession;
    private CustomerVO customerVO;
    private PdJoinReviewVO[] pdJoinReviewVO = new PdJoinReviewVO[50];
    private Call<PdJoinReviewVO[]> callPdJoinReviewVO;
    private Call<CustomerVO> callCustomerVO;
    private String  re_date[] = new String[50], re_contents[] = new String[50], p_name[] = new String[50];
    private int re_rating[] = new int[50];

    private TextView myHowhang, purchaseList, myReview, pointGarlic, couponMugwort, actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        api = RetrofitInit.getRetrofit();
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("내 리뷰 조회");
        myHowhang = findViewById(R.id.move_my_howhang);
        purchaseList = findViewById(R.id.move_purchase_list);
        myReview = findViewById(R.id.move_review_product);
        pointGarlic = findViewById(R.id.move_point_garlic);
        couponMugwort = findViewById(R.id.move_coupon_mugwort);

        myReview.setTextColor(getColor(R.color.orange));




    }

    @Override
    protected void onResume() {
        super.onResume();

        callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                sessionCSeq = response.body();
                //1
                ListView listView;
                ListMyReviewItem.ListMyReviewItemAdapter adapter;
                //2
                listView = findViewById(R.id.my_review_listView);
                //3
                adapter = new ListMyReviewItem.ListMyReviewItemAdapter();
                //4
                callPdJoinReviewVO = api.callSelectPdJoinReviewVO(sessionCSeq);
                callPdJoinReviewVO.enqueue(new Callback<PdJoinReviewVO[]>() {
                    @Override
                    public void onResponse(Call<PdJoinReviewVO[]> call, Response<PdJoinReviewVO[]> response) {
                        pdJoinReviewVO = response.body();
                        if(pdJoinReviewVO != null) {
                            for (int i = 0; i < pdJoinReviewVO.length; i++) {

                                re_date[i] = pdJoinReviewVO[i].getRe_date();
                                re_contents[i] = pdJoinReviewVO[i].getRe_contents();
                                re_rating[i] = pdJoinReviewVO[i].getRe_rating();
                                p_name[i] = pdJoinReviewVO[i].getP_name();

                                adapter.addMyReviewItemList(new ListMyReviewItem(
                                        re_date[i], p_name[i], re_contents[i], re_rating[i]));

                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    showDlgDeleteReview(pdJoinReviewVO[i].getRe_seq());

                                }
                            });


                        }
                        else
                            Toast.makeText(getApplicationContext(), "pdJoinReviewVO 값없음", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onFailure(Call<PdJoinReviewVO[]> call, Throwable t) {
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
                myReview.setOnClickListener(listener);
                pointGarlic.setOnClickListener(listener);
                couponMugwort.setOnClickListener(listener);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "customer 실패", Toast.LENGTH_SHORT).show();
            }
        });





    }

    void showDlgDeleteReview(String re_seq) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MyReviewActivity.this)
                .setMessage("정말로 삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<Boolean> callDeleteReview = api.callDeleteReviewList(re_seq);
                        callDeleteReview.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body()) {
                                    Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                                    callSession = api.callGetSession();
                                    callSession.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            sessionCSeq = response.body();
                                            //1
                                            ListView listView;
                                            ListMyReviewItem.ListMyReviewItemAdapter adapter;
                                            //2
                                            listView = findViewById(R.id.my_review_listView);
                                            //3
                                            adapter = new ListMyReviewItem.ListMyReviewItemAdapter();
                                            //4
                                            callPdJoinReviewVO = api.callSelectPdJoinReviewVO(sessionCSeq);
                                            callPdJoinReviewVO.enqueue(new Callback<PdJoinReviewVO[]>() {
                                                @Override
                                                public void onResponse(Call<PdJoinReviewVO[]> call, Response<PdJoinReviewVO[]> response) {
                                                    pdJoinReviewVO = response.body();
                                                    if(pdJoinReviewVO != null) {
                                                        for (int i = 0; i < pdJoinReviewVO.length; i++) {

                                                            re_date[i] = pdJoinReviewVO[i].getRe_date();
                                                            re_contents[i] = pdJoinReviewVO[i].getRe_contents();
                                                            re_rating[i] = pdJoinReviewVO[i].getRe_rating();
                                                            p_name[i] = pdJoinReviewVO[i].getP_name();

                                                            adapter.addMyReviewItemList(new ListMyReviewItem(
                                                                    re_date[i], p_name[i], re_contents[i], re_rating[i]));

                                                        }
                                                        listView.setAdapter(adapter);
                                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                showDlgDeleteReview(pdJoinReviewVO[i].getRe_seq());

                                                            }
                                                        });


                                                    }
                                                    else
                                                        Toast.makeText(getApplicationContext(), "pdJoinReviewVO 값없음", Toast.LENGTH_SHORT).show();

                                                }
                                                @Override
                                                public void onFailure(Call<PdJoinReviewVO[]> call, Throwable t) {
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
                                            myReview.setOnClickListener(listener);
                                            pointGarlic.setOnClickListener(listener);
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "customer 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }else
                                    Toast.makeText(getApplicationContext(),"알수없는 오류로 삭제가 안됐습니다!",Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"알수없는 오류로 삭제가 안됐습니다!",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("아니요", null);

        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }


}