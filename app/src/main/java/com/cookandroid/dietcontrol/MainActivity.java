package com.cookandroid.dietcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.dietmanagement.DietManagementFragment;
import com.cookandroid.dietcontrol.eventnotice.EventNoticeFragment;
import com.cookandroid.dietcontrol.login.Login;
import com.cookandroid.dietcontrol.shop.ShopFragment;
import com.cookandroid.dietcontrol.mypage.MypageFragment;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.ErrorDialog;
import com.cookandroid.dietcontrol.util.UtilStr;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public String sessionCSeq;
    RetrofitAPI api;
    BottomNavigationView botNavView;
    Fragment fragment = null;
    MenuItem item =null;


    //jy
    public static Boolean goShopCheck = false;
    public static Boolean goLoginCheck = false;
    //



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        api = RetrofitInit.getRetrofit();

        serverNetworkCheck();





        sessionCSeq = loadLoginSession();
        botNavView = findViewById(R.id.botNav_main_menu);

        if(item == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_controler, new ShopFragment()).commit();
            botNavView.setSelectedItemId(R.id.shop_category);
        }



        if(goShopCheck) {
            goShopCheck = false;
            fragment = new ShopFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.body_controler, fragment).commit();
            botNavView.setSelectedItemId(R.id.shop);
        }
        if(goLoginCheck) {
            goLoginCheck = false;
            fragment = new Login();
            getSupportFragmentManager().beginTransaction().replace(R.id.body_controler, fragment).commit();
        }



        botNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){


                    case R.id.shop:
                        serverNetworkCheck();
                            api.callGetSession();
                            fragment = new ShopFragment();


                        break;

                    case R.id.shop_event_notice:
                        serverNetworkCheck();
                        fragment = new EventNoticeFragment();

                        break;

                    case R.id.diet_menagement:
                        serverNetworkCheck();

                        sessionCSeq = loadLoginSession();

                        if(sessionCSeq==null || sessionCSeq.equals("0")) {
                            fragment = new Login();
                        }
                        else {
                            fragment = new DietManagementFragment();
                        }


                        break;

                    case R.id.mypage:
                        serverNetworkCheck();
                            sessionCSeq = loadLoginSession();
                            if (sessionCSeq == null || sessionCSeq.equals("0")) {

                                fragment = new Login();

                            } else {
                                fragment = new MypageFragment();
                            }

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_controler, fragment).commit();



                return true;
            }
        });
    }


     @Override
     public void onResume() {
         super.onResume();
         sessionCSeq = loadLoginSession();
         serverNetworkCheck();
     }
     
     

    /**
     *  다른 액티비티, 프레그먼트에서 메인액티비티를 홈프레그먼트로 이동
     */
    public void fragmentChange(){

        botNavView.setSelectedItemId(R.id.shop);
            fragment = new ShopFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.body_controler, fragment).commit();


    }
    /**
     *  다른 액티비티, 프레그먼트에서 메인액티비티를 다이어트 로그 프레그먼트로 이동
     */
    public void fragmentChangeDietLog(){

        botNavView.setSelectedItemId(R.id.diet_menagement);
        fragment = new DietManagementFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.body_controler, fragment).commit();

    }

    /**
     * 앱 시작시 로그인한 적이 있으면 로그인 상태 유지
     * @return
     */
    public String loadLoginSession(){

        String sessionLoginC_Seq ="";

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(UtilStr.SESSION,Context.MODE_PRIVATE);
        sessionLoginC_Seq = sharedPref.getString(UtilStr.SESSION_C_SEQ, "0");


        sessionCSeq=sessionLoginC_Seq;
        Call<String> call = api.callLoginFromApp(sessionCSeq);
        if(!sessionCSeq.equals("0")) {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    sessionCSeq = response.body();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    sessionCSeq ="0";
                }
            });
        }

        return sessionLoginC_Seq;
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("앱을 종료하시겠습니까?");
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
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void serverNetworkCheck(){
        RetrofitAPI api = RetrofitInit.getRetrofit();
        Call<Boolean> call = api.dbconTest();
        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                builder.setMessage("서버와의 연결이 원활하지 않습니다")
                        .setCancelable(false)
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                moveTaskToBack(true);
                                finishAndRemoveTask();
                                android.os.Process.killProcess(android.os.Process.myPid());


                            }
                        }).show();

            }

        });




    }




}


