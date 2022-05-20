package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CouponVO {
    @SerializedName("cp_seq")           //쿠폰번호
    private int cp_seq;
    @SerializedName("c_seq")            //회원번호
    private int c_seq;
    @SerializedName("cp_name")          //쿠폰명
    private String cp_name;
    @SerializedName("cp_discount")      //쿠폰혜텍(할인율)
    private int cp_discount;
    @SerializedName("cp_date")          //유효기간
    private Date cp_date;
    @SerializedName("cp_useable")       //유효상태
    private boolean cp_useable;

//    get메소드
    public int getCp_seq() {
        return cp_seq;
    }
    public int getC_seq() {
        return c_seq;
    }
    public String getCp_name() {
        return cp_name;
    }
    public int getCp_discount() {
        return cp_discount;
    }
    public Date getCp_date() {
        return cp_date;
    }
    public boolean isCp_useable() {
        return cp_useable;
    }

//    set메소드
    public void setCp_seq(int cp_seq) {
        this.cp_seq = cp_seq;
    }
    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }
    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }
    public void setCp_discount(int cp_discount) {
        this.cp_discount = cp_discount;
    }
    public void setCp_date(Date cp_date) {
        this.cp_date = cp_date;
    }
    public void setCp_useable(boolean cp_useable) {
        this.cp_useable = cp_useable;
    }

//    생성자
    public CouponVO(int cp_seq, int c_seq, String cp_name, int cp_discount, Date cp_date,
                    boolean cp_useable) {
        this.cp_seq = cp_seq;
        this.c_seq = c_seq;
        this.cp_name = cp_name;
        this.cp_discount = cp_discount;
        this.cp_date = cp_date;
        this.cp_useable = cp_useable;
    }
}
