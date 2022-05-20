package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;

public class PointGarlicVO {
    @SerializedName("pt_seq")       //포인트 번호
    private int pt_seq;
    @SerializedName("c_seq")        //회원 번호
    private int c_seq;
    @SerializedName("pt_name")   //포인트 명
    private String pt_name;
    @SerializedName("pt_point")     //포인트량
    private int pt_point;
    @SerializedName("pt_date")      //포인트 지급 날짜
    private String pt_date;

//   get메소드
    public int getPt_seq() {
        return pt_seq;
    }
    public int getC_seq() {
        return c_seq;
    }
    public String getPt_name() {
        return pt_name;
    }
    public int getPt_point() {
        return pt_point;
    }
    public String getPt_date() {
        return pt_date;
    }

//   set메소드
    public void setPt_seq(int pt_seq) {
        this.pt_seq = pt_seq;
    }
    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }
    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }
    public void setPt_point(int pt_point) {
        this.pt_point = pt_point;
    }
    public void setPt_date(String pt_date) {
        this.pt_date = pt_date;
    }

//    생성자
    public PointGarlicVO(int pt_seq, int c_seq, String pt_content, int pt_point, String pt_date) {
        this.pt_seq = pt_seq;
        this.c_seq = c_seq;
        this.pt_name = pt_content;
        this.pt_point = pt_point;
        this.pt_date = pt_date;
    }
}
