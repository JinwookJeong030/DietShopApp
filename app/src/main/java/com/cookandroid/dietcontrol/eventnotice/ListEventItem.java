package com.cookandroid.dietcontrol.eventnotice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.dietcontrol.R;

import java.util.ArrayList;

public class ListEventItem {
    private String eventTitle;
    private String eventTimeStart;
    private String eventTimeEnd;

    //get 메소드
    public String getEventTitle() {
        return eventTitle;
    }
    public String getEventTimeStart() {
        return eventTimeStart;
    }
    public String getEventTimeEnd() {
        return eventTimeEnd;
    }


    //set메소드
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    public void setEventTimeStart(String eventTimeStart) {
        this.eventTimeStart = eventTimeStart;
    }
    public void setEventTimeEnd(String eventTimeEnd) {
        this.eventTimeEnd = eventTimeEnd;
    }

    //    생성자
    public ListEventItem(String eventTitle, String eventTimeStart, String eventTimeEnd) {
        this.eventTitle = eventTitle;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
    }

    public static class ListItemAdapter extends BaseAdapter {
        ArrayList<ListEventItem> items = new ArrayList<ListEventItem>();
        Context context;

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            context = parent.getContext();
            ListEventItem listEventItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.event_simple_listview, parent, false);
            }
            TextView listEventTitle = convertView.findViewById(R.id.event_title);
            TextView listTimeStart = convertView.findViewById(R.id.event_time_start);
            TextView listTimeEnd = convertView.findViewById(R.id.event_time_end);

            listEventTitle.setText(listEventItem.getEventTitle());
            listTimeStart.setText(listEventItem.getEventTimeStart());
            listTimeEnd.setText(listEventItem.getEventTimeEnd());

            return convertView;
        }
        public void addItem(ListEventItem item) {
            items.add(item);
        }
    }
}
