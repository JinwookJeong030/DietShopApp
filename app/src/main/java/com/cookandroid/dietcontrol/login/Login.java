package com.cookandroid.dietcontrol.login;

import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cookandroid.dietcontrol.MainActivity;
import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadingDialog;
import com.cookandroid.dietcontrol.util.UtilStr;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    TextView actionBarTitle;
    ImageView actionBarBackBtn;
    String strID, strPW;
    
    private RetrofitAPI api;

    private MainActivity mainActivity =(MainActivity) getActivity();


    private String mParam1;
    private String mParam2;

    
    public Login() {
    }

/**액티비티 이동을 위한 메소드 *****************************************************/
    // 메인 액티비티 위에 올린다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    // 메인 액티비티에서 내려온다.
    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }
/*******************************************************/
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        super.onCreate(savedInstanceState);

        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.activity_login,container,false);

        //액션바
        actionBarTitle=rootView.findViewById(R.id.actionbar_title);
        actionBarBackBtn=rootView.findViewById(R.id.actionBar_right_btn);
        actionBarTitle.setText("로그인");
        actionBarBackBtn.setImageResource(R.drawable.ic_close);

        Button signInBtn = (Button) rootView.findViewById(R.id.log_signinBtn);
        Button signUpBtn = (Button) rootView.findViewById(R.id.log_signupBtn);
        EditText inputID = (EditText) rootView.findViewById(R.id.log_inputID);
        EditText inputPW = (EditText) rootView.findViewById(R.id.log_inputPW);



        api = RetrofitInit.getRetrofit();


        //로그인 버튼
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                strID = inputID.getText().toString();
                strPW = inputPW.getText().toString();

                if(strID.length() == 0 || strPW.length() == 0) {
                    showDlg();
                } else {


                    //아이디 패스워드 DB 전달
                    Call<String> call = api.callLogin(strID,strPW);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            String c_seq = response.body();
                            try {
                                //로그인
                                if (c_seq.equals("0") || c_seq.equals(null))
                                    Toast.makeText(getContext(), "로그인 실패 - ID 또는 패스워드가 잘못됬습니다!", Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(getContext(), "로그인 완료", Toast.LENGTH_SHORT).show();

                                    LoadingDialog loadingDialog = new LoadingDialog(getContext());
                                    writeSession(c_seq);

                                    loadingDialog.show();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                //홈으로 이동
                                                mainActivity.fragmentChange();
                                                loadingDialog.cancel();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 1000);
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                Toast.makeText(getContext(), "알수없는 오류입니다!", Toast.LENGTH_SHORT).show();
                            }

                            }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                            Toast.makeText(getContext(),"로그인 실패 - 인터넷 연결이 원활하지 않습니다.",Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                            //실패시 실패 토스트로 출력
                        }
                    });


                }
            }
        });

        //회원가입 버튼
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Signup.class);
                startActivity(intent);
            }
        });


        return rootView;

    }

    /**
     *  아이디 비번이 없을시 대화상자
     */
    void showDlg() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setMessage("아이디/비밀번호를 입력해주세요")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }



    /**
     *  로그인시 앱을 껐다 켜도 로그인 유지
     * @param c_seq
     */
    public void writeSession(String c_seq){
        Context context = getActivity().getApplicationContext();;
        SharedPreferences loginLog = context.getSharedPreferences(UtilStr.SESSION,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginLog.edit();
        editor.putString(UtilStr.SESSION_C_SEQ,c_seq);

        editor.apply();
    }

}