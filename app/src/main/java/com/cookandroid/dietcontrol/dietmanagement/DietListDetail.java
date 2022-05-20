package com.cookandroid.dietcontrol.dietmanagement;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadImage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietListDetail extends Activity {


    private LinearLayout dietLayout, exerciseLayout;
    private TextView actionBarTitle ,contentsTextView,
            periodTextView, dateTextView,timeTextView,
            mealTextView, kcalTextView,
            weightTextView,heightTextView, fatTextView, partTextView, minuteTextView;
    private String  object_seq,sessionC_seq,periodStr, dateTimeStr,dateStr,timeStr ,contentsStr,imgUrl,
                    mealStr,kcalStr,
                    weightStr,heightStr,fatStr,partStr,minuteStr;

    private ImageView btnBack, btnMenu ,imgImageView;
    private RetrofitAPI api = RetrofitInit.getRetrofit();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_detail_dietlog);
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("다이어트 로그 상세");
        btnBack =findViewById(R.id.actionbar_left_btn);
        btnBack.setImageResource(R.drawable.ic_left_arrow);
        btnMenu = findViewById(R.id.actionBar_right_btn);
        btnMenu.setImageDrawable(getDrawable(R.drawable.ic_diet_menu));

        imgImageView = findViewById(R.id.detail_diet_img);
        contentsTextView = findViewById(R.id.detail_diet_contents);
        dateTextView =findViewById(R.id.detail_diet_date);
        periodTextView =findViewById(R.id.detail_diet_period);
        timeTextView = findViewById(R.id.detail_diet_time);
        Intent intent =getIntent();

        object_seq = intent.getStringExtra("object_seq");
        periodStr =intent.getStringExtra("dietPeriod");
        dateTimeStr = intent.getStringExtra("dietDate");
        contentsStr = intent.getStringExtra("dietContents");
        sessionC_seq = intent.getStringExtra("c_seq");
        imgUrl= intent.getStringExtra("imgUrl");

        dietLayout= findViewById(R.id.diet_detail_diet);
        exerciseLayout=findViewById(R.id.diet_detail_exercise);

        LoadImage loadImage = new LoadImage(imgUrl,imgImageView);
        loadImage.execute();
        periodTextView.setText(periodStr);

        contentsTextView.setText(contentsStr);



        if(periodStr.equals("운동")){
            dietLayout.setVisibility(View.GONE);
            exerciseLayout.setVisibility(View.VISIBLE);
            weightTextView = findViewById(R.id.diet_detail_weight_content);
            heightTextView = findViewById(R.id.diet_detail_height_content);
            fatTextView = findViewById(R.id.diet_detail_fat_content);
            partTextView = findViewById(R.id.diet_detail_part_content);
            minuteTextView = findViewById(R.id.diet_detail_minute_content);

            weightStr  = intent.getStringExtra("weightStr");
            heightStr  = intent.getStringExtra("heightStr");
            fatStr   = intent.getStringExtra("fatStr");
            partStr  = intent.getStringExtra("partStr");
            minuteStr  = intent.getStringExtra("minuteStr");

            weightTextView.setText(weightStr);
            heightTextView.setText(heightStr);
            fatTextView.setText(fatStr);
            partTextView.setText(partStr);
            minuteTextView.setText(minuteStr);

        }
        else{
            dietLayout.setVisibility(View.VISIBLE);
            exerciseLayout.setVisibility(View.GONE);
            mealTextView= findViewById(R.id.diet_detail_meal_content);
            kcalTextView= findViewById(R.id.diet_detail_kcal_content);
            mealStr = intent.getStringExtra("mealStr");
            kcalStr = intent.getStringExtra("kcalStr");
            mealTextView.setText(mealStr);
            kcalTextView.setText(kcalStr);
        }


        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(sdf.parse(dateTimeStr).getTime());
            DateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormatTime = new SimpleDateFormat("HH:mm");

            dateStr = dateFormatDate.format(date);
            timeStr = dateFormatTime.format(date);
            dateTextView.setText(dateStr);
            timeTextView.setText(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        btnMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDlg();
                    }
                }
        );



    }
    void showDlg(){
        final ArrayList<String> periodArray=new ArrayList<String>();
        String array[] = new String[]{"수정", "삭제"};



        AlertDialog.Builder dlg = new AlertDialog.Builder(DietListDetail.this);

        dlg.setItems(array , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(array[i].equals("수정")){
                    Intent intent = new Intent(getApplicationContext(), DietListEditing.class);
                    intent.putExtra("mode",1);
                    intent.putExtra("object_seq",object_seq);
                    intent.putExtra("c_img",imgUrl);
                    intent.putExtra("c_seq",sessionC_seq);
                    intent.putExtra("periodStr", periodStr);
                    intent.putExtra("dateStr", dateStr);
                    intent.putExtra("timeStr", timeStr);
                    intent.putExtra("contentsStr", contentsStr);
                    intent.putExtra("mealStr",mealStr);
                    intent.putExtra("kcalStr",kcalStr);

                    intent.putExtra("weightStr",weightStr);
                    intent.putExtra("heightStr",heightStr);
                    intent.putExtra("fatStr",fatStr);
                    intent.putExtra("partStr",partStr);
                    intent.putExtra("minuteStr",minuteStr);

                    startActivity(intent);

                }
                else if(array[i].equals("삭제")){
                    try {
                        if (periodStr.equals("운동")) {
                            deleteExerciseLog(object_seq);
                        } else {
                            deleteDietLog(object_seq);
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }


                finish();

            }
        });
        dlg.show();



    }

    void deleteDietLog(String d_seq){
        Call<Boolean> call = api.callDeleteDietLog(d_seq);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"성공적으로 삭제되었습니다!",Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"알수없는 오류로 삭제되지않았습니다!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"알수없는 오류로 삭제되지않았습니다!",Toast.LENGTH_SHORT).show();
            }
        });


    }
    void deleteExerciseLog(String e_seq){
        Call<Boolean> call = api.callDeleteExerciseLog(e_seq);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"성공적으로 삭제되었습니다!",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"알수없는 오류로 삭제되지않았습니다!",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"알수없는 오류로 삭제되지않았습니다!",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
