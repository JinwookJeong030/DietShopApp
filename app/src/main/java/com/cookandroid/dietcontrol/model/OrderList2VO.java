package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

public class OrderList2VO {
    @SerializedName("o_seq")    //주문번호
    private String o_seq;

    @SerializedName("p_name")    //제품 이름
    private String p_name;

    @SerializedName("o_price")  //제품 가격
    private int p_price;

    @SerializedName("p_img")    // 제품 이미지
    private String p_img;

    @SerializedName("o_date")   // 주문 시간
    private String o_date;

    public OrderList2VO(String o_seq, String p_name, int p_price, String p_img, String o_date) {
        this.o_seq = o_seq;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_img = p_img;
        this.o_date = o_date;
    }

    public String getO_seq() {
        return o_seq;
    }

    public void setO_seq(String o_seq) {
        this.o_seq = o_seq;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }

    public String getP_img() {
        return p_img;
    }

    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

    public String getO_date() {
        return o_date;
    }

    public void setO_date(String o_date) {
        this.o_date = o_date;
    }
}
