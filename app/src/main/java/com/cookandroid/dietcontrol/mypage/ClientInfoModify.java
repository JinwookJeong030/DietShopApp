package com.cookandroid.dietcontrol.mypage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.model.CustomerVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.Formatting;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientInfoModify extends AppCompatActivity {
    public static String sessionCSeq="";
    private CustomerVO customerVO;
    RetrofitAPI api;
    Call<String> callSession;
    Call<CustomerVO> callCustomerVO;
    private ImageView actionBarBackBtn;
    private TextView actionBarTitle, emailChoice, mdfAdZipcode, mdfAddressee, mdfSubAddressee;
    private CheckBox mdfEmailAgree,  mdfSmsAgress;
    private EditText emailDirect,mdfClientId, mdfClientName, mdfAlias, mdfPhoneNum, mdfEmail;
    private Button sexMan, sexWoman, adModifyBtn, deleteBtn, modifyByn, myBirthBtn, mdfPwChangeBtn;
    private Date clientBirth;
    private String clientID, clientPW, clientName, clientAlias, clientPhoneNum, clientGender,  clientAddess,
            clientSubAddress, clientBirthStr, myBirth, clientEmailStr, clientZipcode;
    private String[] clientEmail;
    private boolean emailAgree, smsAgree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info_modify);
        api = RetrofitInit.getRetrofit();
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("??? ?????? ??????");
        //xml??? iD??? ??????
        emailChoice = findViewById(R.id.mdf_email_choice);
        emailDirect = findViewById(R.id.mdf_email_direct);
        sexMan = findViewById(R.id.mdf_sex_man);
        sexWoman = findViewById(R.id.mdf_sex_woman);
        adModifyBtn = findViewById(R.id.mdf_addressee_modify);
        deleteBtn = findViewById(R.id.mdf_addressee_delete);
        modifyByn = findViewById(R.id.mdf_modify_btn);
        myBirthBtn = findViewById(R.id.mdf_my_birth_btn);
        mdfClientId = findViewById(R.id.mdf_client_id);
        mdfClientName =findViewById(R.id.mdf_client_name);
        mdfAlias =findViewById(R.id.mdf_alias);
        mdfPhoneNum =findViewById(R.id.mdf_phonenum);
        mdfEmail = findViewById(R.id.mdf_email_id);
        mdfAdZipcode = findViewById(R.id.mdf_ad_zipcode);
        mdfAddressee = findViewById(R.id.mdf_addressee);
        mdfSubAddressee = findViewById(R.id.mdf_subaddressee);
        mdfEmailAgree =findViewById(R.id.mdf_email_agree);
        mdfSmsAgress = findViewById(R.id.mdf_sms_agree);
        mdfPwChangeBtn = findViewById(R.id.mdf_password_change);

        mdfPhoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mdfEmail.setFilters(new InputFilter[]{Formatting.filter});

        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);

        emailChoice.setText("????????? ??????");
        
        //??????
        if(sessionCSeq != null)
        callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                sessionCSeq = response.body();

                if(sessionCSeq != null) {
                    callCustomerVO = api.callSelectCustomerInfo(sessionCSeq);
                    callCustomerVO.enqueue(new Callback<CustomerVO>() {
                        @Override
                        public void onResponse(Call<CustomerVO> call, Response<CustomerVO> response) {
                            if(response.isSuccessful()) {
                                customerVO = response.body();

                                //DB?????? ????????? ??? ??? ????????? ?????? ??????
                                clientID = customerVO.getC_id();
                                clientPW = customerVO.getC_pw();
                                clientName = customerVO.getC_name();
                                clientAlias = customerVO.getC_alias();
                                clientPhoneNum = customerVO.getC_tell();
                                clientEmail = customerVO.getC_email().split("@");
                                clientGender = customerVO.getC_gender();
                                clientZipcode = customerVO.getAd_zipcode();
                                clientAddess = customerVO.getAd_address();
                                clientSubAddress = customerVO.getAd_subaddress();
                                emailAgree = customerVO.isC_agree_email();
                                smsAgree = customerVO.isC_agree_sms();
                                clientBirth = customerVO.getC_birth();      //??????

                                //DB?????? ????????? ??? View ??? ??????
                                mdfClientId.setText(clientID);
                                mdfClientName.setText(clientName);
                                mdfAlias.setText(clientAlias);
                                mdfPhoneNum.setText(clientPhoneNum);
                                mdfEmail.setText(clientEmail[0]);
                                emailDirect.setText(clientEmail[1]);
                                emailChoice.setText(clientEmail[1]);

                                if(clientGender.equals("M"))
                                    sexMan.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.orange));
                                else
                                    sexWoman.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.orange));

                                if(!(clientZipcode == null)) {
                                    deleteBtn.setVisibility(View.VISIBLE);
                                    mdfAdZipcode.setText(clientZipcode);
                                    mdfAddressee.setText(clientAddess);
                                    mdfSubAddressee.setText(clientSubAddress);
                                }
                                else  {
                                    deleteBtn.setVisibility(View.INVISIBLE);
                                    mdfAdZipcode.setText("????????????");
                                    mdfAddressee.setText("????????????");
                                    mdfSubAddressee.setText("????????????");
                                }

                                if(emailAgree)
                                    mdfEmailAgree.setChecked(true);

                                if(smsAgree)
                                    mdfSmsAgress.setChecked(true);


                                clientBirthStr = String.format("%1$tY???%1$tm???%1$td???",clientBirth);
                                myBirthBtn.setText(clientBirthStr);
                            }

                            //????????????
                            Calendar c = Calendar.getInstance();
                            int myYear = c.get(Calendar.YEAR);
                            int myMonth = c.get(Calendar.MONTH) + 1;
                            int myDay = c.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog dpd = new DatePickerDialog(ClientInfoModify.this ,android.R.style.Theme_Holo_Light_Dialog_MinWidth ,new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    month += 1;
                                    myBirthBtn.setText(year + "??? " + month + "??? " + day + "???");
                                    myBirth = year + "-" + month + "-" + day;
                                }
                            }, myYear, myMonth, myDay);


                            //?????? ?????????
                            View.OnClickListener listener = new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (view.getId()) {
                                        case R.id.mdf_sex_man:
                                            //?????? ?????? (???)
                                            sexMan.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.orange));
                                            sexWoman.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.white));
                                            clientGender = "M";
                                            break;
                                        case R.id.mdf_sex_woman:
                                            //?????? ?????? (???)
                                            sexMan.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.white));
                                            sexWoman.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.orange));
                                            clientGender = "W";
                                            break;
                                        case R.id.mdf_addressee_delete:
                                            //?????? ??????(null ??????)
                                            deleteBtn.setVisibility(View.INVISIBLE);
                                            mdfAdZipcode.setText("????????????");
                                            mdfAddressee.setText("????????????");
                                            mdfSubAddressee.setText("????????????");

                                            clientZipcode = "????????????";
                                            clientAddess = "????????????";
                                            clientSubAddress = "????????????";

                                            OnClickDeleteAddressee();
                                            break;
                                        case R.id.mdf_my_birth_btn:
                                            //????????????
                                            dpd.show();
                                            break;
                                        case R.id.mdf_addressee_modify:
                                            //?????? ??????
                                            Intent goAddressSearch = new Intent(getApplicationContext(), AddressSearchActivity.class);
                                            startActivityForResult(goAddressSearch, 0);
                                            break;

                                        case R.id.mdf_password_change:
                                            //??????????????????
                                            Intent goPassword_change = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                                            goPassword_change.putExtra("ClientID", clientID);
                                            goPassword_change.putExtra("ClientPW", clientPW);
                                            startActivity(goPassword_change);;
                                            break;
                                        case R.id.mdf_modify_btn:
                                            //?????????(????????????, ?????? ??????) ?????? ??????
                                            if(myBirth == null)
                                                myBirth = String.format("%1$tY-%1$tm-%1$td",clientBirth);

                                            Call<Void> call1 = api.callUpdateMyInfo(
                                                    clientID,
                                                    mdfAlias.getText().toString(),
                                                    mdfPhoneNum.getText().toString(),
                                                    myBirth,
                                                    mdfEmail.getText().toString() + "@" + emailDirect.getText().toString(),
                                                    clientGender,
                                                    clientZipcode,
                                                    clientAddess,
                                                    clientSubAddress,
                                                    mdfEmailAgree.isChecked(),
                                                    mdfSmsAgress.isChecked()
                                            );
                                            call1.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            finish();
                                            break;
                                    }
                                }
                            };
                            sexMan.setOnClickListener(listener);
                            sexWoman.setOnClickListener(listener);
                            deleteBtn.setOnClickListener(listener);
                            myBirthBtn.setOnClickListener(listener);
                            modifyByn.setOnClickListener(listener);
                            adModifyBtn.setOnClickListener(listener);
                            mdfPwChangeBtn.setOnClickListener(listener);
                        }

                        @Override
                        public void onFailure(Call<CustomerVO> call, Throwable t) {

                        }
                    });
                }


            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

//    ?????? ?????? ???????????????
    public void OnClickDeleteAddressee() {
        AlertDialog.Builder dlgDel = new AlertDialog.Builder(this);

        dlgDel.setTitle("????????????");
        dlgDel.setMessage("??????????????? ?????? ???????????????.");
        dlgDel.setPositiveButton("??????", null);

        AlertDialog alertDel = dlgDel.create();
        alertDel.show();

    }
//    ????????? ?????? ??????
    public void OnclickEmailChoice(View v) {

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        dlg.setItems(R.array.email_address, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                String[] items = getResources().getStringArray(R.array.email_address);
                if(items[position].equals("????????? ??????")) {
                    emailChoice.setText(items[position]);
                    emailDirect.setEnabled(false);
                    emailDirect.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.gray_500));
                    emailDirect.setText(" ");
                }
                else if (items[position].equals("????????????")) {
                    emailChoice.setText(items[position]);
                    emailDirect.setText("");
                    emailDirect.setEnabled(true);
                    emailDirect.setHint("??????????????? ??????");
                    emailDirect.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.my_gray_edittext));
                }
                else {
                    emailDirect.setEnabled(false);
                    emailDirect.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.gray_500));
                    emailChoice.setText(items[position]);
                    emailDirect.setText(items[position]);
                }
            }
        });

        AlertDialog alart = dlg.create();
        alart.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String address1 = data.getStringExtra("mdfAdZipcode");
            String address2 = data.getStringExtra("mdfAddressee");
            String address3 = data.getStringExtra("mdfSubAddressee");

            deleteBtn.setVisibility(View.VISIBLE);
            mdfAdZipcode.setText(address1);
            mdfAddressee.setText(address2);
            mdfSubAddressee.setText(address3);

            clientZipcode = address1;
            clientAddess = address2;
            clientSubAddress = address3;

        }
    }
}