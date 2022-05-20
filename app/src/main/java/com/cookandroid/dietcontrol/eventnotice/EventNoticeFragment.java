package com.cookandroid.dietcontrol.eventnotice;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.R;
import com.cookandroid.dietcontrol.retrofit.RetrofitAPI;
import com.cookandroid.dietcontrol.model.EventVO;
import com.cookandroid.dietcontrol.retrofit.RetrofitInit;
import com.cookandroid.dietcontrol.util.LoadingDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventNoticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventNoticeFragment extends Fragment {
    RetrofitAPI api;
    private TextView actionBarTitle;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventNoticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopBasketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventNoticeFragment newInstance(String param1, String param2) {
        EventNoticeFragment fragment = new EventNoticeFragment();
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

    EventVO eventVO[] = new EventVO[30];
    String eventTitle[] = new String[30] ,event_str_start[] = new String[30], event_str_end[]= new String[30],
            event_str_contents[] = new String[30];
    Date eventTimeStart[] = new Date[30], eventTimeEnd[]= new Date[30];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        api = RetrofitInit.getRetrofit();

        View v = inflater.inflate(R.layout.fragment_event_notice, container, false);
        actionBarTitle = v.findViewById(R.id.actionbar_title);
        actionBarTitle.setText("이벤트 공지");

            Call<EventVO[]> call = api.callSelectEventNoticeList();
            call.enqueue(new Callback<EventVO[]>() {
                @Override
                public void onResponse(Call<EventVO[]> call, Response<EventVO[]> response) {
                    eventVO = response.body();

                    //1
                    ListView listView;
                    ListEventItem.ListItemAdapter adapter;
                    //2
                    listView = v.findViewById(R.id.event_simple_listview);
                    //3
                    adapter = new ListEventItem.ListItemAdapter();

                    try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    for (int i = 0; i < eventVO.length; i++) {
                        eventTitle[i] = eventVO[i].getEv_title();
                        eventTimeStart[i] = eventVO[i].getEv_start();
                        eventTimeEnd[i] = eventVO[i].getEv_end();
                        event_str_contents[i] = eventVO[i].getEv_contents();

                        event_str_start[i] = String.format("%1$tY-%1$tm-%1$td", eventTimeStart[i]);
                        event_str_end[i] = String.format("%1$tY-%1$tm-%1$td", eventTimeEnd[i]);

                        adapter.addItem(new ListEventItem(eventTitle[i], event_str_start[i], event_str_end[i]));
                    }

                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            ListEventItem listEventItem = (ListEventItem) adapter.getItem(position);
                            Intent intent = new Intent(getActivity(), EventContentsActivity.class);
                            intent.putExtra("ev_title", eventTitle[position]);
                            intent.putExtra("ev_start", event_str_start[position]);
                            intent.putExtra("ev_end", event_str_end[position]);
                            intent.putExtra("ev_contents", event_str_contents[position]);
                            startActivity(intent);
                        }
                    });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<EventVO[]> call, Throwable t) {

                    t.printStackTrace();
                }
            });

        return v;
    }
}