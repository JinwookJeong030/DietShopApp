package com.cookandroid.dietcontrol.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.sql.Blob;
import java.util.Date;

public class EventVO {
    @SerializedName("ev_seq")
    private int ev_seq;
    @SerializedName("m_seq")
    private int m_seq;
    @SerializedName("ev_start")
    private Date ev_start;
    @SerializedName("ev_end")
    private Date ev_end;
    @SerializedName("ev_title")
    private String ev_title;
    @SerializedName("ev_contents")
    private String ev_contents;
    @SerializedName("ev_img")
    private String ev_img;
    
//    get메소드
    public int getEc_seq() {
        return ev_seq;
    }
    public int getM_seq() {
        return m_seq;
    }
    public Date getEv_start() {
        return ev_start;
    }
    public Date getEv_end() {
        return ev_end;
    }
    public String getEv_title() {
        return ev_title;
    }
    public String getEv_contents() {
        return ev_contents;
    }
    public String getEv_img() {
        return ev_img;
    }

//    set메소드
    public void setEc_seq(int ec_seq) {
        this.ev_seq = ec_seq;
    }
    public void setM_seq(int m_seq) {
        this.m_seq = m_seq;
    }
    public void setEv_start(Date ev_start) {
        this.ev_start = ev_start;
    }
    public void setEv_end(Date ev_end) {
        this.ev_end = ev_end;
    }
    public void setEv_title(String ev_title) {
        this.ev_title = ev_title;
    }
    public void setEv_contents(String ev_contents) {
        this.ev_contents = ev_contents;
    }
    public void setEv_img(String ev_img) {
        this.ev_img = ev_img;
    }

//    생성자
    public EventVO(int ev_seq, int m_seq, Date ev_start, Date ev_end, String ev_title,
                   String ev_contents, String ev_img) {
        this.ev_seq = ev_seq;
        this.m_seq = m_seq;
        this.ev_start = ev_start;
        this.ev_end = ev_end;
        this.ev_title = ev_title;
        this.ev_contents = ev_contents;
        this.ev_img = ev_img;
    }

    public EventVO() {
    }
}
