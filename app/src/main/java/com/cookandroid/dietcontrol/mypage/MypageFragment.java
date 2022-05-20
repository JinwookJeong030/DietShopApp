package com.cookandroid.dietcontrol.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.MainActivity;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadingDialog;
import com.cookandroid.dietcontrol.util.UtilStr;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends Fragment {

    public static String sessionCSeq;
    private CustomerVO customerVO;
    private RetrofitAPI api;
    private Call<String> callSession;
    private Call<CustomerVO> callCustomerVO;
    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    
    private String mParam1;
    private String mParam2;
  
    private TextView actionBarTitle, myIDTextView;
    private TextView couponTextView, pointTextView;
    private Button logoutBtn;


    int couponCnt, pointCnt;


    public MypageFragment() {
        // Required empty public constructor
    }

    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.fragment_mypage, container, false);
        actionBarTitle=v.findViewById(R.id.actionbar_title);
        actionBarTitle.setText("마이페이지");
        
        
        myIDTextView = v.findViewById(R.id.page_id_info);
        couponTextView = v.findViewById(R.id.mypage_coupon_cnt);
        pointTextView = v.findViewById(R.id.mypage_garlic_cnt);
        logoutBtn =v.findViewById(R.id.mypage_logout_btn);

        api = RetrofitInit.getRetrofit();

        callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                sessionCSeq = response.body();
                ListView listView;
                ListMypageItem.ListMypageItemAdapter adapter;
                //2
                listView = v.findViewById(R.id.mypage_listview);
                //3
                adapter = new ListMypageItem.ListMypageItemAdapter();

                if (sessionCSeq != null) {

                    callCustomerVO = api.callSelectCustomerInfo(sessionCSeq);
                    callCustomerVO.enqueue(new Callback<CustomerVO>() {
                        @Override
                        public void onResponse(Call<CustomerVO> call, Response<CustomerVO> response) {
                            if (response.isSuccessful()) {
                                customerVO = response.body();
                                myIDTextView.setText(customerVO.getC_alias() + "님");
                                pointCnt = customerVO.getC_point();
                                couponCnt = customerVO.getC_useable_coupon();
                                couponTextView.setText(Integer.toString(couponCnt));
                                pointTextView.setText(Integer.toString(pointCnt));

                                adapter.addMapageItem(new ListMypageItem("구매내역"));
                                adapter.addMapageItem(new ListMypageItem("마늘", pointCnt + "개"));
                                adapter.addMapageItem(new ListMypageItem("쿠폰", couponCnt + "개"));
                                adapter.addMapageItem(new ListMypageItem("내가 쓴 글"));
                            } else {
                                //!!통신 실패시...
                                myIDTextView.setText("통신 실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<CustomerVO> call, Throwable t) {

                        }
                    });
                }


                //1



                //4
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        ListMypageItem listMypageItem = (ListMypageItem) adapter.getItem(position);
                        Intent intent = null;
                        switch (listMypageItem.getMypageTitle()) {
                            case "구매내역":
                                intent = new Intent(getActivity(), PurchaseListActivity.class);
                                startActivity(intent);
                                break;
                            case "마늘":
                                intent = new Intent(getActivity(), PointGarlicActivity.class);
                                startActivity(intent);
                                break;
                            case "쿠폰":
                                intent = new Intent(getActivity(), CouponMugwortActivity.class);
                                startActivity(intent);
                                break;
                            case "내가 쓴 글":
                                intent = new Intent(getActivity(), MyReviewActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });

                LinearLayout mypageGoInfo, mypageGoCoupon, mypageGoGarilc;

                mypageGoInfo = (LinearLayout) v.findViewById(R.id.mypage_go_info);
                mypageGoCoupon = (LinearLayout) v.findViewById(R.id.mypage_go_coupon_btn);
                mypageGoGarilc = (LinearLayout) v.findViewById(R.id.mypage_go_garlic_btn);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = null;
                        switch (view.getId()) {
                            case R.id.mypage_go_info:
                                intent = new Intent(getActivity(), ClientInfoModify.class);
                                startActivity(intent);
                                break;
                            case R.id.mypage_go_coupon_btn:
                                intent = new Intent(getActivity(), CouponMugwortActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.mypage_go_garlic_btn:
                                intent = new Intent(getActivity(), PointGarlicActivity.class);
                                startActivity(intent);
                                break;
                                /**!! 22-02-01 수정부분***********************************************/
                            case R.id.mypage_logout_btn:

                                LoadingDialog loadingDialog = new LoadingDialog(getContext());
                                loadingDialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            logoutLoginSession();
                                            MainActivity mainActivity = (MainActivity) getActivity();
                                            mainActivity.fragmentChange();
                                            loadingDialog.cancel();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 1000);



                                break;
                                /*************************************************/


                        }
                    }
                };

                mypageGoInfo.setOnClickListener(listener);
                mypageGoCoupon.setOnClickListener(listener);
                mypageGoGarilc.setOnClickListener(listener);
                logoutBtn.setOnClickListener(listener);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return v;
    }

    /**!! 22-02-01 수정부분****************************************************************************************/

    /**
     * 로그아웃
     */
    public void logoutLoginSession() {

        Call<Boolean> call = api.callLogout();
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


        Context context = getActivity().getApplicationContext();
        SharedPreferences loginLog = context.getSharedPreferences(UtilStr.SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginLog.edit();
        editor.putString(UtilStr.SESSION_C_SEQ, "0");
        editor.apply();


    }







}