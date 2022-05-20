package com.cookandroid.dietcontrol.shop;

public class pp_purchaseItem {

    String pp_purchase_name;
    int pp_purchase_price;
    String pp_purchase_image;
    int pp_purchase_count;

    public pp_purchaseItem(String name, int price, String image, int count) {
        this.pp_purchase_name = name;
        this.pp_purchase_price = price;
        this.pp_purchase_image = image;
        this.pp_purchase_count = count;
    }

    public String getPp_purchase_name() {
        return pp_purchase_name;
    }

    public void setPp_purchase_name(String pp_purchase_name) {
        this.pp_purchase_name = pp_purchase_name;
    }

    public int getPp_purchase_price() {
        return pp_purchase_price;
    }

    public void setPp_purchase_price(int pp_purchase_price) {
        this.pp_purchase_price = pp_purchase_price;
    }

    public String getPp_purchase_image() {
        return pp_purchase_image;
    }

    public void setPp_purchase_image(String pp_purchase_image) {
        this.pp_purchase_image = pp_purchase_image;
    }

    public int getPp_purchase_count() {
        return pp_purchase_count;
    }

    public void setPp_purchase_count(int pp_purchase_count) {
        this.pp_purchase_count = pp_purchase_count;
    }

    public String getPp_purchase_priceString() {
        return this.getPp_purchase_price() + "원";
    }

    public String getPp_purchase_countString() {
        return "수량 : " + this.getPp_purchase_count() + "개";
    }
}
