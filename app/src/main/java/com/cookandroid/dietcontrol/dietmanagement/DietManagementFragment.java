package com.cookandroid.dietcontrol.dietmanagement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;

import com.cookandroid.dietcontrol.model.DietLogVO;
import com.cookandroid.dietcontrol.model.ExerciseLogVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietManagementFragment extends Fragment {


    private int tabFlag;
    private TextView barTitle;
    private ViewGroup rootView;

    private MaterialCalendarView cal;
    private Calendar todayCal = Calendar.getInstance();
    private ScrollView scrollDiet, scrollExercise;
    private Button btnToday, btnTomorrow, btnYesterday;
    private ImageButton btnInvisibleCal, btnVisibleCal;
    private FloatingActionButton floatingBtnEditing, floatingBtnStatistics;
    private CalInfo todayCalInfo = new CalInfo(todayCal);
    private TabLayout tabs;
    private Intent intent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RetrofitAPI api = RetrofitInit.getRetrofit();

    public DietManagementFragment() {
        // Required empty public constructor
    }


    public static DietManagementFragment newInstance(String param1, String param2) {
        DietManagementFragment fragment = new DietManagementFragment();
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

        tabFlag = 0;
        todayCal = Calendar.getInstance();
        rootView = (ViewGroup) inflater.inflate(R.layout.diet_main, container, false);

        barTitle = rootView.findViewById(R.id.actionbar_title);
        barTitle.setText("나의 다이어트 로그");

        cal = rootView.findViewById(R.id.cal);
        btnToday = rootView.findViewById(R.id.btnToday);
        btnTomorrow = rootView.findViewById(R.id.btnTomorrow);
        btnYesterday = rootView.findViewById(R.id.btnYesterday);
        btnVisibleCal = rootView.findViewById(R.id.cal_btn_down);
        btnInvisibleCal = rootView.findViewById(R.id.cal_btn_up);
        scrollDiet = rootView.findViewById(R.id.scroll_diet);
        scrollExercise = rootView.findViewById(R.id.scroll_exercise);


        floatingBtnEditing = rootView.findViewById(R.id.diet_main_floating_btn_insert);
        floatingBtnStatistics = rootView.findViewById(R.id.diet_main_floating_btn_statistics);
        tabs = rootView.findViewById(R.id.tablayout_dietlog);

        btnToday.setText(todayCalInfo.getStrCal());
        btnYesterday.setText(todayCalInfo.getYesterday().getStrCal());
        btnTomorrow.setText(todayCalInfo.getTomorrow().getTomorrow().getStrCal());
        todayCalInfo.getYesterday();
        cal.setDateSelected(todayCal, true);
        cal.addDecorators(new SundayDecorator(), new SaturdayDecorator());
        cal.setTileSize(0);

        cal.setCurrentDate(todayCal);
        cal.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        scrollExercise.setVisibility(View.GONE);


        Call<String> callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String sessionC_seq = response.body();



        changeDietList(rootView,sessionC_seq, btnToday.getText().toString());

        /**
         * 버튼 클릭
         */

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    //내일 버튼
                    case R.id.btnTomorrow:
                        setBtnTomorrow();
                        changeDietList(rootView,sessionC_seq, btnToday.getText().toString());

                        break;
                    //어제 버튼
                    case R.id.btnYesterday:
                        setBtnYesterday();
                        changeDietList(rootView,sessionC_seq, btnToday.getText().toString());

                        break;
                    //오늘 캘린더 버튼
                    case R.id.btnToday:
                        setBtnVisibleCal();
                        break;
                    //화살표 아래 캘린더 버튼
                    case R.id.cal_btn_down:
                        setBtnVisibleCal();
                        break;
                    //화살표 위 캘린더 버튼
                    case R.id.cal_btn_up:
                        setBtnInvisibleCal();
                        break;
                    //플로팅 글쓰기 버튼
                    case R.id.diet_main_floating_btn_insert:
                        intent = new Intent(getContext(), DietListEditing.class);
                        intent.putExtra("mode",0);
                        String periodStr;
                        if(tabFlag ==0)
                            periodStr ="아침";
                        else
                            periodStr= "운동";
                        intent.putExtra("periodStr",periodStr);
                        intent.putExtra("dateStr",btnToday.getText());


                        startActivity(intent);
                        break;
                    //플로팅 통계 버튼
                    case R.id.diet_main_floating_btn_statistics:
                        intent = new Intent(getContext(), DietStatistics.class);
                        startActivity(intent);
                        break;

                }
            }
        };

        btnTomorrow.setOnClickListener(listener);
        btnYesterday.setOnClickListener(listener);
        btnVisibleCal.setOnClickListener(listener);
        btnInvisibleCal.setOnClickListener(listener);
        btnToday.setOnClickListener(listener);
        floatingBtnEditing.setOnClickListener(listener);
        floatingBtnStatistics.setOnClickListener(listener);

        /**
         *  캘린더 날짜 클릭
         */
        cal.setOnDateChangedListener(
                new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        todayCal = date.getCalendar();
                        todayCalInfo.setCal(todayCal);
                        btnToday.setText(todayCalInfo.getStrCal());
                        cal.setDateSelected(todayCal, true);

                        todayCal = date.getCalendar();
                        todayCalInfo.setCal(todayCal);
                        todayCal.add(todayCal.DATE, 1);
                        btnTomorrow.setText(todayCalInfo.getStrCal());

                        todayCal.add(todayCal.DATE, -2);
                        todayCalInfo.setCal(todayCal);
                        btnYesterday.setText(todayCalInfo.getStrCal());

                        todayCal.add(todayCal.DATE, 1);
                        todayCalInfo.setCal(todayCal);

                        changeDietList(rootView,sessionC_seq, btnToday.getText().toString());
                    }
                }
        );
        /**
         *  캘린더 달 클릭
         */
        cal.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                todayCal = date.getCalendar();
                todayCalInfo.setCal(todayCal);
                btnToday.setText(todayCalInfo.getStrCal());
                cal.setDateSelected(todayCal, true);

                todayCal = date.getCalendar();
                todayCalInfo.setCal(todayCal);
                todayCal.add(todayCal.DATE, 1);
                btnTomorrow.setText(todayCalInfo.getStrCal());
                todayCal.add(todayCal.DATE, -1);
                todayCalInfo.setCal(todayCal);

                btnYesterday.setText(todayCalInfo.getStrCal());
                todayCal.add(todayCal.DATE, 1);
                todayCalInfo.setCal(todayCal);
            }
        });
        /**
         *  식단/운동 탭 클릭
         */
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if (position == 0) {
                    tabFlag=0;
                    scrollDiet.setVisibility(View.VISIBLE);
                    scrollExercise.setVisibility(View.GONE);

                } else if (position == 1) {
                    tabFlag=1;
                    scrollDiet.setVisibility(View.GONE);
                    scrollExercise.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        return rootView;
    }










    /**
     *  달력 버튼 정보를 담기위한 클래스
     */
    public static class CalInfo{
        private Calendar cal;

        public CalInfo(){
        }
        public CalInfo(Calendar cal){
            this.cal=cal;
        }
        public void setCal(Calendar cal) {
            this.cal = cal;
        }
        public Calendar getCal() {
            return this.cal;
        }
        public CalInfo getTomorrow(){
            Calendar tomorrow= cal;
            tomorrow.add(tomorrow.DATE,1);
            CalInfo calTomorrow= new CalInfo(tomorrow);
            return calTomorrow;
        }
        public CalInfo getYesterday(){
            Calendar yesterday = cal;

            yesterday.add(yesterday.DATE, -1);
            CalInfo calYesterday= new CalInfo(yesterday);
            return calYesterday;
        }
        public String getStrCal(){
            String format = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String date = sdf.format(cal.getTime());
            return date;
        }
    }

    /**
     *  달력을 꾸미기 위한 클래스(토요일)
     */
    class SaturdayDecorator implements DayViewDecorator {
        private final Calendar calendar =Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay==Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }

    }
    /**
     *  달력을 꾸미기 위한 클래스(일요일)
     */
    class SundayDecorator implements DayViewDecorator{

        private final Calendar calendar =Calendar.getInstance();
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }


    /**
     *  내일 버튼 수정
     */
    void setBtnTomorrow(){
        cal.setDateSelected(todayCal,false);
        todayCal=todayCalInfo.getCal();

        todayCal.add(todayCal.DATE,1);
        btnToday.setText(todayCalInfo.getStrCal());
        cal.setDateSelected(todayCal,true);

        todayCal.add(todayCal.DATE,1);
        btnTomorrow.setText(todayCalInfo.getStrCal());
        todayCal.add(todayCal.DATE,-2);
        todayCalInfo.setCal(todayCal);

        btnYesterday.setText(todayCalInfo.getStrCal());
        todayCal.add(todayCal.DATE,1);
        todayCalInfo.setCal(todayCal);

    };
    /**
     *  어제 버튼 수정
     */
   void setBtnYesterday() {
            cal.setDateSelected(todayCal,false);
            todayCal=todayCalInfo.getCal();

            todayCal.add(todayCal.DATE,-1);
            btnToday.setText(todayCalInfo.getStrCal());
            cal.setDateSelected(todayCal,true);

            todayCal.add(todayCal.DATE,1);
            btnTomorrow.setText(todayCalInfo.getStrCal());
            todayCal.add(todayCal.DATE,-2);
            todayCalInfo.setCal(todayCal);

            btnYesterday.setText(todayCalInfo.getStrCal());
            todayCal.add(todayCal.DATE,1);
            todayCalInfo.setCal(todayCal);

        }

    /**
     *  캘린더 나오게하기
     */
    void setBtnVisibleCal(){
        if(cal.getVisibility()==View.VISIBLE) {

            cal.setVisibility(View.GONE);
            btnInvisibleCal.setVisibility(View.GONE);
            btnYesterday.setVisibility(View.VISIBLE);
            btnTomorrow.setVisibility(View.VISIBLE);
        }
        else {
            cal.setVisibility(View.VISIBLE);
            btnInvisibleCal.setVisibility(View.VISIBLE);
            btnYesterday.setVisibility(View.GONE);
            btnTomorrow.setVisibility(View.GONE);

        }

    }
    /**
     *  캘린더 감추기
     */
    void setBtnInvisibleCal(){
        if(cal.getVisibility()==View.VISIBLE) {

            cal.setVisibility(View.GONE);
            btnInvisibleCal.setVisibility(View.GONE);
            btnYesterday.setVisibility(View.VISIBLE);
            btnTomorrow.setVisibility(View.VISIBLE);
        }
        else {
            cal.setVisibility(View.VISIBLE);
            btnInvisibleCal.setVisibility(View.VISIBLE);
            btnYesterday.setVisibility(View.GONE);
            btnTomorrow.setVisibility(View.GONE);

        }


    }



void changeDietList(ViewGroup rootView,String sessionC_seq, String date){
    /**
     * 식단 탭 DB를 통해 불러오기
     */

    Call<DietLogVO[]> callDietLog = api.callSelectDietLog(sessionC_seq, date );

    callDietLog.enqueue(new Callback<DietLogVO[]>() {
        @Override
        public void onResponse(Call<DietLogVO[]> call, Response<DietLogVO[]> response) {


            DietLogVO dietLogVO[] = response.body();
            //1
            ListView listView;
            ListDietLogItem.ListItemAdapter adapter;
            //2
            listView = rootView.findViewById(R.id.list_diet_log);
            //3
            adapter = new ListDietLogItem.ListItemAdapter();


try {

    for (int i = 0; i < dietLogVO.length; i++) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(sdf.parse(dietLogVO[i].getD_date()).getTime());
        DateFormat dateFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");


                adapter.addItem(new ListDietLogItem(dietLogVO[i].getD_period(),
                        dateFormatDateTime.format(date),
                dietLogVO[i].getD_contents(), dietLogVO[i].getD_img()));

    }

}catch (Exception e){
    e.printStackTrace();
}



            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    ListDietLogItem listDietLogItem = (ListDietLogItem) adapter.getItem(position);
                    Intent intent = new Intent(getActivity(), DietListDetail.class);

                    intent.putExtra("object_seq",Integer.toString(dietLogVO[position].getD_seq()) );
                    intent.putExtra("c_seq",sessionC_seq);
                    intent.putExtra("dietPeriod", dietLogVO[position].getD_period());
                    intent.putExtra("dietDate",dietLogVO[position].getD_date() );
                    intent.putExtra("dietContents",dietLogVO[position].getD_contents() );
                    intent.putExtra("mealStr",dietLogVO[position].getD_meal());
                    intent.putExtra("kcalStr",Double.toString(dietLogVO[position].getD_kcal()));
                    intent.putExtra("imgUrl", dietLogVO[position].getD_img());
                    startActivity(intent);
                }
            });

        }

        @Override
        public void onFailure(Call<DietLogVO[]> call, Throwable t) {
            //1
            ListView listView;
            ListDietLogItem.ListItemAdapter adapter;
            //2
            listView = rootView.findViewById(R.id.list_diet_log);
            //3
            adapter = new ListDietLogItem.ListItemAdapter();

            listView.setAdapter(adapter);


        }

    });


    /**
     * 운동 탭 DB를 통해 불러오기
     */
    Call<ExerciseLogVO[]> callExerciseLog = api.callSelectExercise(sessionC_seq, date);

    callExerciseLog.enqueue(new Callback<ExerciseLogVO[]>() {
        @Override
        public void onResponse(Call<ExerciseLogVO[]> call, Response<ExerciseLogVO[]> response) {



            ExerciseLogVO exerciseLogVO[] = response.body();
            //1
            ListView listView;
            ListDietLogItem.ListItemAdapter adapter;
            //2
            listView = rootView.findViewById(R.id.list_exercise_log);
            //3
            adapter = new ListDietLogItem.ListItemAdapter();

try {

    for (int i = 0; i < exerciseLogVO.length; i++) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(sdf.parse(exerciseLogVO[i].getE_date()).getTime());
        DateFormat dateFormatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");




        adapter.addItem(new ListDietLogItem("운동",
                dateFormatDateTime.format(date),
                exerciseLogVO[i].getE_contents(),  exerciseLogVO[i].getE_img()));




    }

}catch (Exception e){
    e.printStackTrace();
}
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    ListDietLogItem listDietLogItem = (ListDietLogItem) adapter.getItem(position);
                    Intent intent = new Intent(getActivity(), DietListDetail.class);
                    intent.putExtra("object_seq",Integer.toString(exerciseLogVO[position].getE_seq()));
                    intent.putExtra("c_seq",sessionC_seq);
                    intent.putExtra("dietPeriod", "운동");
                    intent.putExtra("dietDate",exerciseLogVO[position].getE_date() );
                    intent.putExtra("dietContents",exerciseLogVO[position].getE_contents());

                    intent.putExtra("weightStr",Double.toString(exerciseLogVO[position].getE_weight()));
                    intent.putExtra("heightStr",Double.toString(exerciseLogVO[position].getE_height()));
                    intent.putExtra("fatStr",Double.toString(exerciseLogVO[position].getE_fat()));
                    intent.putExtra("partStr",exerciseLogVO[position].getE_part());
                    intent.putExtra("minuteStr",Integer.toString(exerciseLogVO[position].getE_minute()));

                    intent.putExtra("imgUrl", exerciseLogVO[position].getE_img());

                    startActivity(intent);
                }
            });



        }

        @Override
        public void onFailure(Call<ExerciseLogVO[]> call, Throwable t) {
            //1
            ListView listView;
            ListDietLogItem.ListItemAdapter adapter;
            //2
            listView = rootView.findViewById(R.id.list_exercise_log);
            //3
            adapter = new ListDietLogItem.ListItemAdapter();

            listView.setAdapter(adapter);

        }
    });






}



@Override
    public void onResume() {
        super.onResume();


        Call<String> callSession = api.callGetSession();
        callSession.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String sessionC_seq = response.body();
                changeDietList(rootView,sessionC_seq, btnToday.getText().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();


    }



}