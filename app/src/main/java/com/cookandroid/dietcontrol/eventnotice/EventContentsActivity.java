package com.cookandroid.dietcontrol.eventnotice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.model.EventVO;

public class EventContentsActivity extends AppCompatActivity {
    TextView actionBarTitle, ev_title, ev_term, ev_subtance;
    String ev_str_title , ev_str_start, ev_str_end, ev_str_contents;
    ImageView actionBarBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_contents);


        actionBarTitle = findViewById(R.id.actionbar_title);
        actionBarBackBtn = findViewById(R.id.actionbar_left_btn);
        actionBarTitle.setText("이벤트 공지");
        actionBarBackBtn.setImageResource(R.drawable.ic_left_arrow);

        ev_title = findViewById(R.id.event_contents_title);
        ev_term = findViewById(R.id.event_contents_term);
        ev_subtance = findViewById(R.id.event_contents_substance);

        Intent intent = getIntent();
        ev_str_start = intent.getStringExtra("ev_start");
        ev_str_end = intent.getStringExtra("ev_end");
        ev_str_title = intent.getStringExtra("ev_title");
        ev_str_contents = intent.getStringExtra("ev_contents");
        
        ev_title.setText(ev_str_title);
        ev_term.setText(ev_str_start + "~" + ev_str_end);
        ev_subtance.setText(ev_str_contents);

        actionBarBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}