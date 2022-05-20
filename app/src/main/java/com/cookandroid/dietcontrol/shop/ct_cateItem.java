package com.cookandroid.dietcontrol.shop;

public class ct_cateItem {
    public String img;
    public String name;
    public int price;
    public double rating;


    public ct_cateItem(String img, String name, int price, double rating) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.rating = rating;

    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
