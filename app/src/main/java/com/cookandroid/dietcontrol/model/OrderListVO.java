package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

public class OrderListVO {
    @SerializedName("o_seq")    //관리자 번호
    private int o_seq;
    @SerializedName("p_name")    //관리자 번호
    private String p_name;

    public OrderListVO(int o_seq, String p_name) {
        this.o_seq = o_seq;
        this.p_name = p_name;
    }

    public int getO_seq() {
        return o_seq;
    }

    public void setO_seq(int o_seq) {
        this.o_seq = o_seq;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }
}
