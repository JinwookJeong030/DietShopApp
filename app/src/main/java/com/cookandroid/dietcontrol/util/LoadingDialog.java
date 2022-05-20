package com.cookandroid.dietcontrol.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.cookandroid.dietcontrol.R;

public class LoadingDialog extends Dialog {

    /**
     * 로딩화면 구현
     * @param context
     *
     * 사용
     * LoadingDialog loadingDialog  = new Loading loadingDialog();
     * loadingDialog.show();
     *
     * 로딩이 끝나면
     * loadingDialog.cancel();
     *
     */
    public LoadingDialog(Context context){
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //로딩중 화면터치 x
        this.setCanceledOnTouchOutside(false);
        //로딩중 뒤로가기 x
        this.setCancelable(false);

    }
}
