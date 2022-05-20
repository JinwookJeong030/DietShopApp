package com.cookandroid.dietcontrol.mypage;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.model.OrderList2VO;
import com.cookandroid.dietcontrol.model.OrderVO;
import com.cookandroid.dietcontrol.model.ProductVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseListActivity extends AppCompatActivity {



    private TextView  actionBarTitle,myHowhang, purchaseList, myReview, pointGarlic, couponMugwort;
    private BottomNavigationView botNavView;
    private Fragment fragment = null;

    private ListView mp_purchaseList;
    private mp_purchaseListAdapter mp_purchaseListAdapter;
    private RetrofitAPI api;
    private CustomerVO customerVO;
    private OrderList2VO[] orderList2VOList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("내 주문 조회");

        api = RetrofitInit.getRetrofit();

        myHowhang = findViewById(R.id.move_my_howhang);
        purchaseList = findViewById(R.id.move_purchase_list);
        myReview = findViewById(R.id.move_review_product);
        pointGarlic = findViewById(R.id.move_point_garlic);
        couponMugwort = findViewById(R.id.move_coupon_mugwort);
        botNavView = findViewById(R.id.botNav_main_menu);

        purchaseList.setTextColor(getColor(R.color.orange));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.move_my_howhang:
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.move_review_product:
                        startActivity(new Intent(getApplicationContext(), MyReviewActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.move_point_garlic:
                        startActivity(new Intent(getApplicationContext(),PointGarlicActivity.class));
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
        pointGarlic.setOnClickListener(listener);
        couponMugwort.setOnClickListener(listener);
        myReview.setOnClickListener(listener);



try {
    // ----------------------------------------------------220207
    mp_purchaseList = (ListView) findViewById(R.id.mp_purchase_list);
    mp_purchaseListAdapter = new mp_purchaseListAdapter(this.getApplicationContext());

    Call<String> callSession = api.callGetSession();
    callSession.enqueue(new Callback<String>() {


        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String c_seq = response.body();
            Call<OrderList2VO[]> orderVOCall = api.callSelectOrderByCSEQ(Integer.parseInt(c_seq));
            orderVOCall.enqueue(new Callback<OrderList2VO[]>() {


                @Override
                public void onResponse(Call<OrderList2VO[]> call, Response<OrderList2VO[]> response) {
                    orderList2VOList = response.body();

                    for (int i = 0; i < orderList2VOList.length; i++) {
                        mp_purchaseListAdapter.mp_addItem(orderList2VOList[i].getO_seq(),
                                orderList2VOList[i].getP_img(), orderList2VOList[i].getP_name(),
                                orderList2VOList[i].getP_price(), orderList2VOList[i].getO_date());
                        mp_purchaseList.setAdapter(mp_purchaseListAdapter);
                    }
                }

                @Override
                public void onFailure(Call<OrderList2VO[]> call, Throwable t) {
                }
            });
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    });
}catch (Exception e){

}

    }
}
