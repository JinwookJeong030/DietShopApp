package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
public class CustomerVO {
    @SerializedName("c_seq")            //회원번호
    private int c_seq;
    @SerializedName("c_id")             //아이디
    private String c_id;
    @SerializedName("c_pw")             //패스워드
    private String c_pw;
    @SerializedName("c_name")           //이름
    private String c_name;
    @SerializedName("c_alias")          //닉네임
    private String c_alias;
    @SerializedName("c_tell")           //전화번호
    private String c_tell;
    @SerializedName("c_email")          //이메일
    private String c_email;
    @SerializedName("c_birth")          //생년월일
    private Date c_birth;
    @SerializedName("c_gender")         //성별
    private String c_gender;
    @SerializedName("c_point")          //보유한 총 포인트
    private int c_point;
    @SerializedName("c_agree_email")    //이메일 수신 동의
    private boolean c_agree_email;
    @SerializedName("c_agree_sms")      //sms 수신 동의
    private boolean c_agree_sms;
    @SerializedName("ad_zipcode")       //우편번호
    private String ad_zipcode;
    @SerializedName("ad_address")       //기본주소
    private String ad_address;
    @SerializedName("ad_subaddress")    //상세주소
    private String ad_subaddress;
    @SerializedName("c_useable_coupon") //사용가능한 쿠폰 수
    private int c_useable_coupon;
    public CustomerVO() {
    }

    public int getC_seq() {
        return c_seq;
    }

    public String getC_id() {
        return c_id;
    }

    public String getC_pw() {
        return c_pw;
    }

    public String getC_name() {
        return c_name;
    }

    public String getC_alias() {
        return c_alias;
    }

    public String getC_tell() {
        return c_tell;
    }

    public String getC_email() {
        return c_email;
    }

    public Date getC_birth() {
        return c_birth;
    }

    public String getC_gender() {
        return c_gender;
    }

    public int getC_point() {
        return c_point;
    }

    public boolean isC_agree_email() {
        return c_agree_email;
    }

    public boolean isC_agree_sms() {
        return c_agree_sms;
    }

    public String getAd_zipcode() {
        return ad_zipcode;
    }

    public String getAd_address() {
        return ad_address;
    }

    public String getAd_subaddress() {
        return ad_subaddress;
    }

    public int getC_useable_coupon() {
        return c_useable_coupon;
    }

    public void setC_seq(int c_seq) {
        this.c_seq = c_seq;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public void setC_pw(String c_pw) {
        this.c_pw = c_pw;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setC_alias(String c_alias) {
        this.c_alias = c_alias;
    }

    public void setC_tell(String c_tell) {
        this.c_tell = c_tell;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public void setC_birth(Date c_birth) {
        this.c_birth = c_birth;
    }

    public void setC_gender(String c_gender) {
        this.c_gender = c_gender;
    }

    public void setC_point(int c_point) {
        this.c_point = c_point;
    }

    public void setC_agree_email(boolean c_agree_email) {
        this.c_agree_email = c_agree_email;
    }

    public void setC_agree_sms(boolean c_agree_sms) {
        this.c_agree_sms = c_agree_sms;
    }

    public void setAd_zipcode(String ad_zipcode) {
        this.ad_zipcode = ad_zipcode;
    }

    public void setAd_address(String ad_address) {
        this.ad_address = ad_address;
    }

    public void setAd_subaddress(String ad_subaddress) {
        this.ad_subaddress = ad_subaddress;
    }

    public void setC_useable_coupon(int c_useable_coupon) {
        this.c_useable_coupon = c_useable_coupon;
    }

    public CustomerVO(int c_seq, String c_id, String c_pw, String c_name, String c_alias, String c_tell, String c_email, Date c_birth, String c_gender, int c_point, boolean c_agree_email, boolean c_agree_sms, String ad_zipcode, String ad_address, String ad_subaddress, int c_useable_coupon) {
        this.c_seq = c_seq;
        this.c_id = c_id;
        this.c_pw = c_pw;
        this.c_name = c_name;
        this.c_alias = c_alias;
        this.c_tell = c_tell;
        this.c_email = c_email;
        this.c_birth = c_birth;
        this.c_gender = c_gender;
        this.c_point = c_point;
        this.c_agree_email = c_agree_email;
        this.c_agree_sms = c_agree_sms;
        this.ad_zipcode = ad_zipcode;
        this.ad_address = ad_address;
        this.ad_subaddress = ad_subaddress;
        this.c_useable_coupon = c_useable_coupon;
    }
}
