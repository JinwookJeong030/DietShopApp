package com.cookandroid.dietcontrol.shop;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cookandroid.dietcontrol.MainActivity;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadImage;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.model.ProductVO;
import com.cookandroid.dietcontrol.model.ReviewVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.cookandroid.dietcontrol.MainActivity.goLoginCheck;

import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import java.util.ArrayList;

public class pd_ProductDetails extends AppCompatActivity {

    private Dialog dialog;
    private TextView pd_productName,pd_productPrice,actionBarTitle,  productAmount, pd_review_count, productPriceTextView, ppSales, ppReviewCount;
    private ImageView actionBarBasketBtn, actionBarBackBtn, pd_productImage, pd_productDetailImage;
    private RatingBar pd_ratingBar;
    private ListView pd_review_list;
    private pd_reviewAdapter pd_reviewAdapter;
    private String baseURL = SERVER_URL;

    private  ProductVO productVO;
    private Button pp_plus, pp_minus;
    private Spinner viewSpinner;


    public static String sessionCSeq="0";
    private RetrofitAPI api;
    private ReviewVO[] reviewVOList;
    private CustomerVO[] customerVOList;

    private String chooseSpinner = "평점순";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        api = RetrofitInit.getRetrofit();
        actionBarTitle =  findViewById(R.id.actionbar_title);
        actionBarBasketBtn =  findViewById(R.id.actionBar_right_btn);
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarTitle.setText("다이어트 식품");
        actionBarBasketBtn.setImageDrawable(getDrawable(R.drawable.ic_basket));
        actionBarBackBtn.setImageDrawable(getDrawable(R.drawable.ic_left_arrow));

        pp_plus = findViewById(R.id.pp_plus);
        pp_minus= findViewById(R.id.pp_minus);
        productAmount=findViewById(R.id.pp_purcahse_product_amount);


        productPriceTextView = findViewById(R.id.pp_purchase_product_price);


        pd_productImage = findViewById(R.id.pd_productImage);
        pd_productName = findViewById(R.id.pd_productName);
        pd_productPrice = findViewById(R.id.pd_productPrice);
        pd_ratingBar = findViewById(R.id.product_detail_top_ratingbar);
        pd_productDetailImage = findViewById(R.id.pd_productDetailImage);
        pd_review_count = findViewById(R.id.pd_review_count);
        ppSales= findViewById(R.id.product_detail_sales);
        ppReviewCount = findViewById(R.id.product_detail_review_amount);
        productVO = (ProductVO) getIntent().getSerializableExtra("product");

        LoadImage loadImage1 = new LoadImage(baseURL +"img/product/"+ productVO.getP_img(), pd_productImage);
        loadImage1.execute();

        LoadImage loadImage2 = new LoadImage(baseURL +"img/product/"+ productVO.getP_content(), pd_productDetailImage);
        loadImage2.execute();

        pd_productName.setText(productVO.getP_name());
        pd_productPrice.setText(Integer.toString(productVO.getP_price()));
        productPriceTextView.setText(Integer.toString(productVO.getP_price()));


        ppSales.setText(Integer.toString(productVO.getP_sales()));


        // 스피너
        viewSpinner = (Spinner) findViewById(R.id.pd_spinner);
        ArrayAdapter viewAdapter = ArrayAdapter.createFromResource(this, R.array.pd_spinner, android.R.layout.simple_spinner_item);
        viewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewSpinner.setAdapter(viewAdapter);








        // 리뷰작성
        Button pd_write_review = findViewById(R.id.pd_write_review);
        pd_write_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Call<String> callGetSession = api.callGetSession();
                    callGetSession.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String sessionCSeq = response.body();

                    Call<Boolean> booleanCall = api.callCheckPurchaseProduct(Integer.parseInt(sessionCSeq), productVO.getP_seq());
                    booleanCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> booleanCall, Response<Boolean> response) {
                            if(response.body()) {
                                // 구매내역이 있을 경우
                                Intent goWriteReviewIntent = new Intent(getApplicationContext(), pd_write_reviewAct.class);
                                goWriteReviewIntent.putExtra("productId", Integer.toString(productVO.getP_seq()));
                                startActivity(goWriteReviewIntent);
                            } else {
                                Toast.makeText(getApplicationContext(), "미구매상품입니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Boolean> booleanCall, Throwable t) { }
                    });
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
                }

            }

        });

        // 구매하기 버튼 클릭 이벤트
        Button pd_purchaseBtn = findViewById(R.id.pd_purchaseBtn);
        pd_purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<String>call = api.callGetSession();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Intent goPurchaseIntent = new Intent(getApplicationContext(), pp_PurchaseProduct.class);
                        ArrayList<Integer> productIds = new ArrayList<Integer>();
                        productIds.add(productVO.getP_seq());
                        goPurchaseIntent.putExtra("productIds", productIds);
                        goPurchaseIntent.putExtra("productTotalPrice",productPriceTextView.getText().toString());
                        goPurchaseIntent.putExtra("productAmount",productAmount.getText().toString());

                        startActivity(goPurchaseIntent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(pd_ProductDetails.this);
                        builder.setTitle("상품 구매는 로그인이 필요합니다.\n로그인화면으로 이동하시겠습니까?");
                        builder.setCancelable(true);
                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent goLogin = new Intent(getApplicationContext(), MainActivity.class);
                                goLoginCheck = true;
                                startActivity(goLogin);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();





                    }
                });
            }
        });
        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pp_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int cnt= Integer.parseInt(productAmount.getText().toString());
                if(cnt<10) {
                    cnt += 1;
                    productAmount.setText(Integer.toString(cnt));
                    int productPrice = Integer.parseInt(pd_productPrice.getText().toString());
                    int totalPrice = productPrice * cnt;
                    productPriceTextView.setText(Integer.toString(totalPrice));
                }else{
                    Toast.makeText(getApplicationContext(),"10개 이상은 구매할수 없습니다!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        pp_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt= Integer.parseInt(productAmount.getText().toString());
                if(cnt>1) {


                    cnt -= 1;
                    productAmount.setText(Integer.toString(cnt));
                    int productPrice = Integer.parseInt(pd_productPrice.getText().toString());
                    int totalPrice = productPrice * cnt;

                    productPriceTextView.setText(Integer.toString(totalPrice) );
                }
            }
        });






    }


    @Override
    protected void onResume() {
        super.onResume();
        //리뷰 명단 작성
        final ArrayList<Integer> customerListCSEQ = new ArrayList<Integer>();
        final ArrayList<String> customerListAlias = new ArrayList<String>();

        Call<CustomerVO[]> customerVOCall = api.callSelectCustomer();
        customerVOCall.enqueue(new Callback<CustomerVO[]>() {
            @Override
            public void onResponse(Call<CustomerVO[]> call, Response<CustomerVO[]> response) {
                customerVOList = response.body();
                for(int i=0; i<customerVOList.length; i++) {
                    customerListCSEQ.add(customerVOList[i].getC_seq());
                    customerListAlias.add(customerVOList[i].getC_alias());
                }

                addReview(productVO, customerListCSEQ, customerListAlias);

                // 스피너 선택 시 변경
                viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(viewSpinner.getItemAtPosition(i).equals("최신순")) chooseSpinner = "최신순";
                        else chooseSpinner = "평점순";

                        addReview(productVO, customerListCSEQ, customerListAlias);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }
                });

            }
            @Override
            public void onFailure(Call<CustomerVO[]> call, Throwable t) {}
        });

        Call<Integer> integerCall = api.callSelectCountReview(productVO.getP_seq());
        integerCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int count = response.body();
                pd_review_count.setText(Integer.toString(count));
                ppReviewCount.setText(Integer.toString(count));
                pd_ratingBar.setRating((float)productVO.getP_rating());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });







    }

    void addReview(ProductVO productVO, ArrayList<Integer> customerListCSEQ, ArrayList<String> customerListAlias) {

        pd_review_list = (ListView) findViewById(R.id.pd_review_list);
        pd_reviewAdapter = new pd_reviewAdapter(this.getApplicationContext());

        if(chooseSpinner.equals("최신순")) {
            // 리뷰 추가(최신순)
            Call<ReviewVO[]> reviewVOCall = api.callSelectReviewDate(productVO.getP_seq());
            reviewVOCall.enqueue(new Callback<ReviewVO[]>() {
                @Override
                public void onResponse(Call<ReviewVO[]> call, Response<ReviewVO[]> response) {
                    reviewVOList = response.body();

                    for(int i=0; i<reviewVOList.length;i++) {

                        String CSEQ = String.valueOf(reviewVOList[i].getC_seq());
                        int count = 0;

                        for(int j=0; j<customerListCSEQ.size(); j++) {
                            if(String.valueOf(customerListCSEQ.get(j)).equals(CSEQ)) count = j;
                        }

                        String name = CSEQ.replace(CSEQ, customerListAlias.get(count));
                        String re_date = reviewVOList[i].getRe_date().toString();
                        String re_img = reviewVOList[i].getRe_img().toString();
                        String re_contents = reviewVOList[i].getRe_contents();
                        int re_rating = reviewVOList[i].getRe_rating();

                        pd_reviewAdapter.pd_addItem(name, re_date, re_img, re_contents, re_rating);
                    }
                    pd_reviewAdapter.pd_listViewHeightSetting(pd_reviewAdapter, pd_review_list);
                    pd_review_list.setAdapter(pd_reviewAdapter);
                }
                @Override
                public void onFailure(Call<ReviewVO[]> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            // 리뷰 추가(별점순)
            Call<ReviewVO[]> reviewVOCall = api.callSelectReviewRating(productVO.getP_seq());
            reviewVOCall.enqueue(new Callback<ReviewVO[]>() {
                @Override
                public void onResponse(Call<ReviewVO[]> call, Response<ReviewVO[]> response) {
                    reviewVOList = response.body();

                    for(int i=0; i<reviewVOList.length;i++) {

                        String CSEQ = String.valueOf(reviewVOList[i].getC_seq());
                        int count = 0;

                        for(int j=0; j<customerListCSEQ.size(); j++) {
                            if(String.valueOf(customerListCSEQ.get(j)).equals(CSEQ)) count = j;
                        }

                        String name = CSEQ.replace(CSEQ, customerListAlias.get(count));
                        String re_date = reviewVOList[i].getRe_date().toString();
                        String re_img = reviewVOList[i].getRe_img().toString();
                        String re_contents = reviewVOList[i].getRe_contents();
                        int re_rating = reviewVOList[i].getRe_rating();

                        pd_reviewAdapter.pd_addItem(name, re_date, re_img, re_contents, re_rating);
                    }
                    pd_reviewAdapter.pd_listViewHeightSetting(pd_reviewAdapter, pd_review_list);
                    pd_review_list.setAdapter(pd_reviewAdapter);
                }
                @Override
                public void onFailure(Call<ReviewVO[]> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }







    }



}