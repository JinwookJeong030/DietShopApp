package com.cookandroid.dietcontrol.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.util.Formatting;

import java.io.InputStream;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    private RetrofitAPI api = RetrofitInit.getRetrofit();

    private TextView actionBarTitle;
    private ImageView actionBarBackBtn;

    private EditText editID,editPW,editPWConfirm,editName,editAlias,editTell,editEmail,editDomain;
    private TextView IDExist, PWNotEqual, emailChoice;

    private String myBirth;
    private String gender;
    private Boolean c_agree_email = false, c_agree_sms = false;


    private Boolean IDConfirmBtnCheck = false, PWEqualConfirmCheck=false, signUpListCheck =false;
    private String myFinalID;
    private Button confirmBtn, myBirthBtn, myM,myFM,fin;
    private CheckBox service1check,service2check,service3check,service4check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        actionBarTitle = findViewById(R.id.actionbar_title);
        actionBarTitle.setText("회원가입");
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);
        gender="";
        confirmBtn = findViewById(R.id.log_IDconfirmBtn);




        myBirthBtn = findViewById(R.id.log_myBirthBtn);
        Calendar c = Calendar.getInstance();

        int myYear = c.get(Calendar.YEAR);
        int myMonth = c.get(Calendar.MONTH)+1;
        int myDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month+=1;
                myBirthBtn.setText(year + "년 " + month + "월 " + day + "일");
                myBirth = year+"-"+month+"-"+day;
            }
        }, myYear, myMonth, myDay);

        myM = findViewById(R.id.log_myM);
        myFM = findViewById(R.id.log_myFM);
        fin = findViewById(R.id.log_fin);

        service1check = findViewById(R.id.log_service1check);
        service2check = findViewById(R.id.log_service2check);
        service3check = findViewById(R.id.log_service3check);
        service4check = findViewById(R.id.log_service4check);


        IDExist = findViewById(R.id.log_IDexist);
        editID = findViewById(R.id.log_myID);
        editPW = findViewById(R.id.log_myPW);
        editPWConfirm = findViewById(R.id.log_myPWconfirm);
        editName = findViewById(R.id.log_myName);
        editAlias = findViewById(R.id.log_myAlias);
        editTell = findViewById(R.id.log_myPhone);
        editEmail = findViewById(R.id.log_myEmail);
        editDomain = findViewById(R.id.log_myDomain);
        emailChoice =findViewById(R.id.log_email_choice);
        PWNotEqual = findViewById(R.id.log_PWnotEqual);

        emailChoice.setText("이메일 선택");
        editTell.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        editID.setFilters(new InputFilter[]{Formatting.filter});
        editEmail.setFilters(new InputFilter[]{Formatting.filter});



        // 약관동의 내용
        TextView log_long = findViewById(R.id.log_long);
        TextView log_long2 = findViewById(R.id.log_long2);

        try {
            InputStream in = getResources().openRawResource(R.raw.agree);
            byte[] b = new byte[in.available()];
            in.read(b);
            String s = new String(b);

            log_long.setText(s);
            log_long2.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    //뒤로가기 버튼
                    case R.id.actionbar_left_btn:
                        finish();
                        break;
                    //아이디 중복 체크
                    case R.id.log_IDconfirmBtn:
                        checkExistId(editID.getText().toString());
                        break;
                    //생년월일 입력
                    case R.id.log_myBirthBtn:
                        dpd.show();
                        break;
                    //남성 체크
                    case R.id.log_myM:
                        checkGender(1);
                        break;
                    //여성 체크
                    case R.id.log_myFM:
                        checkGender(2);
                        break;
                    //회원가입 버튼
                    case R.id.log_fin:

                        checkAll();

                        if(signUpListCheck) {

                            confirmBtn.setText(myBirth);
                            Call<Void> call = api.callInsertCustomer(

                                    editID.getText().toString(),
                                    editPW.getText().toString(),
                                    editName.getText().toString(),
                                    editAlias.getText().toString(),
                                    editTell.getText().toString(),
                                    myBirth,
                                    editEmail.getText().toString()+"@"+editDomain.getText().toString(),
                                    gender,
                                    service3check.isChecked(),
                                    service4check.isChecked()


                            );


                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    fin.setText("성공");
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    fin.setText("실패");
                                }

                            });


                        }

                        break;

                }
            }
        };
        actionBarBackBtn.setOnClickListener(listener);
        confirmBtn.setOnClickListener(listener);
        myBirthBtn.setOnClickListener(listener);
        myM.setOnClickListener(listener);
        myFM.setOnClickListener(listener);
        fin.setOnClickListener(listener);


        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(editPW.getText().toString().equals(editPWConfirm.getText().toString())) {
                    PWNotEqual.setTextColor(Color.GREEN);
                    PWNotEqual.setText("비밀번호가 일치합니다!");
                    PWEqualConfirmCheck=true;
                }
                else{
                    PWNotEqual.setTextColor(Color.RED);
                    PWNotEqual.setText("비밀번호가 일치하지 않습니다!");
                    PWEqualConfirmCheck=false;
                }
                PWNotEqual.setVisibility(View.VISIBLE);
            }

            public void afterTextChanged(Editable s) {
                // 입력이 끝났을 때 조치
            }
        };
        editPW.addTextChangedListener(textWatcher);
        editPWConfirm.addTextChangedListener(textWatcher);


    }
    void showDlg(String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        if(signUpListCheck){
            msgBuilder.setMessage(message)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();

                        }
                    })
            .setOnDismissListener(new DialogInterface.OnDismissListener(){
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }

            }).setCancelable(false);

        }
        else{
            msgBuilder.setMessage(message);
        }
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();

    }

    /** 아이디 중복확인
     *
     * @param strID 입력된 아이디
     */
    void checkExistId(String strID){
        // 중복확인

        Call<Boolean> call = api.callCheckExistID(strID);
        if(strID.equals("")){
            IDExist.setText("사용할 아이디를 입력해주십시오!");
            IDExist.setTextColor(Color.RED);
            IDExist.setVisibility(View.VISIBLE);
        }
        else{


            call.enqueue(new Callback<Boolean>() {

                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    try {
                        if (response.body()) {
                            //아이디 중복
                            IDExist.setText("이미 존재하는 아이디입니다!");
                            IDExist.setTextColor(Color.RED);
                            IDExist.setVisibility(View.VISIBLE);
                        } else {
                            //사용가능한 아이디
                            IDConfirmBtnCheck = true;
                            IDExist.setText("사용 가능한 아이디입니다!");
                            IDExist.setTextColor(Color.GREEN);
                            IDExist.setVisibility(View.VISIBLE);
                            //결정된 아이디
                            myFinalID = editID.getText().toString();

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        IDExist.setText("인터넷 연결이 원활하지 않습니다...");
                        IDExist.setTextColor(Color.RED);
                        IDExist.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }


    }

    /**
     * 성별 체크 버튼 (select=1 남성, select=2 여성)
     * @param select
     * @return
     */
    void checkGender(int select){
        if(select ==1){
            myM.setBackgroundTintList(getResources().getColorStateList(R.color.mfm_btn_on));
            myM.setTextColor(Color.WHITE);
            myFM.setBackgroundTintList(getResources().getColorStateList(R.color.mfm_btn_off));
            myFM.setTextColor(0xFF6201EE);
            gender="M";
        }
        else if(select ==2) {
            myM.setBackgroundTintList(getResources().getColorStateList(R.color.mfm_btn_off));
            myM.setTextColor(0xFF6201EE);
            myFM.setBackgroundTintList(getResources().getColorStateList(R.color.mfm_btn_on));
            myFM.setTextColor(Color.WHITE);
            gender="F";
        }
    }

    void checkAll(){
        Boolean[] finalCheck = {false, false, false, false, false, false, false, false};

        // 0 : 중복 확인을 했는가?
        if(IDConfirmBtnCheck == true) { finalCheck[0] = true; }

        // 1 : 중복 확인 후 아이디를 변경하지 않았는가?
        if(editID.getText().toString().equals(myFinalID)) { finalCheck[1] = true; }



        String strPW = editPW.getText().toString();
        String strPWConfirm = editPWConfirm.getText().toString();
        // 2: 비밀번호를 입력 안되있는가?
        if(!strPW.equals("") && !strPWConfirm.equals("")) {
            finalCheck[2] = true;
        }

        // 3 : 비밀번호가 일치하는가?
        if(PWEqualConfirmCheck) {
            finalCheck[3] = true;
        }


        // 4 : 생년월일을 선택했는가?
        if((myBirthBtn.getText().toString()).equals("생년월일 확인")) {} else { finalCheck[4] = true;}

        // 5 : 성별을 선택했는가?
        if(!gender.equals("")) { finalCheck[5] = true; }


        // 6 : 약관에 동의했는가?
        if(service1check.isChecked() && service2check.isChecked()) { finalCheck[6] = true; }



        // 7 : 아이디, 비밀번호, 회원 정보, 이메일을 모두 기재했는가?
        EditText myName = findViewById(R.id.log_myName);
        String strName = myName.getText().toString();

        EditText myPhone = findViewById(R.id.log_myPhone);
        String strPhone = myPhone.getText().toString();

        EditText myEmail = findViewById(R.id.log_myEmail);
        String strEmail = myEmail.getText().toString();

        EditText myDomain = findViewById(R.id.log_myDomain);
        String strDomain = myDomain.getText().toString();



        if(strName.equals("") || strPhone.equals("")
                || strEmail.equals("") || strDomain.equals(""))
        {} else { finalCheck[7] = true; }

        String strFinalEmail = strEmail + "@" + strDomain;
        String message="";
        if(finalCheck[0] != true) {
            message ="아이디 중복확인이 안되었습니다!";
        } else if(finalCheck[1] != true) {
            message ="아이디 중복확인이 안되었습니다!";
        } else if(finalCheck[2] != true){
            message ="비밀번호가 입력이 안되었습니다!";
        } else if(finalCheck[3] != true){
            message ="비밀번호가 일치하지 않습니다!";
        } else if(finalCheck[4] != true){
            message ="생년월일이 입력이 안되었습니다!";
        } else if(finalCheck[5] != true){
            message ="성별 선택이 안되었습니다!";
        } else if(finalCheck[6] != true){
            message ="필수 동의약관에 동의해주세요!";
        } else if(finalCheck[7] != true){
            message ="회원정보와 이메일을 입력해주세요!";
        } else{
            message ="회원가입이 정상처리 되었습니다!";
            signUpListCheck =true;
        }
        showDlg(message);


        //InsertData task = new InsertData();
        //task.execute("http://" + IP_ADDRESS + TAG, c_id, c_pw, c_name, c_alias, c_tell, c_email, c_agree_email, c_agree_sms);

    }

    public void OnclickEmailChoice(View v) {

        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(this);

        dlg.setItems(R.array.email_address, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String[] items = getResources().getStringArray(R.array.email_address);

                if(items[position].equals("이메일 선택")) {
                    emailChoice.setText(items[position]);
                    editDomain.setEnabled(false);
                    editDomain.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.gray_500));
                    editDomain.setText(" ");
                }
                else if (items[position].equals("직접입력")) {
                    emailChoice.setText(items[position]);
                    editDomain.setText("");
                    editDomain.setEnabled(true);
                    editDomain.setHint("이메일주소 입력");
                    editDomain.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.my_gray_edittext));
                }
                else {
                    editDomain.setEnabled(false);
                    editDomain.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.gray_500));
                    emailChoice.setText(items[position]);
                    editDomain.setText(items[position]);
                }
            }
        });

        androidx.appcompat.app.AlertDialog alart = dlg.create();
        alart.show();


    }

}



