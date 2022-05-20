package com.cookandroid.dietcontrol.dietmanagement;

import static com.cookandroid.dietcontrol.util.UtilStr.SERVER_URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadImage;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import java.text.SimpleDateFormat;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietListEditing extends Activity {


    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private Dialog dialog;
    private String object_seq;
    private TextView actionBarTitle, actionBarSaveBtn , pointCheckStr;
    private Button   btnPeriod, btnDate, btnTime;
    private ImageView actionBarBackBtn,imgBtnImage, imgImageView;
    private EditText editContents,
                     editMeal, editKcal,
                     editWeight, editHeight, editFat,editMinute;
    private Spinner editPart;

    private LinearLayout dietPart, exercisePart;
    private String imgURL , uploadImgURL;
    static String period;
    private RetrofitAPI api = RetrofitInit.getRetrofit();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_editing_dietlog);

        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("다이어트 로그 작성");
        AndroidThreeTen.init(this);

        actionBarBackBtn=findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);
        actionBarSaveBtn=findViewById(R.id.actionBar_right_text);

        pointCheckStr = findViewById(R.id.diet_edit_point_check);
        dietPart =findViewById(R.id.edit_diet_part);
        exercisePart = findViewById(R.id.edit_exercise_part);
        relativeLayout= findViewById(R.id.layout_diet_editing);
        linearLayout= findViewById(R.id.dragView);
        linearLayout.setVisibility(View.GONE);
        imgImageView = findViewById(R.id.edit_diet_img);
        btnPeriod=findViewById(R.id.edit_diet_period);
        btnDate=findViewById(R.id.edit_diet_date);
        btnTime=findViewById(R.id.edit_diet_time);
        imgBtnImage=findViewById(R.id.edit_diet_img);
        
        editMeal =findViewById(R.id.diet_edit_meal_content);
        editKcal=findViewById(R.id.diet_edit_kcal_content);
        editWeight=findViewById(R.id.diet_edit_weight_content);
        editHeight=findViewById(R.id.diet_edit_height_content);
        editFat=findViewById(R.id.diet_edit_fat_content);
        editMinute=findViewById(R.id.diet_edit_minute_content);
        editPart =findViewById(R.id.diet_edit_part_spinner);
        ArrayList arrayListEditPart = new ArrayList();
        arrayListEditPart.add("상체");
        arrayListEditPart.add("하체");
        arrayListEditPart.add("유산소");
        ArrayAdapter<String> arrayAdapterEditPart = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,arrayListEditPart);
        editPart.setAdapter(arrayAdapterEditPart);

        editContents=findViewById(R.id.edit_diet_contents);

        dialog = new Dialog(DietListEditing.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Call<String> callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
                String sessionC_seq = response.body();
                pointCheck(sessionC_seq);




        Intent intent =getIntent();
                String c_img = intent.getStringExtra("c_img");

                LoadImage loadImage = new LoadImage(c_img,imgImageView);
                loadImage.execute();
                
        int mode=intent.getIntExtra("mode",0);
                object_seq = intent.getStringExtra("object_seq");
                btnPeriod.setText(intent.getStringExtra("periodStr"));
                btnDate.setText(intent.getStringExtra("dateStr"));
              
        if(mode ==0) {
            actionBarSaveBtn.setText("저장");
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String time= now.format(formatter);
            btnTime.setText(time);

        }
        else if(mode ==1){
            actionBarSaveBtn.setText("수정");
            btnTime.setText(intent.getStringExtra("timeStr"));
            editContents.setText(intent.getStringExtra("contentsStr"));

            editMeal.setText(intent.getStringExtra("mealStr"));
            editKcal.setText(intent.getStringExtra("kcalStr"));
            editHeight.setText(intent.getStringExtra("weightStr"));
            editWeight.setText(intent.getStringExtra("heightStr"));
            editFat.setText(intent.getStringExtra("fatStr"));
            editMinute.setText(intent.getStringExtra("minuteStr"));
            String partStr ="상체";
            partStr = intent.getStringExtra("partStr");
            try {
                if (partStr.equals("상체"))
                    editPart.setSelection(0);
                else if (partStr.equals("하체"))
                    editPart.setSelection(1);
                else if (partStr.equals("유산소"))
                    editPart.setSelection(2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }





        checkDietOrExercise();

        imgURL= decideImgUrl(btnPeriod.getText().toString(),object_seq);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.actionbar_left_btn:
                            finish();
                        break;
                    case R.id.actionBar_right_text:

                         String dateTimeStr =  btnDate.getText().toString() + " " + btnTime.getText().toString();



                        if(mode==0) {
                            if (btnPeriod.getText().equals("운동")) {
                                Call<String> callMaxExercise = api.callSelectExerciseMax();
                                callMaxExercise.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {


                                        insertExerciseLog(sessionC_seq, dateTimeStr,
                                                imgURL+response.body()+".PNG",
                                                editContents.getText().toString(),
                                                nullOrDouble(editWeight.getText().toString()).toString(),
                                                nullOrDouble(editHeight.getText().toString()).toString(),
                                                nullOrDouble(editFat.getText().toString()).toString(),
                                                editPart.getSelectedItem().toString(),
                                                nullOrDouble(editMinute.getText().toString()).toString());


                                        uploadImg(uploadImgURL,imgURL+response.body()+".PNG");


                                    }





                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });

                        }
                            else{
                                Call<String> callMaxDiet = api.callSelectDietMax();
                                callMaxDiet.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                        insertDietLog(sessionC_seq, dateTimeStr,
                                        imgURL+response.body()+".PNG",
                                        editContents.getText().toString(),
                                        btnPeriod.getText().toString(),
                                        editMeal.getText().toString(),
                                        nullOrDouble(editKcal.getText().toString()).toString()
                                );
                                        if(pointCheckStr.getVisibility() ==View.VISIBLE)
                                            pointUp(sessionC_seq,10);


                                        uploadImg(imgImageView.getDrawable().toString(),imgURL+response.body()+".PNG");
                                    }
                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }



                        }else if(mode==1) {
                            if (btnPeriod.getText().equals("운동"))
                                updateExerciseLog(object_seq ,dateTimeStr,
                                        imgURL,
                                        editContents.getText().toString(),
                                        nullOrDouble( editWeight.getText().toString()).toString(),
                                        nullOrDouble(editHeight.getText().toString()).toString(),
                                        nullOrDouble(editFat.getText().toString()).toString(),
                                        editPart.getSelectedItem().toString(),
                                        nullOrDouble(editMinute.getText().toString()).toString()
                        );
                            else
                                updateDietLog(object_seq, dateTimeStr,
                                        imgURL,
                                        editContents.getText().toString(),
                                        btnPeriod.getText().toString(),
                                        editMeal.getText().toString(),
                                        nullOrDouble( editKcal.getText().toString()).toString());

                        }
                            
                        break;
                    case R.id.edit_diet_period:
                        int index;
                        if(mode ==0)
                            index=0;
                        //글작성
                        else if (btnPeriod.getText().equals("운동"))
                            index=2;
                        //글 수정 식단
                        else
                            index=1;
                        //글 수정 운동
                            setDialogPeriod(index);
                        break;
                    case R.id.edit_diet_date:
                        setDialogDate();
                        break;
                    case R.id.edit_diet_time:
                        setDialogTime();
                        break;
                    case R.id.edit_diet_img:
                        Intent intentGallery = new Intent(Intent.ACTION_PICK);
                        intentGallery. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intentGallery, 200);
                        break;

                }
            }

    };
        actionBarBackBtn.setOnClickListener(listener);
        actionBarSaveBtn.setOnClickListener(listener);
        btnPeriod.setOnClickListener(listener);
        btnDate.setOnClickListener(listener);
        btnTime.setOnClickListener(listener);
        imgBtnImage.setOnClickListener(listener);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });



    }
    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imgImageView.setImageURI(selectedImageUri);
            uploadImgURL= selectedImageUri.getPath();
        }

    }
    /**
     *  period 수정 다이어로그
     */
    void setDialogPeriod(int mode){
        final ArrayList<String> periodArray=new ArrayList<String>();
        String array[];

        if(mode == 1){
            array = new String[]{"아침", "점심", "저녁", "간식및야식"};
        }
        else if(mode == 2){
            array = new String[]{"운동"};
        }
        else{
            array = new String[]{"아침", "점심", "저녁", "간식및야식","운동"};

        }

        AlertDialog.Builder dlg = new AlertDialog.Builder(DietListEditing.this);

        dlg.setItems(array , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                period=array[i];
                btnPeriod.setText(period);
                checkDietOrExercise();

            }
        });
        dlg.show();

    }
    void setDialogDate(){
        dialog.show();


        dialog.setContentView(R.layout.editing_diet_date_dialog);

        MaterialCalendarView cal = dialog.findViewById(R.id.edit_diet_cal_cal);

        cal.setOnDateChangedListener(
                new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        String format = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        String dateStr = sdf.format(date.getCalendar().getTime());
                        btnDate.setText(dateStr);
                        dialog.dismiss();

                    }
                });


    }
    void setDialogTime(){
        dialog.show();
        dialog.setContentView(R.layout.editing_diet_time_dialog);
        TimePicker timePicker = dialog.findViewById(R.id.edit_diet_time_picker);
        Button btnDialogTime = dialog.findViewById(R.id.edit_diet_time_btn);
        btnDialogTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnTime.setText(String.format("%02d", timePicker.getHour())+":"+String.format("%02d", timePicker.getMinute()));
                        dialog.dismiss();
                    }
                }
        );



    }
    void insertDietLog(String c_seq, String d_date,String d_img, String d_contents, String d_period,String d_meal, String d_kcal){
        Call<Boolean> callInsertDietLog = api.callInsertDietLog(
                c_seq,d_date,d_img,d_contents,d_period,d_meal,d_kcal
        );

        callInsertDietLog.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"성공적으로 작성되었습니다!",Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"알수없는 오류로 작성되지않았습니다!",Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"알수없는 오류로 작성되지않았습니다!",Toast.LENGTH_SHORT).show();




            }
        });
        finish();
    }
    void insertExerciseLog(String c_seq, String e_date,String e_img ,String e_contents, String e_weight,String e_height, String e_fat,
                          String e_part,String e_minute){
        Call<Boolean> callInsertExerciseLog = api.callInsertExerciseLog(
                c_seq,e_date,e_img,e_contents,e_weight,e_height,e_fat,e_part,e_minute
        );

        callInsertExerciseLog.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"성공적으로 작성되었습니다!",Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"알수없는 오류로 작성되지않았습니다!",Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"알수없는 오류로 작성되지않았습니다!",Toast.LENGTH_SHORT).show();




            }
        });
        finish();
    }
    void updateDietLog(String d_seq, String d_date, String d_img,String d_contents, String d_period,String d_meal, String d_kcal){

        Call<Boolean> call = api.callUpdateDietLog(d_seq,d_date,d_img,d_contents,d_period,d_meal,d_kcal);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"성공적으로 수정되었습니다!",Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"알수없는 오류로 수정되지않았습니다!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"알수없는 오류로 수정되지않았습니다!",Toast.LENGTH_SHORT).show();
            }

        });


        finish();
    }
    void updateExerciseLog(String e_seq, String e_date, String e_img,String e_contents, String e_weight,String e_height, String e_fat,
                           String e_part,String e_minute){

        Call<Boolean> call = api.callUpdateExerciseLog(e_seq, e_date, e_img,e_contents, e_weight,e_height, e_fat,
                e_part, e_minute);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"성공적으로 수정되었습니다!",Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"알수없는 오류로 수정되지않았습니다!",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"알수없는 오류로 수정되지않았습니다!",Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }


    void checkDietOrExercise(){
        if(btnPeriod.getText().equals("운동")) {
            exercisePart.setVisibility(View.VISIBLE);
            dietPart.setVisibility(View.GONE);
        }else
        {
            exercisePart.setVisibility(View.GONE);
            dietPart.setVisibility(View.VISIBLE);
        }

    }

    /**
     *
     * @param url
     * @param serverUrl
     */
    private  void uploadImg(String url,String serverUrl){
        try {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/PNG"), url);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", "foo", fileBody);


            Call<Boolean> call = api.callUploadImg(serverUrl, body);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body()) {
                        Toast.makeText(getApplicationContext(), "파일 업로드 성공", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "fail-파일 업로드 실패", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "php-파일 업로드 실패", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "파일이없음 -파일 업로드 실패", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 이미지 url주소
     * @param logType
     * @param object_seq
     * @return
     */
    private String decideImgUrl(String logType,String object_seq){
        String URL= SERVER_URL +"img/diet_log";

        if(logType.equals("운동"))
            URL+="/exercise/";
        else
            URL+="/diet/";


        return URL;
    }

    /**
     * 일기 작성시 포인트 증가
     * @param c_seq
     * @param point
     */
    public void pointUp(String c_seq, int point){

        RetrofitAPI retrofitAPI = RetrofitInit.getRetrofit();

        Call<Boolean> callPlusPoint= retrofitAPI.callUpdatePlusCustomerPoint(
                c_seq, "다이어트 일기 작성", point
        ) ;
        callPlusPoint.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    Toast.makeText(getApplicationContext(),"포인트 10+",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });



    }

    /**
     * 금일 최초 포인트 획득여부 판단
     * @param cSeq
     */
    private void pointCheck(String cSeq){


        Call<Boolean> callCheckPoint= api.callCheckDietPoint(cSeq);
        callCheckPoint.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
               if(!response.body())

                    pointCheckStr.setVisibility(View.VISIBLE);
                else
                    pointCheckStr.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                pointCheckStr.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"dd",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    public Double nullOrDouble(String number){
        Double numTemp;
        if(number.equals(""))
            numTemp = 0.0;
        else
            numTemp = Double.parseDouble(number);




        return numTemp;
    }

}
