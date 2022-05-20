package com.cookandroid.dietcontrol.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.MainActivity;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.model.ProductVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.model.OrderListVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
//import com.cookandroid.dietcontrol.model.OrderListVO;
import com.cookandroid.dietcontrol.model.OrderVO;

import static com.cookandroid.dietcontrol.MainActivity.goShopCheck;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pp_purchaseResult extends AppCompatActivity {

    private ListView pp_result_list;
    private pp_purchaseAdapter pp_purchaseAdapter;
    private RetrofitAPI api;
    private TextView actionBarTitle , payCard,payment ,totalPrice ;

    private ImageView actionBarHome;
    OrderVO orderVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp_purchase_result);

        api = RetrofitInit.getRetrofit();

        actionBarTitle =  findViewById(R.id.actionbar_title);
        actionBarTitle.setText("결제 완료");
        actionBarHome = findViewById(R.id.actionbar_left_btn);
        actionBarHome.setImageDrawable(getDrawable(R.drawable.ic_home));
        pp_result_list = (ListView) findViewById(R.id.pp_purchase_result_list);
        pp_purchaseAdapter = new pp_purchaseAdapter(this.getApplicationContext());

        totalPrice = findViewById(R.id.order_total_price);
        payCard = findViewById(R.id.order_card);
        payment = findViewById(R.id.order_payment);

        Call<String> callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

             int c_seq =  Integer.parseInt(response.body());
        Call<String> callSelectOrderMax = api.callSelectOrderMax(c_seq);

        Call<Boolean> callUpdatePoint = api.callUpdatePlusCustomerPoint(Integer.toString(c_seq),"제품구매 포인트적립",100);
        callUpdatePoint.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });



        callSelectOrderMax.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                 int o_seq = Integer.parseInt(response.body());


        Call<OrderVO> callSelectOrderById = api.callSelectOrderById(o_seq);
                callSelectOrderById.enqueue(new Callback<OrderVO>() {
            @Override
            public void onResponse(Call<OrderVO> call, Response<OrderVO> response) {
                orderVO = response.body();

                totalPrice.setText(Integer.toString(orderVO.getO_price()));
                payment.setText(orderVO.getO_pay());
                payCard.setText(orderVO.getO_card());

                Call<ProductVO> callSelectProductBySeq = api.callSelectProductBySeq(orderVO.getP_seq());
                    callSelectProductBySeq.enqueue(new Callback<ProductVO>() {
                        @Override
                        public void onResponse(Call<ProductVO> call, Response<ProductVO> response) {
                            ProductVO productVO = response.body();


                            pp_purchaseAdapter.pp_addItem(productVO.getP_name(), orderVO.getO_price(), productVO.getP_img(), orderVO.getO_cnt() );
                            pp_purchaseAdapter.pp_listViewHeightSetting(pp_purchaseAdapter, pp_result_list);
                            pp_result_list.setAdapter(pp_purchaseAdapter);
                        }

                        @Override
                        public void onFailure(Call<ProductVO> call, Throwable t) {

                        }
                    });



            }

            @Override
            public void onFailure(Call<OrderVO> call, Throwable t) {
            }
        });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        // 홈으로
        Button goHomeBtn = findViewById(R.id.pp_goHome);
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Intent goHomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goHomeIntent);
            }
        });
        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent goHomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goHomeIntent);

            }
        });



    }
}