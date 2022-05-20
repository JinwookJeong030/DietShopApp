package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

public class OrderVO {
    @SerializedName("o_seq")            //주문번호
    private int o_seq;
    @SerializedName("c_seq")            //회원번호
    private int c_seq;
    @SerializedName("p_seq")            //제품번호
    private int p_seq;
    @SerializedName("o_cnt")            //수량
    private int o_cnt;
    @SerializedName("o_person")         //받는분
    private String o_person;
    @SerializedName("o_zipcode")        //우편번호
    private String o_zipcode;
    @SerializedName("o_address")        //기본주소
    private String o_address;
    @SerializedName("o_subaddress")     //상세주소
    private String o_subaddress;
    @SerializedName("o_tell")           //전화번호
    private String o_tell;
    @SerializedName("o_price")          //결게가격
    private int o_price;
    @SerializedName("o_pay")            //결제방법
    private String o_pay;
    @SerializedName("o_card")           //결제카드사
    private String o_card;
    @SerializedName("o_point")          //사용 포인트
    private int o_point;
    @SerializedName("o_coupon_name")    //사용 쿠폰명
    private String o_coupon_name;
    @SerializedName("o_date")    //사용 쿠폰명
    private String o_date;

    public void setO_date(String o_date) {
        this.o_date = o_date;
    }

    //    get메소드
    public int getO_seq() {
        return o_seq;
    }

    public int getC_seq() {
        return c_seq;
    }

    public int getP_seq() {
        return p_seq;
    }

    public int getO_cnt() {
        return o_cnt;
    }

    public String getO_person() {
        return o_person;
    }

    public String getO_zipcode() {
        return o_zipcode;
    }

    public String getO_address() {
        return o_address;
    }

    public String getO_subaddress() {
        return o_subaddress;
    }

    public String getO_tell() {
        return o_tell;
    }

    public int getO_price() {
        return o_price;
    }

    public String getO_pay() {
        return o_pay;
    }

    public String getO_card() {
        return o_card;
    }

    public int getO_point() {
        return o_point;
    }

    public String getO_coupon_name() {
        return o_coupon_name;
    }

    public String getO_date() {
        return o_date;
    }

    //    set메소드
    public void setO_seq(int o_seq) {
        this.o_seq = o_seq;
    }

    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }

    public void setP_seq(int p_seq) {
        this.p_seq = p_seq;
    }

    public void setO_cnt(int o_cnt) {
        this.o_cnt = o_cnt;
    }

    public void setO_person(String o_person) {
        this.o_person = o_person;
    }

    public void setO_zipcode(String o_zipcode) {
        this.o_zipcode = o_zipcode;
    }

    public void setO_address(String o_address) {
        this.o_address = o_address;
    }

    public void setO_subaddress(String o_subaddress) {
        this.o_subaddress = o_subaddress;
    }

    public void setO_tell(String o_tell) {
        this.o_tell = o_tell;
    }

    public void setO_price(int o_price) {
        this.o_price = o_price;
    }

    public void setO_pay(String o_pay) {
        this.o_pay = o_pay;
    }

    public void setO_card(String o_card) {
        this.o_card = o_card;
    }

    public void setO_point(int o_point) {
        this.o_point = o_point;
    }

    public void setO_coupon_name(String o_coupon_name) {
        this.o_coupon_name = o_coupon_name;
    }

    //    생성자
    public OrderVO() {
    }

    public OrderVO(int o_seq, int c_seq, int p_seq, int o_cnt, String o_person, String o_zipcode, String o_address, String o_subaddress, String o_tell, int o_price, String o_pay, String o_card, int o_point, String o_coupon_name, String o_date) {
        this.o_seq = o_seq;
        this.c_seq = c_seq;
        this.p_seq = p_seq;
        this.o_cnt = o_cnt;
        this.o_person = o_person;
        this.o_zipcode = o_zipcode;
        this.o_address = o_address;
        this.o_subaddress = o_subaddress;
        this.o_tell = o_tell;
        this.o_price = o_price;
        this.o_pay = o_pay;
        this.o_card = o_card;
        this.o_point = o_point;
        this.o_coupon_name = o_coupon_name;
        this.o_date = o_date;
    }
}