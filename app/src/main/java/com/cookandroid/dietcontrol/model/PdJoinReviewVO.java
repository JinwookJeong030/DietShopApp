package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

public class PdJoinReviewVO {
    @SerializedName("re_seq")
    private String re_seq;
    @SerializedName("re_date")      //작성일
    private String re_date;
    @SerializedName("re_contents")  //글내용
    private String re_contents;
    @SerializedName("re_rating")       //글별점
    private int re_rating;
    @SerializedName("p_name")       //제품이름
    private String p_name;

    public String getRe_seq() {
        return re_seq;
    }

    public void setRe_seq(String re_seq) {
        this.re_seq = re_seq;
    }

    //   get메소드
    public String getRe_date() {
        return re_date;
    }
    public String getRe_contents() {
        return re_contents;
    }
    public int getRe_rating() {
        return re_rating;
    }
    public String getP_name() {
        return p_name;
    }

    //   set메소드
    public void setRe_date(String re_date) {
        this.re_date = re_date;
    }
    public void setRe_contents(String re_contents) {
        this.re_contents = re_contents;
    }
    public void setRe_rating(int re_rating) {
        this.re_rating = re_rating;
    }
    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    //   생성자
    public PdJoinReviewVO(String re_seq, String re_date, String re_contents, int re_rating, String p_name) {
        this.re_seq = re_seq;
        this.re_date = re_date;
        this.re_contents = re_contents;
        this.re_rating = re_rating;
        this.p_name = p_name;
    }

}
