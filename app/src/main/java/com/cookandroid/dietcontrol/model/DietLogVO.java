package com.cookandroid.dietcontrol.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class DietLogVO {
    @SerializedName("d_seq")            //식단번호
    private int d_seq;
    @SerializedName("c_seq")            //회원번호
    private int c_seq;
    @SerializedName("d_date")           //식단날짜
    private String d_date;
    @SerializedName("d_img")            //식단이미지
    private String d_img;
    @SerializedName("d_contents")       //식단내용
    private String d_contents;
    @SerializedName("d_period")         //식단시기
    private String d_period;
    @SerializedName("d_meal")           //식단
    private String d_meal;
    @SerializedName("d_kcal")           //식단칼로리
    private double d_kcal;

//  get메소드
    public int getD_seq() {
        return d_seq;
    }
    public int getC_seq() {
        return c_seq;
    }
    public String getD_date() {
        return d_date;
    }
    public String getD_img() {
        return d_img;
    }
    public String getD_contents() {
        return d_contents;
    }
    public String getD_period() {
        return d_period;
    }
    public String getD_meal() {
        return d_meal;
    }
    public double getD_kcal() {
        return d_kcal;
    }

//  set메소드
    public void setD_seq(int d_seq) {
        this.d_seq = d_seq;
    }
    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }
    public void setD_date(String d_date) {
        this.d_date = d_date;
    }
    public void setD_img(String d_img) {
        this.d_img = d_img;
    }
    public void setD_contents(String d_contents) {
        this.d_contents = d_contents;
    }
    public void setD_period(String d_period) {
        this.d_period = d_period;
    }
    public void setD_meal(String d_meal) {
        this.d_meal = d_meal;
    }
    public void setD_kcal(double d_kcal) {
        this.d_kcal = d_kcal;
    }

    public DietLogVO() {
    }

    //  생성자
    public DietLogVO(int d_seq, int c_seq, String d_date,String d_img,  String d_contents,
                     String d_period, String d_meal, double d_kcal) {
        this.d_seq = d_seq;
        this.c_seq = c_seq;
        this.d_date = d_date;
        this.d_img = d_img;
        this.d_contents = d_contents;
        this.d_period = d_period;
        this.d_meal = d_meal;
        this.d_kcal = d_kcal;
    }
}
