package com.cookandroid.dietcontrol.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProductVO implements Serializable {
    @SerializedName("p_seq")        //제품번호
    private int p_seq;
    @SerializedName("p_name")       //제품이름
    private String p_name;
    @SerializedName("p_price")      //제품가격
    private int p_price;
    @SerializedName("p_brand")      //제품 브랜드
    private String p_brand;
    @SerializedName("p_sales")      //제품 판매량
    private int p_sales;
    @SerializedName("p_rating")     //평균 평점
    private double p_rating;
    @SerializedName("p_discount")   //할인율
    private int p_discount;
    @SerializedName("p_date")       //개시날짜
    private String p_date;
    @SerializedName("p_stock")      //재고량
    private int p_stock;
    @SerializedName("p_category")   //제품 카테고리
    private String p_category;
    @SerializedName("p_content")   //제품 내용
    private String p_content;
    @SerializedName("p_img")        //제품 이미지
    private String p_img;
    
//    get메소드
    public int getP_seq() {
        return p_seq;
    }
    public String getP_name() {
        return p_name;
    }
    public int getP_price() {
        return p_price;
    }
    public String getP_brand() {
        return p_brand;
    }
    public int getP_sales() {
        return p_sales;
    }
    public double getP_rating() {
        return p_rating;
    }
    public int getP_discount() {
        return p_discount;
    }
    public String getP_date() {
        return p_date;
    }
    public int getP_stock() {
        return p_stock;
    }
    public String getP_category() {
        return p_category;
    }
    public String getP_content() {
        return p_content;
    }
    public String getP_img() {
        return p_img;
    }

//    set메소드
    public void setP_seq(int p_seq) {
        this.p_seq = p_seq;
    }
    public void setP_name(String p_name) {
        this.p_name = p_name;
    }
    public void setP_price(int p_price) {
        this.p_price = p_price;
    }
    public void setP_brand(String p_brand) {
        this.p_brand = p_brand;
    }
    public void setP_sales(int p_sales) {
        this.p_sales = p_sales;
    }
    public void setP_rating(double p_rating) {
        this.p_rating = p_rating;
    }
    public void setP_discount(int p_discount) {
        this.p_discount = p_discount;
    }
    public void setP_date(String p_date) {
        this.p_date = p_date;
    }
    public void setP_stock(int p_stock) {
        this.p_stock = p_stock;
    }
    public void setP_category(String p_category) {
        this.p_category = p_category;
    }
    public void setP_content(String p_content) {
        this.p_content = p_content;
    }
    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

//   생성자
    public ProductVO(int p_seq, String p_name, int p_price, String p_brand, int p_sales,
                     double p_rating, int p_discount, String p_date, int p_stock, String p_category,
                     String p_content, String p_img) {
        this.p_seq = p_seq;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_brand = p_brand;
        this.p_sales = p_sales;
        this.p_rating = p_rating;
        this.p_discount = p_discount;
        this.p_date = p_date;
        this.p_stock = p_stock;
        this.p_category = p_category;
        this.p_content = p_content;
        this.p_img = p_img;
    }
}
