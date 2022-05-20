package com.cookandroid.dietcontrol.shop;

public class pd_reviewItem {
    public String pd_review_name;
    public String pd_review_date;
    public String pd_review_image;
    public String pd_review;
    public int pd_rating_num;

    public pd_reviewItem(String name, String date, String image, String review, int ratingnum) {
        this.pd_review_name = name;
        this.pd_review_date = date;
        this.pd_review_image = image;
        this.pd_review = review;
        this.pd_rating_num = ratingnum;
    }

    public String getPd_review_name() {
        return pd_review_name;
    }

    public void setPd_review_name(String pd_review_name) {
        this.pd_review_name = pd_review_name;
    }

    public String getPd_review_date() {
        return pd_review_date;
    }

    public void setPd_review_date(String pd_review_date) {
        this.pd_review_date = pd_review_date;
    }

    public String getPd_review_image() {
        return pd_review_image;
    }

    public void setPd_review_image(String pd_review_image) {
        this.pd_review_image = pd_review_image;
    }

    public String getPd_review() {
        return pd_review;
    }

    public void setPd_review(String pd_review) {
        this.pd_review = pd_review;
    }

    public int getPd_rating_num() {
        return pd_rating_num;
    }

    public void setPd_rating_num(int pd_rating_num) {
        this.pd_rating_num = pd_rating_num;
    }

    public String getPd_review_name_date() {
        return getPd_review_name() + " | " + getPd_review_date();
    }
}
