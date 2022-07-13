package com.example.e_commerce.models;

import java.io.Serializable;

public class MyCartproductModel implements Serializable {
    private String name;
    //    private String color ;
//    private String size ;
//    private int count;
    private String img_url1;
    private String img_url2;
    private String img_url3;
    private String img_url4;
    private double price;
    private double rating;
    private boolean isLoved ;
    private String description ;

    public MyCartproductModel(String name, String img_url1, String img_url2, String img_url3, String img_url4, double price, double rating, boolean isLoved, String description) {
        this.name = name;
        this.img_url1 = img_url1;
        this.img_url2 = img_url2;
        this.img_url3 = img_url3;
        this.img_url4 = img_url4;
        this.price = price;
        this.rating = rating;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
    //
//    public MyCartproductModel(String name, String color, String size, int count, int image, double price) {
//        this.name = name;
//        this.color = color;
//        this.size = size;
//        this.count = count;
//        this.image = image;
//        this.price = price;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getColor() {
//        return color;
//    }

//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }


}
