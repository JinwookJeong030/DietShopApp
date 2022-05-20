package com.cookandroid.dietcontrol.shop;


import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadImage;
import com.cookandroid.dietcontrol.model.OrderVO;
import com.cookandroid.dietcontrol.model.ProductVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pd_write_reviewAct extends AppCompatActivity {
    private RetrofitAPI api; //api
    private String baseURL = SERVER_URL;

    private RatingBar rev_ratingBar;
    private TextView actionBarTitle;
    private ImageView actionBarBackBtn;
    private EditText rev_reviewText;
    private Button rev_write;

    private ListView reviewOrderList;
    private pp_purchaseAdapter reviewOrderPurchaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pd_write_review);

        actionBarTitle = findViewById(R.id.actionbar_title);
        actionBarTitle.setText("제품 리뷰");
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);

        rev_ratingBar = findViewById(R.id.rev_ratingBar);
        rev_reviewText = findViewById(R.id.rev_reviewText);
        rev_write = findViewById(R.id.rev_write);


        api = RetrofitInit.getRetrofit();



        int pSeq =Integer.parseInt(getIntent().getStringExtra("productId"));

        reviewOrderList = (ListView) findViewById(R.id.review_write_order_list);
        reviewOrderPurchaseAdapter = new pp_purchaseAdapter(this.getApplicationContext());


        Call<String> callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int sessionCSeq = Integer.parseInt(response.body());


                Call<OrderVO[]> callSelectOrderBySEQ = api.callSelectOrderBySEQ( sessionCSeq , pSeq );
                callSelectOrderBySEQ.enqueue(new Callback<OrderVO[]>() {
                    @Override
                    public void onResponse(Call<OrderVO[]> call, Response<OrderVO[]> response) {
                        OrderVO[] orderVO = response.body();
                        int size =response.body().length-1;


                        Call<ProductVO> callSelectProductBySeq = api.callSelectProductBySeq(pSeq);
                        callSelectProductBySeq.enqueue(new Callback<ProductVO>() {
                            @Override
                            public void onResponse(Call<ProductVO> call, Response<ProductVO> response) {
                                ProductVO productVO = response.body();
                                reviewOrderPurchaseAdapter.pp_addItem(productVO.getP_name(),
                                            productVO.getP_price(),
                                            productVO.getP_img(),
                                        orderVO[size].getO_cnt());
                                reviewOrderPurchaseAdapter.pp_listViewHeightSetting(reviewOrderPurchaseAdapter, reviewOrderList);


                            }

                            @Override
                            public void onFailure(Call<ProductVO> call, Throwable t) {

                            }
                        });










                    }

                    @Override
                    public void onFailure(Call<OrderVO[]> call, Throwable t) {

                    }
                });





        reviewOrderList.setAdapter(reviewOrderPurchaseAdapter);











        // 리뷰 작성 버튼
        rev_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<OrderVO[]> OSEQcall = api.callSelectOrderBySEQ(sessionCSeq, pSeq);
                OSEQcall.enqueue(new Callback<OrderVO[]>() {
                    @Override
                    public void onResponse(Call<OrderVO[]> call, Response<OrderVO[]> response) {

                        OrderVO[] orderVO = response.body();
                        int size =response.body().length-1;


                            Call<Void> reviewVOCall = api.callInsertReview(orderVO[size].getO_seq(), sessionCSeq, orderVO[size].getP_seq(),
                                    rev_reviewText.getText().toString(), "", (int) rev_ratingBar.getRating());
                            reviewVOCall.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {

                                    finish();

                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) { }
                            });

                    }
                    @Override
                    public void onFailure(Call<OrderVO[]> call, Throwable t) { }
                });
            }
        });


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });



        // 취소 버튼
        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }
}