package com.cookandroid.dietcontrol.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class ExerciseLogVO {
    @SerializedName("e_seq")        //운동번호
    private int e_seq;
    @SerializedName("c_seq")        //회원번호
    private int c_seq;
    @SerializedName("e_date")       //운동날짜
    private String e_date;
    @SerializedName("e_contents")   //운동내용
    private String e_contents;
    @SerializedName("e_img")        //운동이미지
    private String e_img;
    @SerializedName("e_weight")     //운동몸무게
    private double e_weight;
    @SerializedName("e_height")     //운동키
    private double e_height;
    @SerializedName("e_fat")        //운동체지방
    private double e_fat;
    @SerializedName("e_part")        //운동부위
    private String e_part;
    @SerializedName("e_minute")     //운동시간
    private int e_minute;

//   get메소드
    public int getE_seq() {
        return e_seq;
    }
    public int getC_seq() {
        return c_seq;
    }
    public String  getE_date() {
        return e_date;
    }
    public String getE_contents() {
        return e_contents;
    }
    public String getE_img() {
        return e_img;
    }
    public double getE_weight() {
        return e_weight;
    }
    public double getE_height() {
        return e_height;
    }
    public double getE_fat() {
        return e_fat;
    }
    public String getE_part() {
        return e_part;
    }
    public int getE_minute() {
        return e_minute;
    }

//    set메소드
    public void setE_seq(int e_seq) {
        this.e_seq = e_seq;
    }
    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }
    public void setE_date(String  e_date) {
        this.e_date = e_date;
    }
    public void setE_contents(String e_contents) {
        this.e_contents = e_contents;
    }
    public void setE_img(String e_img) {
        this.e_img = e_img;
    }
    public void setE_weight(double e_weight) {
        this.e_weight = e_weight;
    }
    public void setE_height(double e_height) {
        this.e_height = e_height;
    }
    public void setE_fat(double e_fat) {
        this.e_fat = e_fat;
    }
    public void setE_part(String e_part) {
        this.e_part = e_part;
    }
    public void setE_minute(int e_minute) {
        this.e_minute = e_minute;
    }

//    생성자
    public ExerciseLogVO() {
    }
    public ExerciseLogVO(int e_seq, int c_seq, String e_date, String e_contents, String e_img,
                         double e_weight, double e_height, double e_fat, String e_part,
                         int e_minute) {
        this.e_seq = e_seq;
        this.c_seq = c_seq;
        this.e_date = e_date;
        this.e_contents = e_contents;
        this.e_img = e_img;
        this.e_weight = e_weight;
        this.e_height = e_height;
        this.e_fat = e_fat;
        this.e_part = e_part;
        this.e_minute = e_minute;
    }
}
