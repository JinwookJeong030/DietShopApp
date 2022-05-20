package com.cookandroid.dietcontrol.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReviewVO {
    @SerializedName("re_seq")       //리뷰번호
    private int re_seq;
    @SerializedName("o_seq")        //주문번호
    private int o_seq;
    @SerializedName("c_seq")        //회원번호
    private int c_seq;
    @SerializedName("p_seq")        //제품번호
    private int p_seq;
    @SerializedName("re_date")      //작성일
    private String re_date;
    @SerializedName("re_contents")  //글내용
    private String re_contents;
    @SerializedName("re_img")       //글이미지
    private String re_img;
    @SerializedName("re_rating")       //글별점
    private int re_rating;

//    get메소드
    public int getRe_seq() {
        return re_seq;
    }
    public int getO_seq() {
        return o_seq;
    }
    public int getC_seq() {
        return c_seq;
    }
    public int getP_seq() {
        return p_seq;
    }
    public String getRe_date() {
        return re_date;
    }
    public String getRe_contents() {
        return re_contents;
    }
    public String getRe_img() {
        return re_img;
    }
    public int getRe_rating() {
        return re_rating;
    }

    //    set메소드
    public void setRe_seq(int re_seq) {
        this.re_seq = re_seq;
    }
    public void setO_seq(int o_seq) {
        this.o_seq = o_seq;
    }
    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }
    public void setP_seq(int p_seq) {
        this.p_seq = p_seq;
    }
    public void setRe_date(String re_date) {
        this.re_date = re_date;
    }
    public void setRe_contents(String re_contents) {
        this.re_contents = re_contents;
    }
    public void setRe_img(String re_img) {
        this.re_img = re_img;
    }

    public void setRe_rating(int re_rating) {
        this.re_rating = re_rating;
    }

    //    생성자
    public ReviewVO(int re_seq, int o_seq, int c_seq, int p_seq, String re_date, String re_contents,
                    String re_img, int re_rating) {
        this.re_seq = re_seq;
        this.o_seq = o_seq;
        this.c_seq = c_seq;
        this.p_seq = p_seq;
        this.re_date = re_date;
        this.re_contents = re_contents;
        this.re_img = re_img;
        this.re_rating = re_rating;
    }
}
