package com.cookandroid.dietcontrol.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeActivity extends AppCompatActivity {
    private RetrofitAPI api = RetrofitInit.getRetrofit();
    private TextView actionBarTitle;
    private ImageView actionBarBackBtn;

    private EditText originPw, password, passwordConfirm;
    private TextView checkingState;
    private Button changBtn;
    private String strID , strNewPW, strOriginPW, strPWConfirm, message="";
    private Boolean PWEqualConfirmCheck=false, updatePwFinalCheck =false, differentOriginToNew=false,
            rightOriginPw=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

//      커스텀 엑션바 
        actionBarTitle = findViewById(R.id.actionbar_title);
        actionBarTitle.setText("비밀번호 변경");
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);

        //xml id와 연결
        password = findViewById(R.id.pw_change_password);
        passwordConfirm = findViewById(R.id.pw_change_password_confirm);
        checkingState = findViewById(R.id.pw_change_ckecking_state);
        changBtn = findViewById(R.id.pw_change_okbtn);
        originPw = findViewById(R.id.pw_origin_password);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 switch (view.getId()) {
                     case R.id.pw_change_okbtn:
                         strID = getIntent().getStringExtra("ClientID");
                         pwCheck();

                         if(updatePwFinalCheck) {
                             Call<Void> call =  api.callUpdatePassword(strID, strNewPW);
                             call.enqueue(new Callback<Void>() {
                                 @Override
                                 public void onResponse(Call<Void> call, Response<Void> response) {
                                     Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                 }
                                 @Override
                                 public void onFailure(Call<Void> call, Throwable t) {
                                     Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }
                         break;
                     case R.id.actionbar_left_btn:
                         finish();
                         break;
                 }
            }
        };
        changBtn.setOnClickListener(listener);
        actionBarBackBtn.setOnClickListener(listener);

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strOriginPW = getIntent().getStringExtra("ClientPW");
                if(originPw.getText().toString().equals(strOriginPW)) {
                    password.setEnabled(true);
                    rightOriginPw=true;

                    if(!originPw.getText().toString().equals(password.getText().toString())) {

                        checkingState.setTextColor(Color.GREEN);
                        checkingState.setText("사용가능한 비밀번호입니다!");
                        differentOriginToNew=true;
                        passwordConfirm.setEnabled(true);

                        if(!password.getText().toString().equals("")) {
                            if(password.getText().toString().equals(passwordConfirm.getText().toString())) {
                                checkingState.setTextColor(Color.GREEN);
                                checkingState.setText("새 비밀번호가 일치합니다!");
                                PWEqualConfirmCheck=true;
                            }
                            else{
                                checkingState.setTextColor(Color.RED);
                                checkingState.setText("새 비밀번호가 일치하지 않습니다!");
                                PWEqualConfirmCheck=false;
                            }
                        }
                        else {
                            checkingState.setTextColor(Color.RED);
                            checkingState.setText("새 비밀번호를 입력해주세요!");
                            PWEqualConfirmCheck=false;
                        }
                        checkingState.setVisibility(View.VISIBLE);
                    }
                    else {
                        checkingState.setTextColor(Color.RED);
                        checkingState.setText("기존 비밀번호와 똑같습니다!");
                        differentOriginToNew=false;
                        passwordConfirm.setEnabled(false);
                        checkingState.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    checkingState.setTextColor(Color.RED);
                    checkingState.setText("기존 비밀번호가 다릅니다!");

                    password.setEnabled(false);
                    passwordConfirm.setEnabled(false);
                    rightOriginPw=false;
                    checkingState.setVisibility(View.VISIBLE);
                }
            }

            public void afterTextChanged(Editable s) {
                // 입력이 끝났을 때 조치
            }
        };
        originPw.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        passwordConfirm.addTextChangedListener(textWatcher);


    }
    //대화상자 보이기
    void showDlg(String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        if(updatePwFinalCheck){
            msgBuilder.setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            //!!뒤로가기 하면 넘어가질않음
                        }
                    });

        }
        else{
            msgBuilder.setMessage(message);
        }
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();

    }
//비밀전호 체크
    void pwCheck() {
        Boolean[] finalCheck = {false, false, false, false};

        strNewPW = password.getText().toString();
        strPWConfirm = passwordConfirm.getText().toString();
        // 0 : 비밀번호를 입력 안되있는가?
        if(!strOriginPW.equals("") && !strNewPW.equals("") && !strPWConfirm.equals("")) {
            finalCheck[0] = true;
        }
        // 1 : 기존 비밀번호가 맞는가?
        if(rightOriginPw) {
            finalCheck[1] = true;
        }
        // 2 : 기존 비밀번호와 새 비밀번호가 다른가?
        if(differentOriginToNew) {
            finalCheck[2] = true;
        }
        // 3 : 새 비밀번호와 새 비밀번호 확인과 일치하는가?
        if(PWEqualConfirmCheck) {
            finalCheck[3] = true;
        }

        if(finalCheck[0] != true) {
            message ="비밀번호가 입력이 안되었습니다!";
        } else if(finalCheck[1] != true) {
            message ="기존 비밀번호가 다릅니다!";
        } else if(finalCheck[2] != true) {
            message ="기존 비밀번호와 다른 비밀번호를 입력해주세요!";
        } else if(finalCheck[3] != true) {
            message ="새 비밀번호와 일치하지 않습니다!";
        } else {
            message ="비밀번호 변경이 정상처리 되었습니다!";
            updatePwFinalCheck =true;
        }
        showDlg(message);
    }

}