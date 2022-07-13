package com.example.e_commerce.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private String img_url1 ;
    private String img_url2 ;
    private String img_url3 ;
    private String img_url4 ;
    private String name ;
    private double price ;
    private boolean isLoved ;
    private String description ;
    private double rating ;

    public ProductModel(String img_url1, String img_url2, String img_url3, String img_url4, String name, double price, boolean isLoved, String description, double rating) {
        this.img_url1 = img_url1;
        this.img_url2 = img_url2;
        this.img_url3 = img_url3;
        this.img_url4 = img_url4;
        this.name = name;
        this.price = price;
        this.isLoved = isLoved;
        this.description = description;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ProductModel(String img_url1, String name, double price, boolean isLoved, String description) {
        this.img_url1 = img_url1;
        this.name = name;
        this.price = price;
        this.isLoved = isLoved;
        this.description = description;
    }

    public String getImg_url1() {
        return img_url1;
    }

    public void setImg_url1(String img_url1) {
        this.img_url1 = img_url1;
    }

    public String getImg_url2() {
        return img_url2;
    }

    public void setImg_url2(String img_url2) {
        this.img_url2 = img_url2;
    }

    public String getImg_url3() {
        return img_url3;
    }

    public void setImg_url3(String img_url3) {
        this.img_url3 = img_url3;
    }

    public String getImg_url4() {
        return img_url4;
    }

    public void setImg_url4(String img_url4) {
        this.img_url4 = img_url4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isLoved() {
        return isLoved;
    }

    public void setLoved(boolean loved) {
        isLoved = loved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
