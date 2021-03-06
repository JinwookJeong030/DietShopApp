package com.cookandroid.dietcontrol.shop;


import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.model.CouponVO;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.model.ProductVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pp_PurchaseProduct extends AppCompatActivity {

    private ListView pp_purchase_list;
    private pp_purchaseAdapter pp_purchaseAdapter;
    private static CouponVO[] couponVOList;
    private CustomerVO customerVO;
    private ProductVO productVO;
    private RetrofitAPI api;
    private String ImageBaseUrl = SERVER_URL;
    private int finalMoney = 0;
    private TextView actionBarTitle;
    private ImageView actionBarBasketBtn, actionBarBackBtn;
    private TextView pp_pointCount, pp_countCoupon, pp_finalMoney;




    private EditText pp_name,pp_name2,
            pp_address1,pp_address2,pp_address3,
            pp_phone,pp_phone2,
            pp_address1_2,pp_address2_2,pp_address3_2,
            pp_point;
    private CheckBox pp_checkBox;

    private Button pp_addressBtn, pp_addressBtn2, pp_pointBtn, pp_finalBtn;
    
    private static final String TAG = "PP))";
    private String c_seq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp_purchase_product);
        api = RetrofitInit.getRetrofit();

        actionBarTitle =  findViewById(R.id.actionbar_title);
        actionBarBasketBtn =  findViewById(R.id.actionBar_right_btn);
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarTitle.setText("?????? ??????");
        actionBarBasketBtn.setImageDrawable(getDrawable(R.drawable.ic_basket));
        actionBarBackBtn.setImageDrawable(getDrawable(R.drawable.ic_left_arrow));


        // ?????? ?????? ????????????
        pp_name = findViewById(R.id.pp_name);
        pp_address1 = findViewById(R.id.pp_address1);
        pp_address2 = findViewById(R.id.pp_address2);
        pp_address3 = findViewById(R.id.pp_address3);
        pp_phone = findViewById(R.id.pp_phone);
        pp_addressBtn = findViewById(R.id.pp_addressBtn);

        pp_checkBox = findViewById(R.id.pp_checkBox);
        pp_name2 = findViewById(R.id.pp_name2);
        pp_address1_2 = findViewById(R.id.pp_address1_2);
        pp_address2_2 = findViewById(R.id.pp_address2_2);
        pp_address3_2 = findViewById(R.id.pp_address3_2);
        pp_phone2 = findViewById(R.id.pp_phone2);
        pp_addressBtn2 = findViewById(R.id.pp_addressBtn2);

        pp_pointCount = findViewById(R.id.pp_pointCount);
        pp_countCoupon = findViewById(R.id.pp_countCoupon);
        pp_pointBtn = findViewById(R.id.pp_pointBtn);
        pp_point = findViewById(R.id.pp_point);
        pp_finalMoney = findViewById(R.id.pp_finalMoney);

        pp_finalBtn = findViewById(R.id.pp_finalBtn);

        TextView pp_coupon = findViewById(R.id.pp_coupon);

        final int[] originalFinalMoney = {0};
        final int[] tempCouponMoney = {0};
        final int[] tempPointMoney = {0};
        final int[] tempMoney = {0};




        pp_purchase_list = (ListView) findViewById(R.id.pp_purchase_list);
        pp_purchaseAdapter = new pp_purchaseAdapter(this.getApplicationContext());

        // ?????????
        Spinner cardSpinner = (Spinner) findViewById(R.id.pp_cardSpinner);
        ArrayAdapter viewAdapter1 = ArrayAdapter.createFromResource(this, R.array.pp_cardSpinner, android.R.layout.simple_spinner_item);
        viewAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(viewAdapter1);

        Spinner howSpinner = (Spinner) findViewById(R.id.pp_howSpinner);
        ArrayAdapter viewAdapter2 = ArrayAdapter.createFromResource(this, R.array.pp_howSpinner, android.R.layout.simple_spinner_item);
        viewAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howSpinner.setAdapter(viewAdapter2);







        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Call<String> callGetSession = api.callGetSession();

        callGetSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                c_seq =response.body();


                // ?????? ?????? ????????????
                if(c_seq != null){
                    Call<CustomerVO> customerCall = api.callSelectCustomerInfo(c_seq); // ***
                    customerCall.enqueue(new Callback<CustomerVO>() {
                        @Override
                        public void onResponse(Call<CustomerVO> call, Response<CustomerVO> response) {
                            customerVO = response.body();

                            if(customerVO.getC_name() != null) pp_name.setText(customerVO.getC_name());
                            if(customerVO.getAd_zipcode() != null) pp_address1.setText(customerVO.getAd_zipcode());
                            if(customerVO.getAd_address() != null) pp_address2.setText(customerVO.getAd_address());
                            if(customerVO.getAd_subaddress() != null) pp_address3.setText(customerVO.getAd_subaddress());
                            if(customerVO.getC_tell() != null) pp_phone.setText(customerVO.getC_tell());
                            pp_pointCount.setText("?????? " + customerVO.getC_point() + "??? ??????");
                            pp_countCoupon.setText("??? " + customerVO.getC_useable_coupon() + "??? ?????? ??????");


                        }

                        @Override
                        public void onFailure(Call<CustomerVO> call, Throwable t) { }
                    });
                }




                // ???????????? ??????
                ArrayList<Integer> productIds = getIntent().getIntegerArrayListExtra("productIds");

                if(productIds.size() != 0) {
                    finalMoney = 0; // ??????????????? 0?????? ?????????
                    for(int i=0; i<productIds.size(); i++) {
                        Call<ProductVO> productVOCall = api.callSelectProductById(productIds.get(i));
                        productVOCall.enqueue(new Callback<ProductVO>() {
                            @Override
                            public void onResponse(Call<ProductVO> call, Response<ProductVO> response) {
                                productVO = response.body();
                                finalMoney += productVO.getP_price();

                                pp_purchaseAdapter.pp_addItem(productVO.getP_name(), Integer.parseInt(getIntent().getStringExtra("productTotalPrice")), productVO.getP_img(), Integer.parseInt(getIntent().getStringExtra("productAmount")));
                                pp_purchaseAdapter.pp_listViewHeightSetting(pp_purchaseAdapter, pp_purchase_list);

                                pp_purchase_list.setAdapter(pp_purchaseAdapter);
                                // ?????? ????????????

                                pp_finalMoney.setText(getIntent().getStringExtra("productTotalPrice")+"???");
                                originalFinalMoney[0] = Integer.parseInt(pp_finalMoney.getText().toString().replace("???",""));
                            }
                            @Override
                            public void onFailure(Call<ProductVO> call, Throwable t) { }
                        });



                    }

                }





                if(c_seq != null) {
                    // ?????? ?????? ????????????
                    final ArrayList<String> couponList = new ArrayList<String>();
                    Call<CouponVO[]> callSelectCouponById = api.callSelectCouponById(Integer.parseInt(c_seq)); // ***
                    callSelectCouponById.enqueue(new Callback<CouponVO[]>() {
                        @Override
                        public void onResponse(Call<CouponVO[]> call, Response<CouponVO[]> response) {
                            couponVOList = response.body();
                            couponList.add("?????? ?????? ?????? (-0)");
                            for(int i=0; i<couponVOList.length; i++) {
                                couponList.add(couponVOList[i].getCp_name() + "(-" + couponVOList[i].getCp_discount() + ")");
                            }
                        }
                        @Override
                        public void onFailure(Call<CouponVO[]> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });


                    //?????? ?????? ??????
                    final int[] selectedItem = {0};
                    Button pp_couponBtn = findViewById(R.id.pp_couponBtn);
                    pp_couponBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String[] couponListString = couponList.toArray(new String[couponList.size()]);

                            AlertDialog.Builder couponDialog = new AlertDialog.Builder(pp_PurchaseProduct.this);
                            couponDialog.setTitle("????????? ???????????????")
                                    .setSingleChoiceItems(couponListString, 0, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            selectedItem[0] = i;
                                        }
                                    })
                                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String temp = couponListString[selectedItem[0]];
                                            String tempA[] = temp.split("\\(");
                                            pp_coupon.setText(tempA[0]);

                                            Call <Integer> couponCall = api.callSelectCouponPrice(tempA[0]);
                                            couponCall.enqueue(new Callback<Integer>() {
                                                @Override
                                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                    int discount = response.body();
                                                    tempCouponMoney[0] = discount;

                                                    tempMoney[0] = originalFinalMoney[0] - tempCouponMoney[0] - tempPointMoney[0];
                                                    if(tempMoney[0] < 0) tempMoney[0] = 0;

                                                    pp_finalMoney.setText(tempMoney[0] + "???");

                                                }

                                                @Override
                                                public void onFailure(Call<Integer> call, Throwable t) {
                                                    tempCouponMoney[0] = 0;
                                                    tempMoney[0] = originalFinalMoney[0] - tempCouponMoney[0] - tempPointMoney[0];
                                                    pp_finalMoney.setText(tempMoney[0] + "???");

                                                }
                                            });
                                        }
                                    })
                                    .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) { }
                                    });
                            couponDialog.create();
                            couponDialog.show();

                        }
                    });
                }





                // ????????? ?????? ?????? ??????
                pp_pointBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tempPointMoney[0]  = customerVO.getC_point();
                        pp_point.setText(String.valueOf(tempPointMoney[0]));

                        tempMoney[0]=  originalFinalMoney[0] - tempCouponMoney[0]  - tempPointMoney[0];

                        if(tempMoney[0] < 0) tempMoney[0] = 0;
                            pp_finalMoney.setText(tempMoney[0] + "???");



                    }
                });

                pp_point.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        if(!pp_point.getText().toString().equals("")) {
                            if (Integer.parseInt(pp_point.getText().toString()) >
                                    customerVO.getC_point())
                                pp_point.setText(Integer.toString(customerVO.getC_point()));


                            tempPointMoney[0]  = Integer.parseInt(pp_point.getText().toString());


                            tempMoney[0]=  originalFinalMoney[0] - tempCouponMoney[0]  - tempPointMoney[0];

                            if(tempMoney[0] < 0) tempMoney[0] = 0;
                            pp_finalMoney.setText(tempMoney[0]+"???");
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });






                // ?????? ?????? ??????
                pp_finalBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = pp_name2.getText().toString();

                        String add1 = pp_address1_2.getText().toString();

                        String add2 = pp_address2_2.getText().toString();

                        String add3 = pp_address3_2.getText().toString();

                        String phone = pp_phone2.getText().toString();

                        if(name.equals("")||add1.equals("")||add2.equals("")||add3.equals("")|| phone.equals("")) {
                            showDlgAddress("????????? ????????? ?????? ??????????????????!");
                        }else{

                            int resultMoney = Integer.parseInt(pp_finalMoney.getText().toString().replace("???", ""));

                            String usePointST = pp_point.getText().toString();

                            int usePoint;

                            if(usePointST.equals(""))
                                usePoint = 0;
                            else
                                usePoint = Integer.parseInt(usePointST);


                            EditText useCouponET = findViewById(R.id.pp_coupon);
                            String useCoupon = useCouponET.getText().toString();


                            if(productIds.size() != 0) {
                                for(int i=0; i<productIds.size(); i++) {




                                    Call<ProductVO> productVOCall = api.callSelectProductById(productIds.get(i));
                                    productVOCall.enqueue(new Callback<ProductVO>() {
                                        @Override
                                        public void onResponse(Call<ProductVO> call, Response<ProductVO> response) {
                                            productVO = response.body();

                                            Call<Void> callOrderProduct = api.callOrderProduct(
                                                    customerVO.getC_seq(),
                                                    productVO.getP_seq(),
                                                    Integer.parseInt(getIntent().getStringExtra("productAmount")) ,
                                                    name,
                                                    add1,
                                                    add2,
                                                    add3,
                                                    phone,
                                                    resultMoney,
                                                    howSpinner.getSelectedItem().toString(),
                                                    cardSpinner.getSelectedItem().toString(),
                                                    usePoint,
                                                    useCoupon
                                            );


                                            callOrderProduct.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    Call<Void> callProduct = api.callUpdateProductByPSEQ(productVO.getP_seq(), productVO.getP_stock());
                                                    callProduct.enqueue(
                                                            new Callback<Void>() {

                                                                @Override
                                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                                    int CountPoint = customerVO.getC_point() - tempPointMoney[0];
                                                                    int CountCoupon = customerVO.getC_useable_coupon();
                                                                    if(tempCouponMoney[0] != 0) { CountCoupon = CountCoupon - 1; }
                                                                    // customer ????????????
                                                                    Call<Void> call2 = api.callUpdateCustomer(customerVO.getC_seq(), CountPoint, CountCoupon);
                                                                    call2.enqueue(new Callback<Void>() {
                                                                        @Override
                                                                        public void onResponse(Call<Void> call2, Response<Void> response) {
                                                                            // coupon ????????????
                                                                            Call<Void> call3 = api.callUpdateCoupon(customerVO.getC_seq(), pp_coupon.getText().toString(), 0);
                                                                            call3.enqueue(new Callback<Void>() {
                                                                                @Override
                                                                                public void onResponse(Call<Void> call, Response<Void> response) { }

                                                                                @Override
                                                                                public void onFailure(Call<Void> call, Throwable t) {}
                                                                            });
                                                                        }


                                                                        @Override
                                                                        public void onFailure(Call<Void> call2, Throwable t) { }
                                                                    });












                                                                    }
                                                                @Override
                                                                public void onFailure(Call<Void> call, Throwable t) { }
                                                            });
                                                }
                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) { }
                                            });



                                            Call<Boolean> callUpdatePoint = api.callUpdateMinusCustomerPoint(c_seq, Integer.toString(usePoint));
                                            callUpdatePoint.enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {





                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {

                                                }
                                            });





                                        }
                                        @Override
                                        public void onFailure(Call<ProductVO> call, Throwable t) { }
                                    });


                                    Intent goPurchaseResult = new Intent(getApplicationContext(), pp_purchaseResult.class);

                                    startActivity(goPurchaseResult);


                                }



                            }
                        }}
                });


                // ???????????? ?????? ??????
                pp_addressBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent goAdressAPI = new Intent(getApplicationContext(), addressAPI.class);
                        goAdressAPI.putExtra("address1", "");
                        goAdressAPI.putExtra("address2", "");
                        startActivityForResult(goAdressAPI, 0);
                    }
                });
                pp_addressBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent goAdressAPI = new Intent(getApplicationContext(), addressAPI.class);
                        goAdressAPI.putExtra("address1", "");
                        goAdressAPI.putExtra("address2", "");
                        startActivityForResult(goAdressAPI, 1);
                    }
                });

                // ????????? ????????? ?????? ????????????
                pp_checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(pp_checkBox.isChecked()) {
                            pp_name2.setText(pp_name.getText().toString());
                            pp_address1_2.setText(pp_address1.getText().toString());
                            pp_address2_2.setText(pp_address2.getText().toString());
                            pp_address3_2.setText(pp_address3.getText().toString());
                            pp_phone2.setText(pp_phone.getText().toString());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });






    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                String address1 = data.getStringExtra("result1");
                String address2 = data.getStringExtra("result2");

                EditText pp_address1 = findViewById(R.id.pp_address1);
                EditText pp_address2 = findViewById(R.id.pp_address2);

                pp_address1.setText(address1);
                pp_address2.setText(address2);
            }
        } else if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String address1 = data.getStringExtra("result1");
                String address2 = data.getStringExtra("result2");

                EditText pp_address1_2 = findViewById(R.id.pp_address1_2);
                EditText pp_address2_2 = findViewById(R.id.pp_address2_2);

                pp_address1_2.setText(address1);
                pp_address2_2.setText(address2);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    void showDlgAddress(String message) {
        android.app.AlertDialog.Builder msgBuilder = new android.app.AlertDialog.Builder(this)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

            msgBuilder.setMessage(message);

        android.app.AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();

    }

}