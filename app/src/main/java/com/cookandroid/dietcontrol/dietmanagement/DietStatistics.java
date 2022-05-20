package com.cookandroid.dietcontrol.dietmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.dietcontrol.R;

public class DietStatistics extends Activity {
    TextView actionBarTitle;
    ImageView actionBarBackBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_statistics);
        actionBarTitle=findViewById(R.id.actionbar_title);
        actionBarTitle.setText("다이어트 로그 통계");

        actionBarBackBtn=findViewById(R.id.actionbar_left_btn);
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);



        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
