package com.cookandroid.dietcontrol.mypage;

public class mp_purchaseListItem {

    String mp_o_seq;
    String mp_item_image;
    String mp_item_name;
    int mp_item_price;
    String mp_item_date;

    public mp_purchaseListItem(String mp_o_seq, String mp_item_image, String mp_item_name, int mp_item_price, String mp_item_date) {
        this.mp_o_seq = mp_o_seq;
        this.mp_item_image = mp_item_image;
        this.mp_item_name = mp_item_name;
        this.mp_item_price = mp_item_price;
        this.mp_item_date = mp_item_date;
    }

    public String getMp_o_seq() {
        return mp_o_seq;
    }

    public void setMp_o_seq(String mp_o_seq) {
        this.mp_o_seq = mp_o_seq;
    }

    public String getMp_item_image() {
        return mp_item_image;
    }

    public void setMp_item_image(String mp_item_image) {
        this.mp_item_image = mp_item_image;
    }

    public String getMp_item_name() {
        return mp_item_name;
    }

    public void setMp_item_name(String mp_item_name) {
        this.mp_item_name = mp_item_name;
    }

    public int getMp_item_price() {
        return mp_item_price;
    }

    public void setMp_item_price(int mp_item_price) {
        this.mp_item_price = mp_item_price;
    }

    public String getMp_item_date() {
        return mp_item_date;
    }

    public void setMp_item_date(String mp_item_date) {
        this.mp_item_date = mp_item_date;
    }
}
