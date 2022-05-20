package com.cookandroid.dietcontrol.shop;

import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.MainActivity;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.model.ProductVO;
import com.cookandroid.dietcontrol.util.LoadingDialog;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {
    private GridView myGrid;
    private ct_shopCateAdapter shopCateAdapter;
    private TextView actionBarTitle;
    private ImageView actionBarBasket;
    private RetrofitAPI api;
    private String ImageBaseUrl = SERVER_URL;
    ProductVO[] productVOList;
    private String chooseCate = "전체";
    private String chooseSpinner = "기본순";
    ImageButton cate_btn0,cate_btn1,cate_btn2,cate_btn3,cate_btn4,cate_btn5,cate_btn6,cate_btn7;
    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        actionBarTitle = v.findViewById(R.id.actionbar_title);
        actionBarBasket = v.findViewById(R.id.actionBar_right_btn);
        actionBarTitle.setText("다이어트 식품");
        actionBarBasket.setImageDrawable(getContext().getDrawable(R.drawable.ic_basket));
        myGrid = (GridView)v.findViewById(R.id.shop_grid);
        // +api

            api = RetrofitInit.getRetrofit();

        // 카테고리 선택 시 이동
        cate_btn0 = v.findViewById(R.id.cate_btn0);
        cate_btn1 = v.findViewById(R.id.cate_btn1);
        cate_btn2 = v.findViewById(R.id.cate_btn2);
        cate_btn3 = v.findViewById(R.id.cate_btn3);
        cate_btn4 = v.findViewById(R.id.cate_btn4);
        cate_btn5 = v.findViewById(R.id.cate_btn5);
        cate_btn6 = v.findViewById(R.id.cate_btn6);
        cate_btn7 = v.findViewById(R.id.cate_btn7);

        cate_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "전체";
                addProductList(chooseCate, chooseSpinner);
            }
        });



        cate_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "닭가슴살";
                addProductList(chooseCate, chooseSpinner);
            }
        });
        cate_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "도시락";
                addProductList(chooseCate, chooseSpinner);
            }
        });
        cate_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "샐러드";
                addProductList(chooseCate, chooseSpinner);
            }
        });
        cate_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "과채";
                addProductList(chooseCate, chooseSpinner);
            }
        });
        cate_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "베이커리";
                addProductList(chooseCate, chooseSpinner);
            }
        });
        cate_btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "간편식";
                addProductList(chooseCate, chooseSpinner);
            }
        });
        cate_btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCate = "음료";
                addProductList(chooseCate, chooseSpinner);
            }
        });

        // 그리드뷰 아이템 클릭 시 이벤트 처리
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ct_cateItem item = (ct_cateItem) shopCateAdapter.getItem(i);
                Intent goProductDetailsIntent = new Intent(getActivity().getApplicationContext(), pd_ProductDetails.class);
                goProductDetailsIntent.putExtra("product", productVOList[i]);
                startActivity(goProductDetailsIntent);
            }
        });

        // 스피너 목록 지정
        Spinner viewSpinner = (Spinner) v.findViewById(R.id.shop_spinner_view);
        ArrayAdapter viewAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.shop_spinner, android.R.layout.simple_spinner_item);
        viewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewSpinner.setAdapter(viewAdapter);

        // 기본순, 낮은가격순, 높은가격순 정렬
        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(viewSpinner.getItemAtPosition(i).equals("낮은가격순")) chooseSpinner = "낮은가격순";
                else if(viewSpinner.getItemAtPosition(i).equals("높은가격순")) chooseSpinner = "높은가격순";
                else chooseSpinner = "기본순";

                addProductList(chooseCate , chooseSpinner);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return v;
    }


    // 상품 추가
    void addProductList(String category, String order) {
        shopCateAdapter = new ct_shopCateAdapter(getContext());



            Call<ProductVO[]> call;
            if (order.equals("낮은가격순")) call = api.callSelectProductByCateAsc(category);
            else if (order.equals("높은가격순")) call = api.callSelectProductByCateDesc(category);
            else call = api.callSelectProductByCate(category);


            call.enqueue(new Callback<ProductVO[]>() {
                @Override
                public void onResponse(Call<ProductVO[]> call, Response<ProductVO[]> response) {
                    productVOList = response.body();
                    URL url = null;

                    for (int i = 0; i < productVOList.length; i++) {
                        try {
                            String getP_img = productVOList[i].getP_img();

                            url = new URL(SERVER_URL + "img/product/" + getP_img);


                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                        String p_img = String.valueOf(url);
                        String p_name = productVOList[i].getP_name();
                        int p_price = productVOList[i].getP_price();
                        double p_rating = productVOList[i].getP_rating();


                        shopCateAdapter.addItem(p_img, p_name, p_price, p_rating);


                    }
                    myGrid.setAdapter(shopCateAdapter);
                }

                @Override
                public void onFailure(Call<ProductVO[]> call, Throwable t) {

                }
            });

        }





}